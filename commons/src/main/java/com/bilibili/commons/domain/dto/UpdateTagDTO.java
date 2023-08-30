package com.bilibili.commons.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Silvery
 * @since 2023/8/22 16:03
 */
@Data
public class UpdateTagDTO {
    @NotNull(message = "更新的标签id不能为空")
    private Integer id;
    private String name;
    private Integer parentId;
}
