package com.example.demo2.controller;

import dev.langchain4j.service.TokenStream;
import com.example.demo2.config.AiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
public class ChatController {

    @Autowired
    private AiConfig.Assistant assistant;


    @GetMapping("/chat")
    public Flux<String>chat(@RequestParam(name = "memoryId",defaultValue = "default",required = false) String memoryId,
                            @RequestParam(defaultValue = "我是谁",name = "message", required = false) String message) {
// 调用助手服务的流式接口，获取TokenStream对象
        TokenStream stream = assistant.stream(memoryId,message);
        return Flux.create(sink -> {
            stream.onPartialResponse(sink::next)
                    .onCompleteResponse(s ->sink.complete())
                    .onError(sink::error)
                    .start(); // 启动流处理
        });
    }




}
