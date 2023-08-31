package com.bilibili.video.controller.web;

import com.bilibili.commons.annotation.SystemLog;
import com.bilibili.commons.domain.RestBean;
import com.bilibili.commons.domain.dto.InsertDanmakuDTO;
import com.bilibili.commons.domain.vo.DanmakuListVO;
import com.bilibili.commons.service.DanmakuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Silvery
 * @since 2023/8/28 16:58
 */
@RestController
@RequestMapping("/web/danmaku")
@RequiredArgsConstructor
@Validated
public class WebDanmakuController {

    private final DanmakuService danmakuService;

    @SystemLog(businessName = "新增弹幕")
    @PutMapping("/insertDanmaku")
    public RestBean<Void> insertDanmaku(@Valid @RequestBody InsertDanmakuDTO insertDanmakuDTO) {
        return danmakuService.insertDanmaku(insertDanmakuDTO);
    }

    @SystemLog(businessName = "通过视频id获取弹幕列表")
    @GetMapping("/listDanmakuByVideoId")
    public RestBean<List<DanmakuListVO>> listDanmakuByVideoId(Integer id) {
        return danmakuService.listDanmakuByVideoId(id);
    }
}
