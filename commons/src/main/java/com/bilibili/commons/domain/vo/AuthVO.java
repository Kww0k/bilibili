package com.bilibili.commons.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Silvery
 * @since 2023/8/17 15:04
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class AuthVO {
    AccountAuthVO accountAuthVO;
    String token;
}
