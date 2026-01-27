package com.websocket.health.controller;

import com.websocket.health.service.Assistant;
import dev.langchain4j.model.chat.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    // 方式1：直接注入 ChatModel（底层 API 调用）
    private final ChatModel chatModel;

    // 方式2：注入 AiService 接口（简化调用）
    @Autowired
    private Assistant assistant;

    // 构造函数注入 ChatModel（Spring 自动装配，无需 @Autowired）
    public ChatController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    // 接口1：直接调用大模型（路径：/chat/hello）
    @GetMapping("/chat/hello")
    public String chatWithModel(
            @RequestParam(value = "message", defaultValue = "推荐一份健康早餐") String message) {
        return chatModel.chat(message);
    }

    // 接口2：通过 AiService 调用（路径：/chat/ai）
    @GetMapping("/chat/ai")
    public String chatWithAiService(
            @RequestParam(value = "message", defaultValue = "如何改善失眠") String message) {
        return assistant.chat(message);
    }
}