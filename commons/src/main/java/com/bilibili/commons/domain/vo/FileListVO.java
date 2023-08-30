package com.bilibili.commons.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Silvery
 * @since 2023/8/21 15:20
 */
@Data
@Accessors(chain = true)
public class FileListVO {
    private Integer id;

    /**
     * 文件名
     */
    private String name;
    /**
     * 类型
     */
    private String type;
    /**
     * 文件类型
     */
    private String fileType;
    /**
     * 文件地址
     */
    private String url;
}
