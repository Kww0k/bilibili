package com.bilibili.file.controller.web;

import com.bilibili.commons.domain.RestBean;
import com.bilibili.commons.domain.entity.Files;
import com.bilibili.file.service.FilesService;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

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
    public RestBean<String> uploadPicture(MultipartFile file) throws MinioException, NoSuchAlgorithmException, IOException, InvalidKeyException {
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
    public RestBean<String> uploadVideo(MultipartFile file) throws MinioException, NoSuchAlgorithmException, IOException, InvalidKeyException {
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
        return RestBean.success(filesService.getById(id));
    }

}

