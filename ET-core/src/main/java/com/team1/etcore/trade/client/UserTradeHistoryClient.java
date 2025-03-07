package com.team1.etcore.trade.client;

import com.team1.etcore.trade.dto.TradeReq;
import com.team1.etcore.trade.dto.TradeRes;
import com.team1.etcore.trade.dto.TradeStatus;
import com.team1.etcore.trade.dto.Position;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@FeignClient(name = "ET-user")
public interface UserTradeHistoryClient {

    /**
     * 금액이 충분한지 예치금 조회
     */
    @GetMapping("/api/users/feign/account/enough")
    boolean enoughDeposit(@RequestParam("userId") Long userId, @RequestParam("amount") BigDecimal amount);

    /**
     * 주문 생성
     */
    @PostMapping("/api/users/feign/trade/order")
    TradeRes createOrder(@RequestParam("userId") Long userId, @RequestBody TradeReq tradeReq);

    /**
     * 주문상태 업데이트
     */
    @PutMapping("/api/users/feign/trade/order/update")
    boolean updateHistoryStatus(@RequestParam("orderId") Long historyId,
                                @RequestParam("status") TradeStatus tradeStatus);

    /**
     * 사용자 예치금 업데이트
     */
    @PutMapping("/api/users/feign/account/update")
    boolean updateDeposit(@RequestParam("userId") Long userId, @RequestParam("amount") BigDecimal amount);

    /**
     * 보유 주식 업데이트 API
     */
    @PostMapping("/api/users/feign/stock/update")
    boolean updateUserStock(@RequestParam("userId") Long userId,
                            @RequestParam("stockCode") String stockCode,
                            @RequestParam("amount") int amount,
                            @RequestParam("price") BigDecimal price,
                            @RequestParam("position") Position position);
}
