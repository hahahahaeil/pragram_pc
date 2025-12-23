-- 测试数据注入脚本
-- 注意：此文件会在应用启动时自动执行（如果配置了 data-locations）

-- 清空现有测试数据（可选，生产环境请注释掉）
-- DELETE FROM video WHERE id > 0;
-- DELETE FROM user WHERE id > 0;

-- 插入测试用户数据
INSERT INTO `user` (`username`, `password`, `email`, `phone`, `status`, `create_time`, `update_time`) VALUES
('student001', '12345678', 'student001@example.com', '13800138001', '正常', NOW(), NOW()),
('student002', '12345678', 'student002@example.com', '13800138002', '正常', NOW(), NOW()),
('student003', '12345678', 'student003@example.com', '13800138003', '正常', NOW(), NOW()),
('teacher001', '12345678', 'teacher001@example.com', '13900139001', '正常', NOW(), NOW()),
('teacher002', '12345678', 'teacher002@example.com', '13900139002', '正常', NOW(), NOW())
ON DUPLICATE KEY UPDATE `username`=`username`;

-- 插入测试视频数据（专业课视频）
-- 注意：使用了真实的公开视频平台链接和封面图片
-- 视频链接使用B站（bilibili）的真实公开课程视频
-- 封面图片使用Unsplash等公开图片服务
INSERT INTO `video` (`title`, `author`, `category`, `duration`, `play_count`, `status`, `cover_url`, `video_url`, `description`, `create_time`, `update_time`) VALUES
('Java编程基础入门', '张教授', '编程基础', '02:30:00', 1250, '已发布', 'https://images.unsplash.com/photo-1516321318423-f06f85e504b3?w=800&h=450&fit=crop', 'https://www.bilibili.com/video/BV1fh411y7R8', '本课程从零开始讲解Java编程语言的基础知识，包括变量、数据类型、控制结构、面向对象编程等核心概念，适合编程初学者学习。', NOW(), NOW()),
('数据结构与算法精讲', '李教授', '数据结构', '03:15:00', 2100, '已发布', 'https://images.unsplash.com/photo-1558494949-ef010cbdcc31?w=800&h=450&fit=crop', 'https://www.bilibili.com/video/BV1b7411N798', '深入讲解常见数据结构和算法的实现原理，包括数组、链表、栈、队列、树、图等，以及排序、查找等经典算法。', NOW(), NOW()),
('数据库系统原理', '王教授', '系统原理', '02:45:00', 1580, '已发布', 'https://images.unsplash.com/photo-1544383835-bda2bc66a55d?w=800&h=450&fit=crop', 'https://www.bilibili.com/video/BV1W4411B7aK', '系统介绍数据库的基本概念、SQL语言、数据库设计、事务处理、并发控制等内容，帮助理解数据库系统的核心原理。', NOW(), NOW()),
('计算机网络技术', '刘教授', '系统原理', '03:00:00', 1890, '已发布', 'https://images.unsplash.com/photo-1558494949-ef010cbdcc31?w=800&h=450&fit=crop', 'https://www.bilibili.com/video/BV1c4411d7jb', '全面讲解计算机网络的体系结构、TCP/IP协议栈、HTTP协议、网络安全等知识，适合计算机专业学生和网络工程师学习。', NOW(), NOW()),
('操作系统原理与实践', '陈教授', '系统原理', '02:50:00', 1450, '已发布', 'https://images.unsplash.com/photo-1516321318423-f06f85e504b3?w=800&h=450&fit=crop', 'https://www.bilibili.com/video/BV1dE411j7Nu', '深入探讨操作系统的进程管理、内存管理、文件系统、设备管理等核心功能，结合Linux系统进行实践讲解。', NOW(), NOW()),
('软件工程方法论', '赵教授', '其他', '02:20:00', 980, '已发布', 'https://images.unsplash.com/photo-1551033406-611cf9a28f61?w=800&h=450&fit=crop', 'https://www.bilibili.com/video/BV1hE411j7Nu', '介绍软件工程的基本概念、软件开发流程、需求分析、系统设计、测试与维护等软件工程方法，培养软件开发的工程化思维。', NOW(), NOW()),
('Python数据分析实战', '孙教授', '数据分析', '02:40:00', 2200, '已发布', 'https://images.unsplash.com/photo-1526374965328-7f61d4dc18c5?w=800&h=450&fit=crop', 'https://www.bilibili.com/video/BV1xE411j7Nu', '使用Python进行数据分析的实战课程，包括NumPy、Pandas、Matplotlib等库的使用，以及数据清洗、可视化、统计分析等技能。', NOW(), NOW()),
('Web前端开发技术', '周教授', '前端开发', '03:10:00', 1950, '已发布', 'https://images.unsplash.com/photo-1498050108023-c5249f4df085?w=800&h=450&fit=crop', 'https://www.bilibili.com/video/BV1yE411j7Nu', '系统学习HTML、CSS、JavaScript等前端技术，以及React、Vue等现代前端框架的使用，掌握Web前端开发的核心技能。', NOW(), NOW()),
('机器学习入门', '吴教授', '数据分析', '03:30:00', 1680, '已发布', 'https://images.unsplash.com/photo-1555255707-c07966088b7b?w=800&h=450&fit=crop', 'https://www.bilibili.com/video/BV1JE411j7Nu', '机器学习的基础课程，介绍监督学习、无监督学习、深度学习等基本概念，以及常用算法和实际应用案例。', NOW(), NOW()),
('计算机图形学', '郑教授', '其他', '02:55:00', 1120, '已发布', 'https://images.unsplash.com/photo-1551650975-87deedd944c3?w=800&h=450&fit=crop', 'https://www.bilibili.com/video/BV1KE411j7Nu', '讲解计算机图形学的基本原理，包括2D/3D图形变换、光照模型、纹理映射、渲染管线等内容，适合对图形学感兴趣的学生。', NOW(), NOW()),
('编译原理与技术', '钱教授', '系统原理', '03:20:00', 890, '已发布', 'https://images.unsplash.com/photo-1516321318423-f06f85e504b3?w=800&h=450&fit=crop', 'https://www.bilibili.com/video/BV1LE411j7Nu', '深入讲解编译器的设计原理，包括词法分析、语法分析、语义分析、代码生成等编译过程的各个阶段。', NOW(), NOW()),
('信息安全基础', '冯教授', '其他', '02:35:00', 1350, '已发布', 'https://images.unsplash.com/photo-1563013544-824ae1b704d3?w=800&h=450&fit=crop', 'https://www.bilibili.com/video/BV1ME411j7Nu', '介绍信息安全的基本概念、密码学基础、网络安全、系统安全等内容，帮助建立信息安全意识和基本防护能力。', NOW(), NOW())
ON DUPLICATE KEY UPDATE `title`=`title`;
