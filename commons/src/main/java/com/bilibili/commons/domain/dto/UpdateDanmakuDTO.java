package com.bilibili.commons.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Silvery
 * @since 2023/8/28 17:08
 */
@Data
public class UpdateDanmakuDTO {
    @NotNull(message = "id不能为空")
    private Integer id;
    /**
     * 内容
     */
    private String content;
    /**
     * 颜色
     */
    private String color;
    /**
     * 弹幕出现时间
     */
    private Double time;
    /**
     * 弹幕类型
     */
    private String type;
}
