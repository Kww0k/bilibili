package com.bilibili.commons.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author Silvery
 * @since 2023/8/25 10:06
 */
@Data
public class InsertTodoDTO {
    /**
     * 代办事项
     */
    @NotBlank(message = "代办事项不能为空")
    private String todoAffair;
    /**
     * 完成情况
     */
    private String completionStatus;
}
