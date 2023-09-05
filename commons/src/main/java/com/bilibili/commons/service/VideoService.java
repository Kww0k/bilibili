package com.bilibili.commons.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.commons.domain.RestBean;
import com.bilibili.commons.domain.dto.InsertVideoDTO;
import com.bilibili.commons.domain.dto.UpdateVideoDTO;
import com.bilibili.commons.domain.entity.Video;
import com.bilibili.commons.domain.vo.BannerVO;
import com.bilibili.commons.domain.vo.CardVO;
import com.bilibili.commons.domain.vo.VideoListVO;

import java.util.List;


/**
 * (Video)表服务接口
 *
 * @author Silvery
 * @since 2023-08-25 15:23:45
 */
public interface VideoService extends IService<Video> {

    /**
     * 获取过审的列表
     *
     * @param title  标题名
     * @param typeId 所属类型
     * @return RestBean<List < VideoListVO>>
     * @author Silvery
     * @since 2023/8/25 15:43
     */
    RestBean<List<VideoListVO>> listPassVideo(String title, Integer typeId);

    /**
     * 获取未过审核的列表
     *
     * @author Silvery
     * @since 2023/8/25 15:43
     * @param title 标题名
     * @return RestBean<List<VideoListVO>>
     */
    RestBean<List<VideoListVO>> listUnPassVideo(String title);

    /**
     * 插入视频
     *
     * @author Silvery
     * @since 2023/8/25 16:08
     * @param insertVideoDTO 插入视频的信息
     * @return RestBean<Void>
     */
    RestBean<Void> insertVideo(InsertVideoDTO insertVideoDTO);

    /**
     * 更新视频信息
     *
     * @author Silvery
     * @since 2023/8/25 16:09
     * @param updateVideoById 更新视频信息
     * @return RestBean<Void>
     */
    RestBean<Void> updateVideoById(UpdateVideoDTO updateVideoById);

    /**
     * 删除视频信息
     *
     * @author Silvery
     * @since 2023/8/25 16:09
     * @param id 视频id
     * @return RestBean<Void>
     */
    RestBean<Void> deleteVideoById(Integer id);

    /**
     * 通过审核
     *
     * @author Silvery
     * @since 2023/8/25 16:29
     * @param id 视频id
     * @return RestBean<Void>
     */
    RestBean<Void> passVideo(Integer id);

    /**
     * 下架通过审核的视频
     *
     * @author Silvery
     * @since 2023/8/25 16:29
     * @param id 视频id
     * @return RestBean<Void>
     */
    RestBean<Void> unPassVideo(Integer id);

    /**
     * 获取首页轮播图
     *
     * @author Silvery
     * @since 2023/9/5 11:46
     * @return RestBean<BannerVO>
     */
    RestBean<List<BannerVO>> listBanner();

    /**
     * 获取首页的建议
     *
     * @author Silvery
     * @since 2023/9/5 11:46
     * @return RestBean<CardVO>
     */
    RestBean<List<CardVO>> listAdvice();

    /**
     * 获取首页下面的卡片
     *
     * @author Silvery
     * @since 2023/9/5 11:47
     * @return RestBean<CardVO>
     */
    RestBean<List<CardVO>> listCard();

    /**
     * 通过id获取信息
     *
     * @author Silvery
     * @since 2023/9/5 16:43
     * @param id 视频信息id
     * @return RestBean<VideoListVO>
     */
    RestBean<VideoListVO> getCardInfo(Integer id);
}
