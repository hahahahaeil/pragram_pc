package com.example.demo2.service.impl;

import com.example.demo2.entity.Admin;
import com.example.demo2.mapper.AdminMapper;
import com.example.demo2.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员服务实现类
 * </p>
 *
 * @author jerry
 * @since 2025-01-20
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

}

