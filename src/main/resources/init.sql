CREATE DATABASE IF NOT EXISTS video_recommend_system
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_general_ci;

USE video_recommend_system;


CREATE TABLE video_resource (
                                id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',

                                course_name VARCHAR(50) NOT NULL COMMENT '课程名称',
                                video_url VARCHAR(255) NOT NULL COMMENT '视频链接',

                                source_platform VARCHAR(50) NOT NULL COMMENT '来源平台，如B站、YouTube、csdiy.wiki',
                                course_type VARCHAR(50) NOT NULL COMMENT '课程类型，如考研强化、数学基础、数学进阶',

                                status TINYINT DEFAULT 1 COMMENT '资源状态 1可用 0不可用',
                                create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT='专业课视频资源表';


INSERT INTO video_resource
(course_name, video_url, source_platform, course_type)
VALUES
-- ===== 计算机考研强化（B站 王道）=====
('数据结构',
 'https://www.bilibili.com/video/BV1b7411N798?spm_id_from=333.788.videopod.sections&vd_source=c90dd7a7c58ad7efa6456229a647b2d8',
 'B站',
 '考研强化'),

('操作系统',
 'https://www.bilibili.com/video/BV1YE41D7nH?spm_id_from=333.788.videopod.sections&vd_source=c90dd7a7c58ad7efa6456229a647b2d8',
 'B站',
 '考研强化'),

('计算机组成原理',
 'https://www.bilibili.com/video/BV1Bh5YZ3EA?spm_id_from=333.788.videopod.sections&vd_source=c90dd7a7c58ad7efa6456229a647b2d8',
 'B站',
 '考研强化'),

('计算机网络',
 'https://www.bilibili.com/video/BV19E41D78Q?spm_id_from=333.788.videopod.sections&vd_source=c90dd7a7c58ad7efa6456229a647b2d8',
 'B站',
 '考研强化'),

-- ===== 数学基础（MIT / YouTube）=====
('微积分与线性代数',
 'https://csdiy.wiki/%E6%95%B0%E5%AD%A6%E5%9F%BA%E7%A1%80/MITmaths/',
 'MIT',
 '数学基础'),

('微积分与线性代数',
 'https://www.youtube.com/playlist?list=PLZHQObOWTQDMsr9K-rJ53DwVRMYO3t5Yr',
 'YouTube',
 '数学基础'),

('信息论入门',
 'https://csdiy.wiki/%E6%95%B0%E5%AD%A6%E5%9F%BA%E7%A1%80/Information/',
 'MIT',
 '数学基础'),

-- ===== 数学进阶（UC Berkeley / MIT）=====
('离散数学与概率论',
 'https://csdiy.wiki/%E6%95%B0%E5%AD%A6%E8%BF%9B%E9%98%B6/CS70/',
 'UC Berkeley',
 '数学进阶'),

('离散数学与概率论',
 'https://csdiy.wiki/%E6%95%B0%E5%AD%A6%E8%BF%9B%E9%98%B6/CS126/',
 'UC Berkeley',
 '数学进阶'),

('数值分析',
 'https://computationalthinking.mit.edu/Spring21/',
 'MIT',
 '数学进阶');
