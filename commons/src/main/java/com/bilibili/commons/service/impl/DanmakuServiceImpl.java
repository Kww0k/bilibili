package com.bilibili.commons.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.commons.cache.DanmakuCache;
import com.bilibili.commons.domain.RestBean;
import com.bilibili.commons.domain.dto.InsertDanmakuDTO;
import com.bilibili.commons.domain.dto.UpdateDanmakuDTO;
import com.bilibili.commons.domain.entity.Danmaku;
import com.bilibili.commons.domain.vo.DanmakuListVO;
import com.bilibili.commons.exctption.video.DanmakuNotFindException;
import com.bilibili.commons.mapper.DanmakuMapper;
import com.bilibili.commons.service.DanmakuService;
import com.bilibili.commons.utils.BeanCopyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.bilibili.commons.constants.AppConstants.FALSE_CODE;

/**
 * 弹幕(Danmaku)表服务实现类
 *
 * @author Silvery
 * @since 2023-08-28 16:55:01
 */
@Service("danmakuService")
@RequiredArgsConstructor
public class DanmakuServiceImpl extends ServiceImpl<DanmakuMapper, Danmaku> implements DanmakuService {

    private final BeanCopyUtils beanCopyUtils;

    private final DanmakuCache danmakuCache;

    @Override
    public RestBean<List<DanmakuListVO>> listDanmaku() {
        return RestBean.success(danmakuCache.getList()
                .stream()
                .map(danmaku -> beanCopyUtils.copyBean(danmaku, DanmakuListVO.class))
                .toList());
    }

    @Override
    public RestBean<Void> insertDanmaku(InsertDanmakuDTO insertDanmakuDTO) {
        Danmaku danmaku = beanCopyUtils.copyBean(insertDanmakuDTO, Danmaku.class);
        baseMapper.insert(danmaku);
        danmakuCache.save(danmaku);
        return RestBean.success();
    }

    @Override
    public RestBean<Void> updateDanmakuById(UpdateDanmakuDTO updateDanmakuDTO) {
        Danmaku danmaku = beanCopyUtils.copyBean(updateDanmakuDTO, Danmaku.class);
        if (baseMapper.updateById(danmaku) == FALSE_CODE)
            throw new DanmakuNotFindException();
        danmakuCache.save(danmaku);
        return RestBean.success();
    }

    @Override
    public RestBean<Void> deleteDanmakuById(Integer id) {
        if (baseMapper.deleteById(id) == FALSE_CODE)
            throw new DanmakuNotFindException();
        danmakuCache.delete(id);
        return RestBean.success();
    }

    @Override
    public RestBean<List<DanmakuListVO>> listDanmakuByVideoId(Integer id) {
        return RestBean.success(danmakuCache.getList()
                .stream()
                .filter(danmaku -> Objects.equals(danmaku.getVideoId(), id))
                .map(danmaku -> beanCopyUtils.copyBean(danmaku, DanmakuListVO.class))
                .toList());
    }
}
