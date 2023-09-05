package com.bilibili.commons.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.commons.cache.VideoCache;
import com.bilibili.commons.domain.RestBean;
import com.bilibili.commons.domain.dto.InsertVideoDTO;
import com.bilibili.commons.domain.dto.UpdateVideoDTO;
import com.bilibili.commons.domain.entity.Video;
import com.bilibili.commons.domain.vo.BannerVO;
import com.bilibili.commons.domain.vo.CardVO;
import com.bilibili.commons.domain.vo.VideoListVO;
import com.bilibili.commons.exctption.video.VideoInfoNotFindException;
import com.bilibili.commons.mapper.AccountMapper;
import com.bilibili.commons.mapper.FilesMapper;
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

    private final FilesMapper filesMapper;

    private final VideoCache videoCache;

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
        Video video = beanCopyUtils.copyBean(insertVideoDTO, Video.class);
        baseMapper.insert(video);
        videoCache.save(video);
        return RestBean.success();
    }

    @Override
    public RestBean<Void> updateVideoById(UpdateVideoDTO updateVideoById) {
        Video video = beanCopyUtils.copyBean(updateVideoById, Video.class);
        if (baseMapper.updateById(video) == FALSE_CODE)
            throw new VideoInfoNotFindException();
        videoCache.save(video);
        return RestBean.success();
    }

    @Override
    public RestBean<Void> deleteVideoById(Integer id) {
        if (baseMapper.deleteById(id) == FALSE_CODE)
            throw new VideoInfoNotFindException();
        videoCache.delete(id);
        return RestBean.success();
    }

    @Override
    public RestBean<Void> passVideo(Integer id) {
        Video video = videoCache.getOnt(id);
        if (video == null)
            throw new VideoInfoNotFindException();
        baseMapper.updateById(video.setStatus(PASS_VIDEO));
        videoCache.save(video);
        return RestBean.success();
    }

    @Override
    public RestBean<Void> unPassVideo(Integer id) {
        Video video = videoCache.getOnt(id);
        if (video == null)
            throw new VideoInfoNotFindException();
        baseMapper.updateById(video.setStatus(NOT_PASS_VIDEO));
        videoCache.save(video);
        return RestBean.success();
    }

    @Override
    public RestBean<List<BannerVO>> listBanner() {
        return RestBean.success(videoCache.getList()
                .stream()
                .filter(video -> Objects.equals(IS_BANNER, video.getBanner()))
                .map(video -> beanCopyUtils.copyBean(video, BannerVO.class)
                        .setPreviewUrl(filesMapper.selectById(video.getPreviewId()).getUrl()))
                .toList());
    }

    @Override
    public RestBean<List<CardVO>> listAdvice() {
        return RestBean.success(videoCache.getList()
                .stream()
                .filter(video -> Objects.equals(IS_ADVICE, video.getAdvice()))
                .map(video -> beanCopyUtils.copyBean(video, CardVO.class)
                        .setPreviewUrl(filesMapper.selectById(video.getPreviewId()).getUrl())
                        .setVideoUrl(filesMapper.selectById(video.getVideoId()).getUrl()))
                .toList());
    }

    @Override
    public RestBean<List<CardVO>> listCard(Integer pageNum) {
        return null;
    }

    private List<VideoListVO> getList(String title, Integer typeId, String status) {
        return videoCache.getList()
                .stream()
                .filter(video -> Objects.equals(video.getStatus(), status))
                .filter(video -> Objects.equals(typeId, null) | Objects.equals(typeId, video.getTypeId()))
                .filter(video -> !StringUtils.hasText(title) | Objects.equals(title, video.getTitle()))
                .map(video -> beanCopyUtils.copyBean(video, VideoListVO.class)
                        .setPreviewUrl(filesMapper.selectById(video.getPreviewId()).getUrl())
                        .setVideoUrl(filesMapper.selectById(video.getVideoId()).getUrl())
                        .setCreateBy(accountMapper.selectById(video.getCreateBy()).getNickname())
                        .setType(tagMapper.selectById(video.getTypeId()).getName()))
                .toList();
    }
}
