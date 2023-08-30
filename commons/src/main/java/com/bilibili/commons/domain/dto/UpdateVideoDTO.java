package com.bilibili.commons.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Silvery
 * @since 2023/8/25 16:06
 */
@Data
public class UpdateVideoDTO {
    @NotNull(message = "id不能为空")
    private Integer id;
    /**
     * 标题
     */
    @NotBlank(message = "标题不能为空")
    private String title;
    /**
     * 描述
     */
    @NotBlank(message = "描述不能为空")
    private String description;
    /**
     * 封面图片地址
     */
    @NotBlank(message = "封面图片不能为空")
    private String previewUrl;
    /**
     * 对应标签id
     */
    @NotNull(message = "标签不能为空")
    private Integer typeId;
    /**
     * 视频地址
     */
    @NotBlank(message = "视频地址不能为空")
    private String videoUrl;
    /**
     * 谁可见
     */
    @NotBlank(message = "可见人群不能为空")
    private String visibility;
}
