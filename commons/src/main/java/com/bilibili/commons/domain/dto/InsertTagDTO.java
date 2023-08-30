package com.bilibili.commons.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author Silvery
 * @since 2023/8/22 16:02
 */
@Data
public class InsertTagDTO {
    @NotBlank(message = "标签名不能为空")
    private String name;
    private Integer parentId;
}
