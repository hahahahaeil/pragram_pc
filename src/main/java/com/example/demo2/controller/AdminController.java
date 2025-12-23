package com.example.demo2.controller;

import com.example.demo2.entity.Admin;
import com.example.demo2.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 管理员控制器
 * </p>
 *
 * @author jerry
 * @since 2025-01-20
 */
@RestController
@RequestMapping("/admin/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    /**
     * 获取管理员信息（默认ID为1）
     */
    @GetMapping("/info")
    public Map<String, Object> getInfo() {
        Admin admin = adminService.getById(1);
        Map<String, Object> map = new HashMap<>();
        if (admin != null) {
            map.put("code", 200);
            map.put("message", "查询成功");
            map.put("data", admin);
        } else {
            map.put("code", 404);
            map.put("message", "管理员不存在");
        }
        return map;
    }

    /**
     * 更新管理员信息
     */
    @PutMapping("/update")
    public Map<String, Object> update(@RequestBody Admin admin) {
        admin.setId(1); // 默认更新ID为1的管理员
        
        // 如果密码为空，则不更新密码（从数据库获取原密码）
        if (admin.getPassword() == null || admin.getPassword().isEmpty()) {
            Admin existingAdmin = adminService.getById(1);
            if (existingAdmin != null) {
                admin.setPassword(existingAdmin.getPassword());
            }
        }
        
        boolean success = adminService.updateById(admin);
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
}

