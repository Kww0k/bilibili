package com.bilibili.commons.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Silvery
 * @since 2023/9/5 11:45
 */
@Data
@Accessors(chain = true)
public class CardVO {
    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 封面图片地址
     */
    private String previewUrl;
    /**
     * 视频地址
     */
    private String videoUrl;
}
