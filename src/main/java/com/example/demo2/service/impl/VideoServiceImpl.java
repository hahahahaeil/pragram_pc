package com.example.demo2.service.impl;

import com.example.demo2.entity.Video;
import com.example.demo2.mapper.VideoMapper;
import com.example.demo2.service.IVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 视频服务实现类
 * </p>
 *
 * @author jerry
 * @since 2025-01-20
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements IVideoService {

}

