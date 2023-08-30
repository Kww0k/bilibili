package com.bilibili.file.runner;

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

    @Override
    public void run(String... args) throws Exception {
        List<Files> list = filesMapper.selectList(null);
        for (Files files : list)
            filesMapper.updateById(files.setUrl(minioUtilS.generatePreviewUrl(files.getName(), files.getType())));
    }
}
