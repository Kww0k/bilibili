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
 * 代办事项Todo表实体类
 *
 * @author Silvery
 * @since 2023-08-25 09:25:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("todo")
public class Todo  {
    /**
    * id
    */
    @TableId
    private Integer id;

     /**
     * 代办事项
     */
    private String todoAffair;
     /**
     * 完成情况
     */
    private String completionStatus;
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
