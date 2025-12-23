package com.example.demo2.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo2.entity.Admin;
import com.example.demo2.service.IAdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 管理员认证控制器（登录）
 * </p>
 *
 * @author jerry
 * @since 2025-01-20
 */
@RestController
@RequestMapping("/api/admin")
public class AdminAuthController {

    @Autowired
    private IAdminService adminService;

    /**
     * 管理员登录
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> params, HttpSession session) {
        String username = params.get("username");
        String password = params.get("password");
//        String code = params.get("code"); // 验证码
        
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

        
        // 查询管理员
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        wrapper.eq("password", password);
        Admin admin = adminService.getOne(wrapper);
        
        if (admin == null) {
            map.put("code", 401);
            map.put("message", "账号或密码错误");
            return map;
        }
        
        // 登录成功，保存到Session
        session.setAttribute("adminId", admin.getId());
        session.setAttribute("adminUsername", admin.getUsername());
        session.setAttribute("userType", "admin");
        
        // 返回管理员信息（不包含密码）
        Map<String, Object> adminInfo = new HashMap<>();
        adminInfo.put("id", admin.getId());
        adminInfo.put("username", admin.getUsername());
        
        map.put("code", 200);
        map.put("message", "登录成功");
        map.put("data", adminInfo);
        return map;
    }

    /**
     * 管理员退出登录
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
     * 检查管理员登录状态
     */
    @GetMapping("/check")
    public Map<String, Object> checkLogin(HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        Object adminId = session.getAttribute("adminId");
        if (adminId != null) {
            map.put("code", 200);
            map.put("loggedIn", true);
            map.put("adminId", adminId);
            map.put("adminUsername", session.getAttribute("adminUsername"));
        } else {
            map.put("code", 401);
            map.put("loggedIn", false);
            map.put("message", "未登录");
        }
        return map;
    }
}

