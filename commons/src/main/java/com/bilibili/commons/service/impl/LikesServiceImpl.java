package com.bilibili.commons.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.commons.domain.entity.Likes;
import com.bilibili.commons.mapper.LikesMapper;
import com.bilibili.commons.service.LikesService;
import org.springframework.stereotype.Service;

/**
 * (Likes)表服务实现类
 *
 * @author Silvery
 * @since 2023-09-15 16:01:38
 */
@Service("likesService")
public class LikesServiceImpl extends ServiceImpl<LikesMapper, Likes> implements LikesService {

}
