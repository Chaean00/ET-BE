package com.team1.etcore.trade.client;

import com.team1.etcore.stock.dto.UserFavoriteStocksRes;
import com.team1.etcore.stock.dto.UserStocksRes;
import com.team1.etcore.trade.dto.TradeReq;
import com.team1.etcore.trade.dto.TradeRes;
import com.team1.etcore.trade.dto.TradeStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

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


    @GetMapping("/api/users/stocks")
    ResponseEntity<List<UserStocksRes>> getUserStocks(@RequestHeader("X-Id") String userId);

    @GetMapping("/api/users/favorite")
    ResponseEntity<List<UserFavoriteStocksRes>> getUserFavoriteStocks(@RequestHeader("X-Id") String userId);

}
