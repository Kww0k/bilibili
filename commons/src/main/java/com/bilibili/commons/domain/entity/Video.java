package com.bilibili.commons.domain.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
/**
 * (Video)表实体类
 *
 * @author Silvery
 * @since 2023-08-25 15:23:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("video")
public class Video  {
    /**
    * id
    */
    @TableId
    private Integer id;
     /**
     * 标题
     */
    private String title;
     /**
     * 描述
     */
    private String description;
     /**
     * 封面图片地址
     */
    private Integer previewId;
     /**
     * 视频地址
     */
    private Integer videoId;
    /**
     * 是否是轮播图
     */
    private Integer banner;
     /**
     * 对应标签id
     */
    private Integer typeId;
    /**
     * 是否建议
     */
    private Integer advice;
    /**
     * 颜色
     */
    private String color;
    /**
     * 谁可见
     */
    private String visibility;
    /**
     * 审核状态
     */
    private String status;
    @TableField(fill = FieldFill.INSERT)
    private Integer createBy;
    @TableField(fill = FieldFill.INSERT)
    private String createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;
    private Integer delFlag;



}
