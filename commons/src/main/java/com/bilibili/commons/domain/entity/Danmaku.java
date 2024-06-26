package com.bilibili.commons.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 弹幕(Danmaku)表实体类
 *
 * @author Silvery
 * @since 2023-08-28 16:55:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("danmaku")
public class Danmaku  {
    @TableId
    private Integer id;

     /**
     * 内容
     */
    private String content;
     /**
     * 颜色
     */
    private String color;
     /**
     * 弹幕出现时间
     */
    private String time;
     /**
     * 弹幕类型
     */
    private String type;
    private Integer videoId;
    @TableField(fill = FieldFill.INSERT)
    private Integer createBy;
    @TableField(fill = FieldFill.INSERT)
    private String createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;
    private String delFlag;



}
