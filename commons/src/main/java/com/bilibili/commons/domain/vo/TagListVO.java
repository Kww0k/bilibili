package com.bilibili.commons.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Silvery
 * @since 2023/9/1 22:17
 */
@Data
public class TagListVO {
    private Integer id;
    private String name;
    private Integer parentId;
    private List<TagListVO> children;
}
