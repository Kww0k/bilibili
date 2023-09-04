package com.bilibili.commons.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.commons.cache.TagCache;
import com.bilibili.commons.domain.RestBean;
import com.bilibili.commons.domain.dto.InsertTagDTO;
import com.bilibili.commons.domain.dto.UpdateTagDTO;
import com.bilibili.commons.domain.entity.Tag;
import com.bilibili.commons.domain.vo.ParentTagListVO;
import com.bilibili.commons.domain.vo.SimpleTagListVO;
import com.bilibili.commons.domain.vo.TagListVO;
import com.bilibili.commons.exctption.video.TagNotFIndException;
import com.bilibili.commons.mapper.TagMapper;
import com.bilibili.commons.service.TagService;
import com.bilibili.commons.utils.BeanCopyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    private final TagCache tagCache;

    @Override
    public RestBean<List<ParentTagListVO>> listParentTag() {
        return RestBean.success(tagCache.getList()
                .stream()
                .filter(tag -> Objects.equals(tag.getParentId(), PARENT_TAG))
                .map(tag -> beanCopyUtils.copyBean(tag, ParentTagListVO.class))
                .toList());
    }

    @Override
    public RestBean<Void> insertTag(InsertTagDTO insertTagDTO) {
        if (insertTagDTO.getParentId() != null && insertTagDTO.getParentId() > 0)
            if (baseMapper.selectById(insertTagDTO.getParentId()) == null)
                throw new TagNotFIndException();
        Tag tag = beanCopyUtils.copyBean(insertTagDTO, Tag.class);
        baseMapper.insert(tag);
        tagCache.save(tag);
        return RestBean.success();
    }

    @Override
    public RestBean<Void> updateTagById(UpdateTagDTO updateTagDTO) {
        if (updateTagDTO.getParentId() != null && updateTagDTO.getParentId() > 0)
            if (baseMapper.selectById(updateTagDTO.getParentId()) == null)
                throw new TagNotFIndException();
        Tag tag = beanCopyUtils.copyBean(updateTagDTO, Tag.class);
        if (baseMapper.updateById(tag) == FALSE_CODE)
            throw new TagNotFIndException();
        tagCache.save(tag);
        return RestBean.success();
    }

    @Override
    public RestBean<Void> deleteTagById(Integer id) {
        if (baseMapper.deleteById(id) == FALSE_CODE)
            throw new TagNotFIndException();
        tagCache.delete(id);
        return RestBean.success();
    }

    @Override
    public RestBean<List<SimpleTagListVO>> listSimpleTag() {
        return RestBean.success(tagCache.getList()
                .stream()
                .filter(tag -> !Objects.equals(tag.getParentId(), PARENT_TAG))
                .map(tag -> beanCopyUtils.copyBean(tag, SimpleTagListVO.class)
                        .setParent(baseMapper.selectById(tag.getParentId()).getName()))
                .toList());
    }

    @Override
    public RestBean<List<TagListVO>> listTag() {
        List<TagListVO> parentNode = tagCache.getList().stream()
                .filter(tag -> Objects.equals(tag.getParentId(), PARENT_TAG))
                .map(tag -> beanCopyUtils.copyBean(tag, TagListVO.class))
                .toList();
        for (TagListVO tagListVO : parentNode) {
            tagListVO.setChildren(tagCache.getList().stream()
                    .filter(tagListVO1 -> Objects.equals(tagListVO.getId(), tagListVO1.getParentId()))
                    .map(tag -> beanCopyUtils.copyBean(tag, TagListVO.class))
                    .toList());
        }
        return RestBean.success(parentNode);
    }
}
