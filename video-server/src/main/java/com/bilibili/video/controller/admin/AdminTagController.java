package com.bilibili.video.controller.admin;

import com.bilibili.commons.annotation.SystemLog;
import com.bilibili.commons.domain.RestBean;
import com.bilibili.commons.domain.dto.InsertTagDTO;
import com.bilibili.commons.domain.dto.UpdateTagDTO;
import com.bilibili.commons.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Silvery
 * @since 2023/8/22 15:45
 */
@RestController
@RequestMapping("/admin/tag")
@RequiredArgsConstructor
@Validated
public class AdminTagController {

    private final TagService tagService;

    @SystemLog(businessName = "新增标签")
    @PutMapping("/insertTag")
    public RestBean<Void> insertTag(@Valid @RequestBody InsertTagDTO insertTagDTO) {
        return tagService.insertTag(insertTagDTO);
    }

    @SystemLog(businessName = "更新标签信息")
    @PostMapping("/updateTagById")
    public RestBean<Void> updateTagById(@Valid @RequestBody UpdateTagDTO updateTagDTO) {
        return tagService.updateTagById(updateTagDTO);
    }

    @SystemLog(businessName = "删除标签信息")
    @DeleteMapping("/deleteTagById/{id}")
    public RestBean<Void> deleteTagById(@PathVariable Integer id) {
        return tagService.deleteTagById(id);
    }
}
