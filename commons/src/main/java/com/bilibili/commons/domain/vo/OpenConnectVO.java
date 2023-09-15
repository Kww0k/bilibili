package com.bilibili.commons.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Silvery
 * @since 2023/9/15 17:29
 */
@Data
@Accessors(chain = true)
public class OpenConnectVO {
    private String nickname;
}
