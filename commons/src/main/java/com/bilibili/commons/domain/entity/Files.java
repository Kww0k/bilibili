package com.bilibili.commons.domain.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

/**
 * (Files)表实体类
 *
 * @author Silvery
 * @since 2023-08-21 10:49:47
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("files")
public class Files  {
    @TableId
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

    /**
     * 是否是轮播图
     */
    private String banner;
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
