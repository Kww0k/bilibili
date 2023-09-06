package com.bilibili.commons.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.commons.cache.AccountListCache;
import com.bilibili.commons.cache.FileListCache;
import com.bilibili.commons.cache.TagCache;
import com.bilibili.commons.cache.VideoCache;
import com.bilibili.commons.domain.RestBean;
import com.bilibili.commons.domain.dto.InsertVideoDTO;
import com.bilibili.commons.domain.dto.UpdateVideoDTO;
import com.bilibili.commons.domain.entity.Account;
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

    private final VideoCache videoCache;

    private final FileListCache fileListCache;

    private final AccountListCache accountListCache;

    private final TagCache tagCache;

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
        Video video = videoCache.getOne(id);
        if (video == null)
            throw new VideoInfoNotFindException();
        baseMapper.updateById(video.setStatus(PASS_VIDEO));
        videoCache.save(video);
        return RestBean.success();
    }

    @Override
    public RestBean<Void> unPassVideo(Integer id) {
        Video video = videoCache.getOne(id);
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
                        .setPreviewUrl(fileListCache.getOne(video.getPreviewId()).getUrl()))
                .toList());
    }

    @Override
    public RestBean<List<CardVO>> listAdvice() {
        return RestBean.success(videoCache.getList()
                .stream()
                .filter(video -> Objects.equals(IS_ADVICE, video.getAdvice()))
                .map(video -> new CardVO()
                        .setId(video.getId())
                        .setTitle(video.getTitle())
                        .setPreviewUrl(fileListCache.getOne(video.getPreviewId()).getUrl())
                        .setVideoUrl(fileListCache.getOne(video.getVideoId()).getUrl())
                        .setCreateTime(video.getCreateTime().substring(5, 10))
                        .setCreateBy(accountListCache.getOne(video.getCreateBy()).getNickname()))
                .toList());
    }

    @Override
    public RestBean<List<CardVO>> listCard() {
        return RestBean.success(videoCache.getList()
                .stream()
                .filter(video -> !Objects.equals(IS_ADVICE, video.getAdvice()))
                .filter(video -> !Objects.equals(video.getBanner(), IS_BANNER))
                .map(video -> new CardVO()
                        .setId(video.getId())
                        .setTitle(video.getTitle())
                        .setPreviewUrl(fileListCache.getOne(video.getPreviewId()).getUrl())
                        .setVideoUrl(fileListCache.getOne(video.getVideoId()).getUrl())
                        .setCreateTime(video.getCreateTime().substring(5, 10))
                        .setCreateBy(accountListCache.getOne(video.getCreateBy()).getNickname()))
                .toList());
    }

    @Override
    public RestBean<VideoListVO> getCardInfo(Integer id) {
        Video video = videoCache.getOne(id);
        if (video == null || Objects.equals(video.getStatus(), NOT_PASS_VIDEO))
            throw new VideoInfoNotFindException();
        Account account = accountListCache.getOne(video.getCreateBy());
        return RestBean.success(beanCopyUtils.copyBean(video, VideoListVO.class)
                .setNickname(account.getNickname())
                .setUrl(account.getUrl())
                .setSignature(account.getSignature()));
    }

    private List<VideoListVO> getList(String title, Integer typeId, String status) {
        return videoCache.getList()
                .stream()
                .filter(video -> Objects.equals(video.getStatus(), status))
                .filter(video -> Objects.equals(typeId, null) | Objects.equals(typeId, video.getTypeId()))
                .filter(video -> !StringUtils.hasText(title) | Objects.equals(title, video.getTitle()))
                .map(video -> beanCopyUtils.copyBean(video, VideoListVO.class)
                        .setPreviewUrl(fileListCache.getOne(video.getPreviewId()).getUrl())
                        .setVideoUrl(fileListCache.getOne(video.getVideoId()).getUrl())
                        .setCreateBy(accountListCache.getOne(video.getCreateBy()).getNickname())
                        .setType(tagCache.getOne(video.getTypeId()).getName()))
                .toList();
    }
}
