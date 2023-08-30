package com.bilibili.commons.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.commons.domain.RestBean;
import com.bilibili.commons.domain.dto.InsertTagDTO;
import com.bilibili.commons.domain.dto.UpdateTagDTO;
import com.bilibili.commons.domain.entity.Tag;
import com.bilibili.commons.domain.vo.ParentTagListVO;
import com.bilibili.commons.domain.vo.SimpleTagListVO;

import java.util.List;


/**
 * (Tag)表服务接口
 *
 * @author Silvery
 * @since 2023-08-22 15:43:07
 */
public interface TagService extends IService<Tag> {

    /**
     * 获取父标签列表
     *
     * @author Silvery
     * @since 2023/8/22 16:05
     * @return RestBean<List<TagListVO>>
     */
    RestBean<List<ParentTagListVO>> listParentTag();

    /**
     * 新增标签信息
     *
     * @author Silvery
     * @since 2023/8/22 16:05
     * @param insertTagDTO 新增的标签信息
     * @return RestBean<Void>
     */
    RestBean<Void> insertTag(InsertTagDTO insertTagDTO);

    /**
     * 更新标签信息
     *
     * @author Silvery
     * @since 2023/8/22 16:06
     * @param updateTagDTO 更新的标签信息
     * @return RestBean<Void>
     */
    RestBean<Void> updateTagById(UpdateTagDTO updateTagDTO);

    /**
     * 删除标签信息
     *
     * @author Silvery
     * @since 2023/8/22 16:06
     * @param id 删除的标签id
     * @return RestBean<Void>
     */
    RestBean<Void> deleteTagById(Integer id);

    /**
     * 获取普通标签列表
     *
     * @author Silvery
     * @since 2023/8/22 16:41
     * @return RestBean<List<TagListVO>>
     */
    RestBean<List<SimpleTagListVO>> listSimpleTag();

    /**
     * 获取所以标签信息
     *
     * @author Silvery
     * @since 2023/8/28 14:18
     * @return RestBean<List<ParentTagListVO>>
     */
    RestBean<List<ParentTagListVO>> listTag();
}
