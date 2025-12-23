package com.example.demo2.service;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Service;

@Service
public class ToolsService {
    @Tool("广州有多少个名字的")
    public Integer guangZhouNameCount(@P("姓名") String name) {
        return 10000;
    }
    @Tool("谁的武功天下第一")
    public String KongfuNumberOne() {
        return "李崇铭";
    }

}
