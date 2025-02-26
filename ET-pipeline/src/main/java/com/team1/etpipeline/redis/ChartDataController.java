package com.team1.etpipeline.redis;

import com.team1.etpipeline.redis.dto.StockResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class ChartDataController {
    private final ChartDataService chartDataService;

    @GetMapping("/{stockId}")
    public StockResponseDto getStock(@PathVariable String stockId) {
        return chartDataService.getStockData(stockId);
    }
}
