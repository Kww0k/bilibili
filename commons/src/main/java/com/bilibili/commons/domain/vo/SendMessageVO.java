package com.bilibili.commons.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Silvery
 * @since 2023/9/15 18:18
 */
@Data
@Accessors(chain = true)
public class SendMessageVO {
    Integer id;
    String text;
}
