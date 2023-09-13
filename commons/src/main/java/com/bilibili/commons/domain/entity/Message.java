package com.bilibili.commons.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Message)表实体类
 *
 * @author Silvery
 * @since 2023-09-13 23:13:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("message")
public class Message  {
    @TableId
    private Long id;

     /**
     * 内容
     */
    private String content;
     /**
     * 发起人
     */
    private Integer fromId;
     /**
     * 接受人
     */
    private Integer toId;
     /**
     * 创建时间
     */
    private String createTime;
     /**
     * 更新时间
     */
    private String updateTime;
    private Integer delFlag;



}
