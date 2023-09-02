package com.bilibili.video.controller.web;

import com.bilibili.commons.annotation.SystemLog;
import com.bilibili.commons.domain.RestBean;
import com.bilibili.commons.domain.vo.ParentTagListVO;
import com.bilibili.commons.domain.vo.SimpleTagListVO;
import com.bilibili.commons.domain.vo.TagListVO;
import com.bilibili.commons.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Silvery
 * @since 2023/8/22 15:41
 */
@RestController
@RequestMapping("/web/tag")
@RequiredArgsConstructor
@Validated
public class WebTagController {

    private final TagService tagService;

    @SystemLog(businessName = "获取父标签信息")
    @GetMapping("/listParentTag")
    public RestBean<List<ParentTagListVO>> listParentTag() {
        return tagService.listParentTag();
    }

    @SystemLog(businessName = "获取普通标签信息")
    @GetMapping("/listSimpleTag")
    public RestBean<List<SimpleTagListVO>> listSimpleTag() {
        return tagService.listSimpleTag();
    }

    @SystemLog(businessName = "获取所以标签")
    @GetMapping("/listTag")
    public RestBean<List<TagListVO>> listTag() {
        return tagService.listTag();
    }
}
