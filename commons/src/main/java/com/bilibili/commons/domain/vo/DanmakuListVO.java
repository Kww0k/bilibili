package com.bilibili.commons.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Silvery
 * @since 2023/8/28 17:05
 */
@Data
@Accessors(chain = true)
public class DanmakuListVO {
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
    private String time;
    /**
     * 弹幕类型
     */
    private String type;

    private boolean isMe;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 创建人
     */
    private String createBy;
}
