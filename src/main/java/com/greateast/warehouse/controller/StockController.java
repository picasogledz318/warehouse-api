
package com.greateast.warehouse.controller;

import com.greateast.warehouse.service.StockService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for order/stock validation.
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @PostMapping("/validate")
    public String validateStock(@RequestBody StockRequest request) {
        stockService.validateStock(request.variantId, request.quantity);
        return "Stock available";
    }

    /**
     * Simple DTO for stock validation request.
     */
    @Data
    static class StockRequest {
        public Long variantId;
        public int quantity;
    }
}
