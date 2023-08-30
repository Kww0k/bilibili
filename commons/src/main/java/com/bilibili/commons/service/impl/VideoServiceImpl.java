package com.bilibili.commons.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.commons.domain.RestBean;
import com.bilibili.commons.domain.dto.InsertVideoDTO;
import com.bilibili.commons.domain.dto.UpdateVideoDTO;
import com.bilibili.commons.domain.entity.Video;
import com.bilibili.commons.domain.vo.VideoListVO;
import com.bilibili.commons.exctption.video.VideoInfoNotFindException;
import com.bilibili.commons.mapper.AccountMapper;
import com.bilibili.commons.mapper.TagMapper;
import com.bilibili.commons.mapper.VideoMapper;
import com.bilibili.commons.service.VideoService;
import com.bilibili.commons.utils.BeanCopyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

import static com.bilibili.commons.constants.AppConstants.*;

/**
 * (Video)表服务实现类
 *
 * @author Silvery
 * @since 2023-08-25 15:23:45
 */
@Service("videoService")
@RequiredArgsConstructor
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    private final BeanCopyUtils beanCopyUtils;

    private final AccountMapper accountMapper;

    private final TagMapper tagMapper;

    @Override
    public RestBean<List<VideoListVO>> listPassVideo(String title, Integer typeId) {
        return RestBean.success(getList(title, typeId, PASS_VIDEO));
    }

    @Override
    public RestBean<List<VideoListVO>> listUnPassVideo(String title) {
        return RestBean.success(getList(title, null, NOT_PASS_VIDEO));
    }

    @Override
    public RestBean<Void> insertVideo(InsertVideoDTO insertVideoDTO) {
        baseMapper.insert(beanCopyUtils.copyBean(insertVideoDTO, Video.class));
        return RestBean.success();
    }

    @Override
    public RestBean<Void> updateVideoById(UpdateVideoDTO updateVideoById) {
        if (baseMapper.updateById(beanCopyUtils.copyBean(updateVideoById, Video.class)) == FALSE_CODE)
            throw new VideoInfoNotFindException();
        return RestBean.success();
    }

    @Override
    public RestBean<Void> deleteVideoById(Integer id) {
        if (baseMapper.deleteById(id) == FALSE_CODE)
            throw new VideoInfoNotFindException();
        return RestBean.success();
    }

    @Override
    public RestBean<Void> passVideo(Integer id) {
        Video video = baseMapper.selectById(id);
        if (video == null)
            throw new VideoInfoNotFindException();
        baseMapper.updateById(video.setStatus(PASS_VIDEO));
        return RestBean.success();
    }

    @Override
    public RestBean<Void> unPassVideo(Integer id) {
        Video video = baseMapper.selectById(id);
        if (video == null)
            throw new VideoInfoNotFindException();
        baseMapper.updateById(video.setStatus(NOT_PASS_VIDEO));
        return RestBean.success();
    }

    private List<VideoListVO> getList(String title, Integer typeId, String status) {
        return baseMapper.selectList(new LambdaQueryWrapper<Video>()
                        .eq(Video::getStatus, status)
                        .eq(!Objects.equals(typeId, null), Video::getTypeId, title)
                        .like(StringUtils.hasText(title), Video::getTitle, title))
                .stream()
                .map(video -> beanCopyUtils.copyBean(video, VideoListVO.class)
                        .setCreateBy(accountMapper.selectById(video.getCreateBy()).getNickname())
                        .setType(tagMapper.selectById(video.getTypeId()).getName()))
                .toList();
    }
}
