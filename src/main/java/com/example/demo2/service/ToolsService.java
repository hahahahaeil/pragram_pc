package com.example.demo2.service;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ToolsService {

    private final JdbcTemplate jdbcTemplate;

    public ToolsService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Tool("你好")
    public String nihaoNumberOne() {
        return "你好，请问有什么能够帮到你";
    }


    @Tool("个性化视频推荐")
    public String recommendByHistory(Integer userId) {
        String sql = """
        SELECT v.title, v.course, v.url
        FROM video v
        JOIN user_history h ON v.id = h.video_id
        WHERE h.user_id = ?
        GROUP BY v.course
        ORDER BY COUNT(*) DESC
        LIMIT 5
    """;

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, userId);

        if (list.isEmpty()) {
            return "暂无你的观看记录，先看看热门视频吧～";
        }

        StringBuilder sb = new StringBuilder("根据你的学习记录推荐：\n");
        for (Map<String, Object> row : list) {
            sb.append("《").append(row.get("title")).append("》")
                    .append("（").append(row.get("course")).append("）")
                    .append("\n链接：").append(row.get("url"))
                    .append("\n\n");
        }
        return sb.toString();
    }



    @Tool("操作系统视频推荐")
    public List<Map<String, String>> osVideos() {
        return List.of(
                Map.of(
                        "title", "操作系统精讲",
                        "source", "王道",
                        "url", "https://www.bilibili.com/video/BV1YE411D7nH"
                )
        );
    }

    @Tool("数据结构视频推荐")
    public List<Map<String, String>> dsVideos() {
        return List.of(
                Map.of(
                        "title", "数据结构精讲",
                        "source", "王道",
                        "url", "https://www.bilibili.com/video/BV1b7411N798"
                )
        );
    }

    @Tool("计算机组成原理视频推荐")
    public List<Map<String, String>> pcVideos() {
        return List.of(
                Map.of(
                        "title", "计算机组成原理精讲",
                        "source", "王道",
                        "url", "https://www.bilibili.com/video/BV1ps4y1d73V"
                )
        );
    }

    @Tool("计算机网络视频推荐")
    public List<Map<String, String>> computerNetworkVideos() {
        return List.of(
                Map.of(
                        "title", "计算机网络精讲",
                        "source", "王道",
                        "url", "https://www.bilibili.com/video/BV19E411D78Q"
                )
        );
    }

    @Tool("微积分与线性代数视频推荐")
    public List<Map<String, String>> mathBaseVideos() {
        return List.of(
                Map.of(
                        "title", "微积分与线性代数",
                        "source", "MIT OpenCourseWare",
                        "url", "https://csdiy.wiki/%E6%95%B0%E5%AD%A6%E5%9F%BA%E7%A1%80/MITmaths/"
                ),
                Map.of(
                        "title", "微积分与线性代数",
                        "source", "YouTube",
                        "url", "https://www.youtube.com/playlist?list=PLZHQObOWTQDMsr9K-rj53DwVRMYO3t5Yr"
                )
        );
    }

    @Tool("离散数学与概率论视频推荐")
    public List<Map<String, String>> discreteMathVideos() {
        return List.of(
                Map.of(
                        "title", "离散数学与概率论",
                        "source", "UC Berkeley CS70",
                        "url", "https://csdiy.wiki/%E6%95%B0%E5%AD%A6%E8%BF%9B%E9%98%B6/CS70/"
                ),
                Map.of(
                        "title", "离散数学与概率论",
                        "source", "UC Berkeley CS126",
                        "url", "https://csdiy.wiki/%E6%95%B0%E5%AD%A6%E8%BF%9B%E9%98%B6/CS126/"
                )
        );
    }

    @Tool("数值分析视频推荐")
    public List<Map<String, String>> numericalAnalysisVideos() {
        return List.of(
                Map.of(
                        "title", "数值分析",
                        "source", "MIT",
                        "url", "https://computationalthinking.mit.edu/Spring21/"
                )
        );
    }

    @Tool("MIT-Missing-Semester视频推荐")
    public List<Map<String, String>> mitMissingSemesterVideos() {
        return List.of(
                Map.of(
                        "title", "MIT Missing Semester",
                        "source", "MIT",
                        "url", "https://csdiy.wiki/%E7%BC%96%E7%A8%8B%E5%85%A5%E9%97%A8/MIT-Missing-Semester/"
                )
        );
    }

    @Tool("CS50视频推荐")
    public List<Map<String, String>> cs50Videos() {
        return List.of(
                Map.of(
                        "title", "CS50: This is CS50x",
                        "source", "Harvard University",
                        "url", "https://csdiy.wiki/%E7%BC%96%E7%A8%8B%E5%85%A5%E9%97%A8/C/CS50/"
                )
        );
    }

    @Tool("MIT 6.092 Java编程入门视频推荐")
    public List<Map<String, String>> mit6092JavaVideos() {
        return List.of(
                Map.of(
                        "title", "MIT 6.092 Java 编程入门",
                        "source", "MIT",
                        "url", "https://csdiy.wiki/%E7%BC%96%E7%A8%8B%E5%85%A5%E9%97%A8/Java/MIT%206.092/"
                )
        );
    }

    @Tool("CS50P Python编程入门视频推荐")
    public List<Map<String, String>> cs50pVideos() {
        return List.of(
                Map.of(
                        "title", "CS50P Python 编程入门",
                        "source", "Harvard University",
                        "url", "https://csdiy.wiki/%E7%BC%96%E7%A8%8B%E5%85%A5%E9%97%A8/Python/CS50P/"
                )
        );
    }



    @Tool("MIT 6.007信号与系统视频推荐")
    public String mit6007Videos() {
        return "MIT 6.007: signals and Systems - MIT " +
                "https://ocw.mit.edu/courses/res-6-007-signals-and-systems-spring-2011/";
    }

    @Tool("UCB CS61B数据结构与算法视频推荐")
    public String ucbCs61bVideos() {
        return "CS61B: Data Structures and Algorithms - UCB " +
                "https://sp18.datastructur.es/";
    }

    @Tool("MIT 6.S081操作系统工程视频推荐")
    public String mit6s081Videos() {
        return "MIT 6.S081: Operating System Engineering - MIT " +
                "https://pdos.csail.mit.edu/6.828/2021/schedule.html";
    }

    @Tool("CMU 15-445数据库系统视频推荐")
    public String cmu15445Videos() {
        return "CMU 15-445: Database Systems - CMU " +
                "https://15445.courses.cs.cmu.edu/fall2020/schedule.html";
    }


    @Tool("Stanford CS229机器学习视频推荐")
    public String stanfordCs229Videos() {
        return "Stanford CS229: Machine Learning - Stanford " +
                "https://cs229.stanford.edu/";
    }

    @Tool("UCB CS162操作系统视频推荐")
    public String ucbCs162Videos() {
        return "UCB CS162: Operating Systems and Systems Programming - UCB " +
                "https://cs162.org/";
    }


    @Tool("Stanford CS144计算机网络视频推荐")
    public String stanfordCs144Videos() {
        return "Stanford CS144: Introduction to Computer Networking - Stanford " +
                "https://cs144.github.io/";
    }

    @Tool("CMU 15-213计算机系统基础视频推荐")
    public String cmu15213Videos() {
        return "CMU 15-213: Introduction to Computer Systems - CMU " +
                "https://www.cs.cmu.edu/~213/";
    }


    @Tool("清华计算机组成原理课程推荐")
    public String tsinghuaCompOrgVideos() {
        return "计算机组成原理 - 清华大学（学堂在线）" +
                "https://www.xuetangx.com/course/THU08091000267/12532845";
    }


    @Tool("北大数据结构与算法（B站）")
    public String pkuDataStructBilibili() {
        return "数据结构与算法 - 北京大学（B站）" +
                "https://www.bilibili.com/video/BV1VC4y1x7uv/";
    }

    @Tool("浙大Python程序设计（B站）")
    public String zjuPythonBilibili() {
        return "Python程序设计基础 - 浙江大学（B站）" +
                "https://www.bilibili.com/video/BV1T7411e7gm/";
    }

    @Tool("上交操作系统（B站）")
    public String sjtuOsBilibili() {
        return "操作系统 - 上海交通大学（B站）" +
                "https://www.bilibili.com/video/BV1B341117Ez/";
    }

    @Tool("中科大计算机网络（B站）")
    public String fudanNetworksBilibili() {
        return "计算机网络 - 中科大（B站）" +
                "https://www.bilibili.com/video/BV1JV411t7ow/";
    }

    @Tool("哈工大C语言程序设计（B站）")
    public String hitCLanguageBilibili() {
        return "C语言程序设计精髓 - 哈尔滨工业大学（B站）" +
                "https://www.bilibili.com/video/BV1CA4m1L7WW/";
    }

    @Tool("人大数据库系统（B站）")
    public String hustDbBilibili() {
        return "数据库系统概论 - 中国人民大学（B站）" +
                "https://www.bilibili.com/video/BV1p1NoeTE89/";
    }

    @Tool("中科大编译原理（B站）")
    public String ustcCompilerBilibili() {
        return "编译原理 - 中国科学技术大学（B站）" +
                "https://www.bilibili.com/video/BV16h411X7JY/";
    }
}
