package com.bilibili.commons.domain.vo;

import lombok.Data;

/**
 * @author Silvery
 * @since 2023/8/25 09:44
 */
@Data
public class TodoListVO {
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
