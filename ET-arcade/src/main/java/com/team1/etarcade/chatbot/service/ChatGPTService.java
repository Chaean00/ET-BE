package com.team1.etarcade.chatbot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChatGPTService {

    @Value("${openai.api.key}")
    private String apiKey;

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final int MAX_HISTORY = 9;

    private final Map<Long, List<Map<String, String>>> userChatHistory = new ConcurrentHashMap<>();
    private static final Map<String, String> SYSTEM_MESSAGE = Map.of(
            "role", "system",
            "content", "너는 주식 퀴즈에 대한 힌트를 주고 주식 교육을 도와주는 챗봇이야. 이름은 '트레이드타운봇'이야. "
                    + "짧고 간결하게 500 토큰 이내로 이모티콘 많이 쓰고 존댓말로 친절하게 답변해. 만약 중간에 문장을 끊어서 보냈으면 사용자가 너에게 '이어서 얘기해줘'라고 보낼거야. 그러면 끊긴 문장 다음부터 이어서 대답하면 돼."
    );

    public String processUserMessage(Long userId, String userMessage) {
        if (userId == null) {
            return "X-Id 헤더가 필요합니다.";
        }
        if (userMessage == null || userMessage.trim().isEmpty()) {
            return "메시지가 비어 있습니다.";
        }

        List<Map<String, String>> messages = userChatHistory.computeIfAbsent(userId, k -> new ArrayList<>());

        if (messages.isEmpty()) {
            messages.add(SYSTEM_MESSAGE);
        }

        messages.add(Map.of("role", "user", "content", userMessage));
        String response = getChatGPTResponse(messages);
        messages.add(Map.of("role", "assistant", "content", response));

        if (messages.size() > 1 + (MAX_HISTORY * 2)) {
            messages.subList(1, messages.size() - (MAX_HISTORY * 2)).clear();
        }

        System.out.println(messages);

        return response;
    }

    private String getChatGPTResponse(List<Map<String, String>> messages) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> requestBody = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", messages,
                "max_tokens", 500
        );

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        try {
            ResponseEntity<Map> response = restTemplate.exchange(API_URL, HttpMethod.POST, requestEntity, Map.class);
            if (response.getBody() != null) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
                if (choices != null && !choices.isEmpty()) {
                    Map<String, Object> firstChoice = choices.get(0);
                    Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
                    return (String) message.get("content");
                }
            }
        } catch (Exception e) {
            return "응답을 가져오지 못했습니다.";
        }
        return "응답을 가져오지 못했습니다.";
    }
}
