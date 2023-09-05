package com.bilibili.commons.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Silvery
 * @since 2023/9/5 11:44
 */
@Data
@Accessors(chain = true)
public class BannerVO {
    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 封面图片地址
     */
    private String previewUrl;

    private String color;
}
