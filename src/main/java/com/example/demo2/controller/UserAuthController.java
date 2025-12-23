package com.example.demo2.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo2.entity.User;
import com.example.demo2.service.IUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户认证控制器（登录、注册）
 * </p>
 *
 * @author jerry
 * @since 2025-01-20
 */
@RestController
@RequestMapping("/api/user")
public class UserAuthController {

    @Autowired
    private IUserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        String confirmPassword = params.get("confirmPassword");
        
        Map<String, Object> map = new HashMap<>();
        
        // 验证参数
        if (username == null || username.trim().isEmpty()) {
            map.put("code", 400);
            map.put("message", "账号不能为空");
            return map;
        }
        
        if (password == null || password.trim().isEmpty()) {
            map.put("code", 400);
            map.put("message", "密码不能为空");
            return map;
        }
        
        if (!password.equals(confirmPassword)) {
            map.put("code", 400);
            map.put("message", "两次输入的密码不一致");
            return map;
        }
        
        // 检查用户名是否已存在
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User existUser = userService.getOne(wrapper);
        if (existUser != null) {
            map.put("code", 400);
            map.put("message", "该账号已存在");
            return map;
        }
        
        // 创建新用户
        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // 实际项目中应该加密
        user.setStatus("正常");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        
        boolean success = userService.save(user);
        if (success) {
            map.put("code", 200);
            map.put("message", "注册成功");
        } else {
            map.put("code", 500);
            map.put("message", "注册失败");
        }
        return map;
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> params, HttpSession session) {
        String username = params.get("username");
        String password = params.get("password");
        
        Map<String, Object> map = new HashMap<>();
        
        // 验证参数
        if (username == null || username.trim().isEmpty()) {
            map.put("code", 400);
            map.put("message", "账号不能为空");
            return map;
        }
        
        if (password == null || password.trim().isEmpty()) {
            map.put("code", 400);
            map.put("message", "密码不能为空");
            return map;
        }
        
        // 查询用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        wrapper.eq("password", password);
        User user = userService.getOne(wrapper);
        
        if (user == null) {
            map.put("code", 401);
            map.put("message", "账号或密码错误");
            return map;
        }
        
        if (!"正常".equals(user.getStatus())) {
            map.put("code", 403);
            map.put("message", "账号已被禁用");
            return map;
        }
        
        // 登录成功，保存到Session
        session.setAttribute("userId", user.getId());
        session.setAttribute("username", user.getUsername());
        session.setAttribute("userType", "user");
        
        // 返回用户信息（不包含密码）
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", user.getId());
        userInfo.put("username", user.getUsername());
        userInfo.put("email", user.getEmail());
        userInfo.put("phone", user.getPhone());
        
        map.put("code", 200);
        map.put("message", "登录成功");
        map.put("data", userInfo);
        return map;
    }

    /**
     * 用户退出登录
     */
    @PostMapping("/logout")
    public Map<String, Object> logout(HttpSession session) {
        session.invalidate();
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("message", "退出成功");
        return map;
    }

    /**
     * 检查用户登录状态
     */
    @GetMapping("/check")
    public Map<String, Object> checkLogin(HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        Object userId = session.getAttribute("userId");
        if (userId != null) {
            map.put("code", 200);
            map.put("loggedIn", true);
            map.put("userId", userId);
            map.put("username", session.getAttribute("username"));
        } else {
            map.put("code", 401);
            map.put("loggedIn", false);
            map.put("message", "未登录");
        }
        return map;
    }

    /**
     * 获取当前登录用户信息
     */
    @GetMapping("/current")
    public Map<String, Object> getCurrentUser(HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        Object userId = session.getAttribute("userId");
        if (userId == null) {
            map.put("code", 401);
            map.put("message", "未登录");
            return map;
        }
        
        User user = userService.getById((Integer) userId);
        if (user == null) {
            map.put("code", 404);
            map.put("message", "用户不存在");
            return map;
        }
        
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", user.getId());
        userInfo.put("username", user.getUsername());
        userInfo.put("email", user.getEmail());
        userInfo.put("phone", user.getPhone());
        userInfo.put("status", user.getStatus());
        userInfo.put("createTime", user.getCreateTime());
        
        map.put("code", 200);
        map.put("message", "查询成功");
        map.put("data", userInfo);
        return map;
    }

    /**
     * 用户自己修改个人信息
     */
    @PutMapping("/profile/update")
    public Map<String, Object> updateProfile(@RequestBody Map<String, String> params, HttpSession session) {
        Object userId = session.getAttribute("userId");
        Map<String, Object> map = new HashMap<>();
        
        if (userId == null) {
            map.put("code", 401);
            map.put("message", "未登录");
            return map;
        }
        
        User user = userService.getById((Integer) userId);
        if (user == null) {
            map.put("code", 404);
            map.put("message", "用户不存在");
            return map;
        }
        
        // 更新用户信息
        if (params.containsKey("email")) {
            user.setEmail(params.get("email"));
        }
        if (params.containsKey("phone")) {
            user.setPhone(params.get("phone"));
        }
        if (params.containsKey("password") && params.get("password") != null && !params.get("password").isEmpty()) {
            user.setPassword(params.get("password"));
        }
        
        user.setUpdateTime(LocalDateTime.now());
        boolean success = userService.updateById(user);
        
        if (success) {
            map.put("code", 200);
            map.put("message", "更新成功");
        } else {
            map.put("code", 500);
            map.put("message", "更新失败");
        }
        return map;
    }
}

