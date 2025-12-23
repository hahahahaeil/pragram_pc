package com.example.demo2.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo2.entity.Video;
import com.example.demo2.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 视频管理控制器
 * </p>
 *
 * @author jerry
 * @since 2025-01-20
 */
@RestController
@RequestMapping("/admin/video")
public class VideoController {

    @Autowired
    private IVideoService videoService;

    /**
     * 分页查询视频列表
     */
    @GetMapping("/list")
    public Map<String, Object> list(@RequestParam(defaultValue = "1") Integer current,
                                    @RequestParam(defaultValue = "10") Integer size,
                                    @RequestParam(required = false) String keyword) {
        Page<Video> page = new Page<>(current, size);
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w.like("title", keyword)
                    .or().like("author", keyword));
        }
        
        wrapper.orderByDesc("create_time");
        Page<Video> result = videoService.page(page, wrapper);
        
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("message", "查询成功");
        map.put("data", result.getRecords());
        map.put("total", result.getTotal());
        return map;
    }

    /**
     * 根据ID查询视频
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
     * 新增视频
     */
    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody Video video) {
        video.setCreateTime(LocalDateTime.now());
        video.setUpdateTime(LocalDateTime.now());
        if (video.getStatus() == null || video.getStatus().isEmpty()) {
            video.setStatus("已发布");
        }
        if (video.getPlayCount() == null) {
            video.setPlayCount(0);
        }
        boolean success = videoService.save(video);
        Map<String, Object> map = new HashMap<>();
        if (success) {
            map.put("code", 200);
            map.put("message", "添加成功");
        } else {
            map.put("code", 500);
            map.put("message", "添加失败");
        }
        return map;
    }

    /**
     * 更新视频
     */
    @PutMapping("/update")
    public Map<String, Object> update(@RequestBody Video video) {
        video.setUpdateTime(LocalDateTime.now());
        boolean success = videoService.updateById(video);
        Map<String, Object> map = new HashMap<>();
        if (success) {
            map.put("code", 200);
            map.put("message", "更新成功");
        } else {
            map.put("code", 500);
            map.put("message", "更新失败");
        }
        return map;
    }

    /**
     * 视频上下架
     */
    @PutMapping("/{id}/status")
    public Map<String, Object> updateStatus(@PathVariable Integer id, @RequestBody Map<String, String> params) {
        String status = params.get("status");
        Map<String, Object> map = new HashMap<>();
        
        if (status == null || (!"已发布".equals(status) && !"待审核".equals(status) && !"已下架".equals(status))) {
            map.put("code", 400);
            map.put("message", "状态参数错误");
            return map;
        }
        
        Video video = videoService.getById(id);
        if (video == null) {
            map.put("code", 404);
            map.put("message", "视频不存在");
            return map;
        }
        
        video.setStatus(status);
        video.setUpdateTime(LocalDateTime.now());
        boolean success = videoService.updateById(video);
        
        if (success) {
            map.put("code", 200);
            map.put("message", "操作成功");
        } else {
            map.put("code", 500);
            map.put("message", "操作失败");
        }
        return map;
    }

    /**
     * 删除视频
     */
    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable Integer id) {
        boolean success = videoService.removeById(id);
        Map<String, Object> map = new HashMap<>();
        if (success) {
            map.put("code", 200);
            map.put("message", "删除成功");
        } else {
            map.put("code", 500);
            map.put("message", "删除失败");
        }
        return map;
    }
}

