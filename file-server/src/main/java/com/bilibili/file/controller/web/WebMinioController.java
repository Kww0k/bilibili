package com.bilibili.file.controller.web;

import com.bilibili.commons.annotation.SystemLog;
import com.bilibili.commons.domain.RestBean;
import com.bilibili.commons.domain.entity.Files;
import com.bilibili.commons.domain.vo.FileListVO;
import com.bilibili.file.service.FilesService;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/web/file")
public class WebMinioController {


    private final FilesService filesService;

    /**
     * 上传图片文件
     *
     * @author Silvery
     * @since 2023/8/21 10:20
     * @param file 文件
     * @return Object
     */
    @PostMapping("/uploadPicture")
    public RestBean<Files> uploadPicture(MultipartFile file) throws MinioException, NoSuchAlgorithmException, IOException, InvalidKeyException {
        return filesService.uploadPicture(file);
    }

    /**
     * 上传视频文件
     *
     * @author Silvery
     * @since 2023/8/21 10:20
     * @param file 文件
     * @return Object
     */
    @PostMapping("/uploadVideo")
    public RestBean<Files> uploadVideo(MultipartFile file) throws MinioException, NoSuchAlgorithmException, IOException, InvalidKeyException {
        return filesService.uploadVideo(file);
    }

    /**
     * 根据id获取文件信息
     *
     * @author Silvery
     * @since 2023/8/29 11:40
     * @param id 文件id
     */
    @GetMapping("/getFileById/{id}")
    public RestBean<Files> getFileById(@PathVariable Integer id) {
        return RestBean.success(filesService.getByFileById(id));
    }

    /**
     * 获取轮播图
     *
     * @author Silvery
     * @since 2023/8/31 10:45
     * @return RestBean<List<FileListVO>>
     */
    @GetMapping("/listBanner")
    @SystemLog(businessName = "通过id获取图片")
    public RestBean<List<FileListVO>> listBanner() {
        return filesService.listBanner();
    }

}

