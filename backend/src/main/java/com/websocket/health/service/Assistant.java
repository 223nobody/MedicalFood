package com.websocket.health.service;


import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

// 标记为 AI 服务，自动被 Spring 管理
@AiService
public interface Assistant {
    // 系统提示词：定义助手性格（礼貌回应）
    @SystemMessage("You are a polite assistant focused on health-related questions.")
    String chat(String userMessage);  // 接收用户消息，返回 AI 回复
}
