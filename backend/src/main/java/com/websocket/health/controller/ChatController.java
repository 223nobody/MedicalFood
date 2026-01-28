package com.websocket.health.controller;

import com.websocket.health.service.Assistant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * AI 聊天控制器
 * 提供药膳健康咨询的 AI 对话接口
 */
@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class ChatController {

    // 注入 AiService 接口（通过 Assistant 提供专业的药膳咨询服务）
    @Autowired
    private Assistant assistant;

    /**
     * AI 智能对话接口
     * 支持多模型选择：千问（Qwen）、DeepSeek、GLM（智谱）
     *
     * @param message 用户输入的问题
     * @param model 选择的 AI 模型（qwen/deepseek/glm），默认为 qwen
     * @return AI 生成的回复内容
     */
    @GetMapping("/chat/ai")
    public String chatWithAiService(
            @RequestParam(value = "message", defaultValue = "如何改善失眠") String message,
            @RequestParam(value = "model", defaultValue = "qwen") String model) {

        // 验证模型参数
        if (!isValidModel(model)) {
            return "错误：不支持的模型类型。请选择 qwen、deepseek 或 glm。";
        }

        // 调用 Assistant 服务进行对话
        // 注意：当前版本暂时使用默认模型，后续可扩展多模型动态切换
        return assistant.chat(message);
    }

    /**
     * 验证模型参数是否有效
     *
     * @param model 模型名称
     * @return 是否为有效的模型
     */
    private boolean isValidModel(String model) {
        return "qwen".equalsIgnoreCase(model)
            || "deepseek".equalsIgnoreCase(model)
            || "glm".equalsIgnoreCase(model);
    }
}