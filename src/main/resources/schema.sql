-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
    `id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
    `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    `password` VARCHAR(255) NOT NULL COMMENT '密码',
    `email` VARCHAR(100) COMMENT '邮箱',
    `phone` VARCHAR(20) COMMENT '手机号',
    `status` VARCHAR(20) DEFAULT '正常' COMMENT '状态：正常/已禁用',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_username` (`username`),
    INDEX `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 视频表
CREATE TABLE IF NOT EXISTS `video` (
    `id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '视频ID',
    `title` VARCHAR(200) NOT NULL COMMENT '视频标题',
    `author` VARCHAR(100) NOT NULL COMMENT '作者',
    `category` VARCHAR(50) COMMENT '分类：电影/电视剧/综艺/动漫/纪录片/音乐',
    `duration` VARCHAR(20) COMMENT '时长',
    `play_count` INT DEFAULT 0 COMMENT '播放量',
    `status` VARCHAR(20) DEFAULT '待审核' COMMENT '状态：已发布/待审核',
    `cover_url` VARCHAR(500) COMMENT '封面URL',
    `video_url` VARCHAR(500) COMMENT '视频URL',
    `description` TEXT COMMENT '视频介绍',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_title` (`title`),
    INDEX `idx_author` (`author`),
    INDEX `idx_category` (`category`),
    INDEX `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='视频表';

-- 管理员表
CREATE TABLE IF NOT EXISTS `admin` (
    `id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '管理员ID',
    `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '管理员账号',
    `password` VARCHAR(255) NOT NULL COMMENT '密码',
    INDEX `idx_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员表';

-- 插入默认管理员账号（账号：admin，密码：admin123）
INSERT INTO `admin` (`username`, `password`) VALUES ('admin', 'admin123')
ON DUPLICATE KEY UPDATE `username`=`username`;

