package com.bilibili.commons.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.commons.domain.RestBean;
import com.bilibili.commons.domain.dto.InsertDanmakuDTO;
import com.bilibili.commons.domain.dto.UpdateDanmakuDTO;
import com.bilibili.commons.domain.entity.Danmaku;
import com.bilibili.commons.domain.vo.DanmakuListVO;

import java.util.List;


/**
 * 弹幕(Danmaku)表服务接口
 *
 * @author Silvery
 * @since 2023-08-28 16:55:01
 */
public interface DanmakuService extends IService<Danmaku> {

    /**
     * 获取弹幕列表
     *
     * @author Silvery
     * @since 2023/8/28 17:15
     * @return RestBean<List<DanmakuListVO>>
     */
    RestBean<List<DanmakuListVO>> listDanmaku();

    /**
     * 新增弹幕
     *
     * @author Silvery
     * @since 2023/8/28 17:15
     * @param insertDanmakuDTO 新增的弹幕信息
     * @return RestBean<Void>
     */
    RestBean<Void> insertDanmaku(InsertDanmakuDTO insertDanmakuDTO);

    /**
     * 更新弹幕
     *
     * @author Silvery
     * @since 2023/8/28 17:15
     * @param updateDanmakuDTO 更新的信息
     * @return RestBean<Void>
     */
    RestBean<Void> updateDanmakuById(UpdateDanmakuDTO updateDanmakuDTO);

    /**
     * 删除弹幕信息
     *
     * @author Silvery
     * @since 2023/8/28 17:16
     * @param id 删除的id
     * @return RestBean<Void>
     */
    RestBean<Void> deleteDanmakuById(Integer id);

    /**
     * 根据视频id获取弹幕列表
     *
     * @author Silvery
     * @since 2023/8/28 17:16
     * @param id 视频id
     * @return RestBean<List<DanmakuListVO>>
     */
    RestBean<List<DanmakuListVO>> listDanmakuByVideoId(Integer id);
}
