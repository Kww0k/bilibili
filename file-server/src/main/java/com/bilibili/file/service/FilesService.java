package com.bilibili.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.commons.domain.RestBean;
import com.bilibili.commons.domain.entity.Files;
import com.bilibili.commons.domain.vo.FileListVO;
import io.minio.errors.MinioException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;


/**
 * (Files)表服务接口
 *
 * @author Silvery
 * @since 2023-08-21 10:49:48
 */
public interface FilesService extends IService<Files> {

    RestBean<Files> uploadPicture(MultipartFile file) throws MinioException, NoSuchAlgorithmException, IOException, InvalidKeyException;

    RestBean<Files> uploadVideo(MultipartFile file) throws MinioException, NoSuchAlgorithmException, IOException, InvalidKeyException;

    RestBean<List<FileListVO>> listPicture(String name);

    RestBean<List<FileListVO>> listVideo(String name);

    RestBean<List<FileListVO>> listBanner();

    Files getByFileById(Integer id);
}
