package com.bilibili.commons.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * (Account)表实体类
 *
 * @author Silvery
 * @since 2023-08-19 13:22:56
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("account")
public class Account implements Serializable {
    /*
    * 用户id
    */
    @TableId
    private Integer id;

     /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nickname;
     /**
     * 密码
     */
    private String password;
     /**
     * 邮箱地址
     */
    private String email;
     /**
     * 头像地址
     */
    private String url;
     /**
     * 生日
     */
    private String birthday;
     /**
     * 性别
     */
    private String sex;
     /**
     * 学校
     */
    private String school;
     /**
     * 签名
     */
    private String signature;
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
    @TableField(fill = FieldFill.INSERT)
    private String createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;

    private Integer delFlag;

}
