package com.bilibili.file.controller.admin;

import com.bilibili.commons.domain.RestBean;
import com.bilibili.commons.domain.vo.FileListVO;
import com.bilibili.file.service.FilesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Silvery
 * @since 2023/8/21 10:39
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/file")
public class AdminMinioController {

    private final FilesService filesService;

    @GetMapping("/listPicture")
    public RestBean<List<FileListVO>> listPicture(String name) {
        return filesService.listPicture(name);
    }

    @GetMapping("/listVideo")
    public RestBean<List<FileListVO>> listVideo(String name) {
        return filesService.listVideo(name);
    }


}
