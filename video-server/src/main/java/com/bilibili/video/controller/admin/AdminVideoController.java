package com.bilibili.video.controller.admin;

import com.bilibili.commons.annotation.SystemLog;
import com.bilibili.commons.domain.RestBean;
import com.bilibili.commons.domain.dto.InsertVideoDTO;
import com.bilibili.commons.domain.dto.UpdateVideoDTO;
import com.bilibili.commons.domain.vo.VideoListVO;
import com.bilibili.commons.service.VideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Silvery
 * @since 2023/8/25 15:31
 */
@RestController
@RequestMapping("/admin/video")
@RequiredArgsConstructor
@Validated
public class AdminVideoController {

    private final VideoService videoService;

    @GetMapping("/listPassVideo")
    @SystemLog(businessName = "获取过审的视频列表")
    public RestBean<List<VideoListVO>> listPassVideo(String title, Integer typeId) {
        return videoService.listPassVideo(title, typeId);
    }

    @GetMapping("/passVideo/{id}")
    @SystemLog(businessName = "通过审核")
    public RestBean<Void> passVideo(@PathVariable Integer id) {
        return videoService.passVideo(id);
    }

    @GetMapping("/unPassVideo/{id}")
    @SystemLog(businessName = "下架作品")
    public RestBean<Void> unPassVideo(@PathVariable Integer id) {
        return videoService.unPassVideo(id);
    }

    @GetMapping("/listUnPassVideo")
    @SystemLog(businessName = "获取未审核的视频列表")
    public RestBean<List<VideoListVO>> listUnPassVideo(String title) {
        return videoService.listUnPassVideo(title);
    }

    @PostMapping("/testInsertVideo")
    @SystemLog(businessName = "测试投稿")
    public RestBean<Void> testInsertVideo(@Valid @RequestBody InsertVideoDTO insertVideoDTO) {
        return videoService.insertVideo(insertVideoDTO);
    }

    @PutMapping("/updateVideoById")
    @SystemLog(businessName = "更新视频信息")
    public RestBean<Void> updateVideoById(@Valid @RequestBody UpdateVideoDTO updateVideoById) {
        return videoService.updateVideoById(updateVideoById);
    }

    @DeleteMapping("/deleteVideoById/{id}")
    @SystemLog(businessName = "删除视频信息")
    public RestBean<Void> deleteVideoById(@PathVariable Integer id) {
        return videoService.deleteVideoById(id);
    }
}
