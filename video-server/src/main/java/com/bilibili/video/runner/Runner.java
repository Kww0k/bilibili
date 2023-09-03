package com.bilibili.video.runner;

import com.bilibili.commons.domain.entity.Tag;
import com.bilibili.commons.domain.vo.TagListVO;
import com.bilibili.commons.mapper.TagMapper;
import com.bilibili.commons.utils.BeanCopyUtils;
import com.bilibili.commons.utils.RedisCache;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

import static com.bilibili.commons.constants.AppConstants.PARENT_TAG;
import static com.bilibili.commons.constants.VideoConstants.HOME_TAG_CACHE;

/**
 * @author Silvery
 * @since 2023/8/29 11:26
 */
@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    private final TagMapper tagMapper;

    private final BeanCopyUtils beanCopyUtils;

    private final RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        List<Tag> tags = tagMapper.selectList(null);
        List<TagListVO> tagListVOS = beanCopyUtils.copyBeanList(tags, TagListVO.class);
        List<TagListVO> parentNode = tagListVOS.stream()
                .filter(tag -> Objects.equals(tag.getParentId(), PARENT_TAG))
                .toList();
        for (TagListVO tagListVO : parentNode) {
            tagListVO.setChildren(tagListVOS.stream()
                    .filter(tagListVO1 -> Objects.equals(tagListVO.getId(), tagListVO1.getParentId()))
                    .toList());
        }
        redisCache.setCacheList(HOME_TAG_CACHE, parentNode);
    }
}
