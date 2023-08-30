package com.bilibili.commons.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Silvery
 * @since 2023/8/22 15:51
 */
@Data
@Accessors(chain = true)
public class SimpleTagListVO {
    private Integer id;
    private String name;
    private String parent;
}
