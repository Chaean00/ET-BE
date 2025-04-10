package com.team1.etcore.trade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettlementReq {
    private Long userId;
    private Long historyId;
    private String stockCode;
    private Position position;
    private BigDecimal orderPrice;
    private BigDecimal orderAmount;
    private Position orderPosition;
}
