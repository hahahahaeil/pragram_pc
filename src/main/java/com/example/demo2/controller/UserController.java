package com.example.demo2.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo2.entity.User;
import com.example.demo2.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户管理控制器
 * </p>
 *
 * @author jerry
 * @since 2025-01-20
 */
@RestController
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 分页查询用户列表
     */
    @GetMapping("/list")
    public Map<String, Object> list(@RequestParam(defaultValue = "1") Integer current,
                                    @RequestParam(defaultValue = "10") Integer size,
                                    @RequestParam(required = false) String keyword) {
        Page<User> page = new Page<>(current, size);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w.like("username", keyword)
                    .or().like("email", keyword)
                    .or().like("phone", keyword));
        }
        
        wrapper.orderByDesc("create_time");
        Page<User> result = userService.page(page, wrapper);
        
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("message", "查询成功");
        map.put("data", result.getRecords());
        map.put("total", result.getTotal());
        return map;
    }

    /**
     * 根据ID查询用户
     */
    @GetMapping("/{id}")
    public Map<String, Object> getById(@PathVariable Integer id) {
        User user = userService.getById(id);
        Map<String, Object> map = new HashMap<>();
        if (user != null) {
            map.put("code", 200);
            map.put("message", "查询成功");
            map.put("data", user);
        } else {
            map.put("code", 404);
            map.put("message", "用户不存在");
        }
        return map;
    }

    /**
     * 新增用户
     */
    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody User user) {
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        if (user.getStatus() == null || user.getStatus().isEmpty()) {
            user.setStatus("正常");
        }
        boolean success = userService.save(user);
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
     * 更新用户
     */
    @PutMapping("/update")
    public Map<String, Object> update(@RequestBody User user) {
        // 如果密码为空，则不更新密码（从数据库获取原密码）
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            User existingUser = userService.getById(user.getId());
            if (existingUser != null) {
                user.setPassword(existingUser.getPassword());
            }
        }
        user.setUpdateTime(LocalDateTime.now());
        boolean success = userService.updateById(user);
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
     * 删除用户
     */
    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable Integer id) {
        boolean success = userService.removeById(id);
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

