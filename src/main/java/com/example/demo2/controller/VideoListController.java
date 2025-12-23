package com.example.demo2.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo2.entity.Video;
import com.example.demo2.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 视频列表控制器（供前端首页使用）
 * </p>
 *
 * @author jerry
 * @since 2025-01-20
 */
@RestController
@RequestMapping("/api/video")
public class VideoListController {

    @Autowired
    private IVideoService videoService;

    /**
     * 获取视频列表（首页使用）
     */
    @GetMapping("/list")
    public Map<String, Object> list(@RequestParam(defaultValue = "1") Integer current,
                                     @RequestParam(defaultValue = "12") Integer size,
                                     @RequestParam(required = false) String category,
                                     @RequestParam(required = false) String keyword) {
        Page<Video> page = new Page<>(current, size);
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        
        // 只显示已发布的视频
        wrapper.eq("status", "已发布");
        
        // 分类筛选
        if (category != null && !category.isEmpty() && !"全部".equals(category) && !"推荐".equals(category)) {
            wrapper.eq("category", category);
        }
        
        // 关键词搜索
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w.like("title", keyword)
                    .or().like("author", keyword)
                    .or().like("description", keyword));
        }
        
        wrapper.orderByDesc("create_time");
        Page<Video> result = videoService.page(page, wrapper);
        
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("message", "查询成功");
        map.put("data", result.getRecords());
        map.put("total", result.getTotal());
        map.put("current", result.getCurrent());
        map.put("size", result.getSize());
        map.put("pages", result.getPages());
        return map;
    }

    /**
     * 根据ID获取视频详情
     */
    @GetMapping("/{id}")
    public Map<String, Object> getById(@PathVariable Integer id) {
        Video video = videoService.getById(id);
        Map<String, Object> map = new HashMap<>();
        if (video != null) {
            map.put("code", 200);
            map.put("message", "查询成功");
            map.put("data", video);
        } else {
            map.put("code", 404);
            map.put("message", "视频不存在");
        }
        return map;
    }

    /**
     * 根据标题获取视频详情（用于详情页）
     */
    @GetMapping("/detail")
    public Map<String, Object> getByTitle(@RequestParam String title) {
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("title", title);
        Video video = videoService.getOne(wrapper);
        Map<String, Object> map = new HashMap<>();
        if (video != null) {
            map.put("code", 200);
            map.put("message", "查询成功");
            map.put("data", video);
        } else {
            map.put("code", 404);
            map.put("message", "视频不存在");
        }
        return map;
    }
}

