package com.bilibili.commons.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Silvery
 * @since 2023/8/28 17:06
 */
@Data
public class InsertDanmakuDTO {
    /**
     * 内容
     */
    @NotBlank(message = "内容不能为空")
    private String content;
    /**
     * 颜色
     */
    private String color;
    /**
     * 弹幕出现时间
     */
    @NotNull(message = "时间不能为空")
    private String time;
    /**
     * 弹幕类型
     */
    private String type;

    @NotNull(message = "视频id不能为空")
    private Integer videoId;
}
