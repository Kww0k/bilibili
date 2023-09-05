package com.bilibili.video.controller.web;

import com.bilibili.commons.annotation.SystemLog;
import com.bilibili.commons.domain.RestBean;
import com.bilibili.commons.domain.vo.BannerVO;
import com.bilibili.commons.domain.vo.CardVO;
import com.bilibili.commons.domain.vo.VideoListVO;
import com.bilibili.commons.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Silvery
 * @since 2023/8/25 15:27
 */
@RestController
@RequestMapping("/web/video")
@Validated
@RequiredArgsConstructor
public class WebVideoController {

    private final VideoService videoService;

    @GetMapping("/listBanner")
    @SystemLog(businessName = "获取轮播图")
    public RestBean<List<BannerVO>> listBanner() {
        return videoService.listBanner();
    }

    @GetMapping("/listAdvice")
    @SystemLog(businessName = "获取建议列表")
    public RestBean<List<CardVO>> listAdvice() {
        return videoService.listAdvice();
    }

    @GetMapping("/listCard")
    public RestBean<List<CardVO>> listCard() {
        return videoService.listCard();
    }

    @GetMapping("/getCardInfo/{id}")
    public RestBean<VideoListVO> getCardInfo(@PathVariable Integer id) {
        return videoService.getCardInfo(id);
    }

}
