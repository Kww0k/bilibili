package com.bilibili.file.runner;

import com.bilibili.commons.cache.FileListCache;
import com.bilibili.commons.domain.entity.Files;
import com.bilibili.commons.mapper.FilesMapper;
import com.bilibili.file.utils.MinioUtilS;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author Silvery
 * @since 2023/8/29 11:26
 */
@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    private final MinioUtilS minioUtilS;

    private final FilesMapper filesMapper;

    private final FileListCache fileListCache;

    @Override
    public void run(String... args) throws Exception {
        List<Files> list = filesMapper.selectList(null);
        list.forEach(files -> {
            fileListCache.save(files);
            try {
                filesMapper.updateById(files.setUrl(minioUtilS.generatePreviewUrl(files.getName(), files.getType())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
