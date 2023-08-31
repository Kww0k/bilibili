package com.bilibili.video.controller.admin;

import com.bilibili.commons.annotation.SystemLog;
import com.bilibili.commons.domain.RestBean;
import com.bilibili.commons.domain.dto.UpdateDanmakuDTO;
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
@RequestMapping("/admin/danmaku")
@RequiredArgsConstructor
@Validated
public class AdminDanmakuController {

    private final DanmakuService danmakuService;

    @SystemLog(businessName = "获取弹幕列表")
    @GetMapping("/listDanmaku")
    public RestBean<List<DanmakuListVO>> listDanmaku() {
        return danmakuService.listDanmaku();
    }

    @PostMapping("/updateDanmakuById")
    @SystemLog(businessName = "更新弹幕信息")
    public RestBean<Void> updateDanmakuById(@Valid @RequestBody UpdateDanmakuDTO updateDanmakuDTO) {
        return danmakuService.updateDanmakuById(updateDanmakuDTO);
    }

    @DeleteMapping("/deleteDanmakuById/{id}")
    @SystemLog(businessName = "删除弹幕")
    public RestBean<Void> deleteDanmakuById(@PathVariable Integer id) {
        return danmakuService.deleteDanmakuById(id);
    }
}
