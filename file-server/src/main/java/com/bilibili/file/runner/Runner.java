package com.bilibili.file.runner;

import com.bilibili.commons.cache.FileListCache;
import com.bilibili.commons.domain.entity.Files;
import com.bilibili.commons.mapper.FilesMapper;
import com.bilibili.file.utils.MinioUtilS;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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
    public void run(String... args) {
        List<Files> list = filesMapper.selectList(null);
        list.forEach(files -> {
            try {
                Files files1 = files.setUrl(minioUtilS.generatePreviewUrl(files.getName(), files.getType()));
                filesMapper.updateById(files1);
                fileListCache.save(files1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
