package com.bilibili.commons.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Likes)表实体类
 *
 * @author Silvery
 * @since 2023-09-15 16:01:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("likes")
public class Likes  {

    private Integer accountId;
    private Integer likesId;

}
