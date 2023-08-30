package com.bilibili.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.commons.domain.RestBean;
import com.bilibili.commons.domain.entity.Files;
import com.bilibili.commons.domain.vo.FileListVO;
import com.bilibili.commons.exctption.file.NotPictureException;
import com.bilibili.commons.exctption.file.NotVideoException;
import com.bilibili.commons.exctption.file.UploadFalseException;
import com.bilibili.commons.mapper.FilesMapper;
import com.bilibili.commons.utils.BeanCopyUtils;
import com.bilibili.commons.utils.TypeUtils;
import com.bilibili.file.service.FilesService;
import com.bilibili.file.utils.MinioUtilS;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

import static com.bilibili.commons.constants.AppConstants.*;

/**
 * (Files)表服务实现类
 *
 * @author Silvery
 * @since 2023-08-21 10:49:49
 */
@Service("filesService")
@RequiredArgsConstructor
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files> implements FilesService {

    private final MinioUtilS minioUtilS;

    private final BeanCopyUtils beanCopyUtils;

    private final TypeUtils typeUtils;

    @Override
    public RestBean<String> uploadPicture(MultipartFile file) throws MinioException, NoSuchAlgorithmException, IOException, InvalidKeyException {
        return RestBean.success(upload(file, PICTURE_BUCK));
    }

    @Override
    public RestBean<String> uploadVideo(MultipartFile file) throws MinioException, NoSuchAlgorithmException, IOException, InvalidKeyException {
        return RestBean.success(upload(file, VIDEO_BUCK));
    }

    @Override
    public RestBean<List<FileListVO>> listPicture(String name) {
        return RestBean.success(beanCopyUtils.copyBeanList(baseMapper.selectList(
                        new LambdaQueryWrapper<Files>()
                                .eq(Files::getType, PICTURE_BUCK)
                                .like(StringUtils.hasText(name), Files::getName, name)),
                FileListVO.class));
    }

    @Override
    public RestBean<List<FileListVO>> listVideo(String name) {
        return RestBean.success(beanCopyUtils.copyBeanList(baseMapper.selectList(
                        new LambdaQueryWrapper<Files>()
                                .eq(Files::getType, VIDEO_BUCK)
                                .like(StringUtils.hasText(name), Files::getName, name)),
                FileListVO.class));
    }

    private String upload(MultipartFile file, String type) throws MinioException, NoSuchAlgorithmException, IOException, InvalidKeyException {
        if (Objects.equals(type, PICTURE_BUCK))
            if (!typeUtils.isPicture(file))
                throw new NotPictureException();
        if (Objects.equals(type, VIDEO_BUCK))
            if (!typeUtils.isVideo(file))
                throw new NotVideoException();

        List<String> upload = minioUtilS.upload(new MultipartFile[]{file}, type);
        if (upload.isEmpty())
            throw new UploadFalseException();
        String url = minioUtilS.generatePreviewUrl(upload.get(FILE_POSITION), type);
        if (!StringUtils.hasText(url))
            throw new UploadFalseException();
        baseMapper.insert(new Files().setName(upload.get(FILE_POSITION))
                .setUrl(url)
                .setType(type)
                .setFileType(Objects.requireNonNull(file.getOriginalFilename())
                        .substring(file.getOriginalFilename().lastIndexOf(".") + 1)));
        return url;
    }
}
