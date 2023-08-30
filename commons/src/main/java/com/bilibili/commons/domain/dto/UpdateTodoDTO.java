package com.bilibili.commons.domain.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Silvery
 * @since 2023/8/25 10:06
 */
@Data
public class UpdateTodoDTO {
    /**
     * id
     */
    @TableId
    private Integer id;

    /**
     * 代办事项
     */
    private String todoAffair;
    /**
     * 完成情况
     */
    private String completionStatus;
}
