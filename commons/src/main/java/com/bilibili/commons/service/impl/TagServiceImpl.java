package com.bilibili.commons.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.commons.domain.RestBean;
import com.bilibili.commons.domain.dto.InsertTagDTO;
import com.bilibili.commons.domain.dto.UpdateTagDTO;
import com.bilibili.commons.domain.entity.Tag;
import com.bilibili.commons.domain.vo.ParentTagListVO;
import com.bilibili.commons.domain.vo.SimpleTagListVO;
import com.bilibili.commons.exctption.video.TagNotFIndException;
import com.bilibili.commons.mapper.TagMapper;
import com.bilibili.commons.service.TagService;
import com.bilibili.commons.utils.BeanCopyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.bilibili.commons.constants.AppConstants.FALSE_CODE;
import static com.bilibili.commons.constants.AppConstants.PARENT_TAG;

/**
 * (Tag)表服务实现类
 *
 * @author Silvery
 * @since 2023-08-22 15:43:07
 */
@Service("tagService")
@RequiredArgsConstructor
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    private final BeanCopyUtils beanCopyUtils;

    @Override
    public RestBean<List<ParentTagListVO>> listParentTag() {
        List<Tag> tags = baseMapper.selectList(new LambdaQueryWrapper<Tag>().eq(Tag::getParentId, PARENT_TAG));
        return RestBean.success(tags.stream()
                .map(tag -> beanCopyUtils.copyBean(tag, ParentTagListVO.class))
                .toList());
    }

    @Override
    public RestBean<Void> insertTag(InsertTagDTO insertTagDTO) {
        if (insertTagDTO.getParentId() != null && insertTagDTO.getParentId() > 0)
            if (baseMapper.selectById(insertTagDTO.getParentId()) == null)
                throw new TagNotFIndException();
        baseMapper.insert(beanCopyUtils.copyBean(insertTagDTO, Tag.class));
        return RestBean.success();
    }

    @Override
    public RestBean<Void> updateTagById(UpdateTagDTO updateTagDTO) {
        if (updateTagDTO.getParentId() != null && updateTagDTO.getParentId() > 0)
            if (baseMapper.selectById(updateTagDTO.getParentId()) == null)
                throw new TagNotFIndException();
        if (baseMapper.updateById(beanCopyUtils.copyBean(updateTagDTO, Tag.class)) == FALSE_CODE)
            throw new TagNotFIndException();
        return RestBean.success();
    }

    @Override
    public RestBean<Void> deleteTagById(Integer id) {
        if (baseMapper.deleteById(id) == FALSE_CODE)
            throw new TagNotFIndException();
        return RestBean.success();
    }

    @Override
    public RestBean<List<SimpleTagListVO>> listSimpleTag() {
        return RestBean.success(baseMapper.selectList(
                new LambdaQueryWrapper<Tag>().ne(Tag::getParentId, PARENT_TAG)).stream()
                .map(tag -> beanCopyUtils.copyBean(tag, SimpleTagListVO.class)
                        .setParent(baseMapper.selectById(tag.getParentId()).getName()))
                .toList());
    }

    @Override
    public RestBean<List<ParentTagListVO>> listTag() {
        return RestBean.success(baseMapper.selectList(null).stream()
                .map(tag -> beanCopyUtils.copyBean(tag, ParentTagListVO.class))
                .toList());
    }
}
