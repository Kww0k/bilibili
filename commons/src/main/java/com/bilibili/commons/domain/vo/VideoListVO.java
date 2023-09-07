package com.bilibili.commons.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Silvery
 * @since 2023/8/25 15:41
 */
@Data
@Accessors(chain = true)
public class VideoListVO {
    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String description;
    /**
     * 封面图片地址
     */
    private String previewUrl;
    /**
     * 视频地址
     */
    private String videoUrl;
    /**
     * 对应标签
     */
    private String type;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 创建人
     */
    private Integer createBy;

    private String nickname;
    private String url;
    private String signature;
}
