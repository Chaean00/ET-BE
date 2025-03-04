package com.team1.etcore.stock.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SseService {
    private final Map<String, List<SseEmitter>> interestSubscribers = new ConcurrentHashMap<>();//현재가
    private final Map<String, List<SseEmitter>> portfolioSubscribers = new ConcurrentHashMap<>();//현재가
    private final Map<String, List<SseEmitter>> askBidSubscribers = new ConcurrentHashMap<>();//현재가

    public SseEmitter getInterestStockPrice(String userId) {//관심종목 현재가
        SseEmitter emitter = new SseEmitter(200_000L); // 60초 타임아웃
        //이부분은 for 문으로 userId 로 종목들 조회해서 맵에 넣어야될듯
        interestSubscribers.computeIfAbsent("005930", k -> new ArrayList<>()).add(emitter);

        //sse emitter는 사용자
        /**
         * 키값 변경예정 db요청해서 값 받아서
         */
        emitter.onCompletion(() -> interestSubscribers.get("005930").remove(emitter));
        emitter.onTimeout(() -> interestSubscribers.get("005930").remove(emitter));

        return emitter;
    }

    public void sendToClientsInterestStockPrice(String stockCode, Object data) {

        List<SseEmitter> emitters = interestSubscribers.getOrDefault(stockCode, new ArrayList<>());

        for (SseEmitter emitter : emitters) {
            try {
                // 객체를 JSON 문자열로 변환
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonData = objectMapper.writeValueAsString(data);

                // JSON 문자열을 보내기
                emitter.send(SseEmitter.event().data(jsonData));
            } catch (IOException e) {
                emitter.complete();
            }
        }
    }

    public SseEmitter getPortfolioStockPrice(String userId) {//관심종목 현재가
        SseEmitter emitter = new SseEmitter(200_000L); // 60초 타임아웃
        //이부분은 for 문으로 userId 로 종목들 조회해서 맵에 넣어야될듯
        portfolioSubscribers.computeIfAbsent("005930", k -> new ArrayList<>()).add(emitter);

        //sse emitter는 사용자
        /**
         * 키값 변경예정 db요청해서 값 받아서
         */
        emitter.onCompletion(() -> portfolioSubscribers.get("005930").remove(emitter));
        emitter.onTimeout(() -> portfolioSubscribers.get("005930").remove(emitter));

        return emitter;
    }

    public void sendToClientsPortfolioStockPrice(String stockCode, Object data) {

        List<SseEmitter> emitters = portfolioSubscribers.getOrDefault(stockCode, new ArrayList<>());

        for (SseEmitter emitter : emitters) {
            try {
                // 객체를 JSON 문자열로 변환
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonData = objectMapper.writeValueAsString(data);

                // JSON 문자열을 보내기
                emitter.send(SseEmitter.event().data(jsonData));
            } catch (IOException e) {
                emitter.complete();
            }
        }
    }



}
