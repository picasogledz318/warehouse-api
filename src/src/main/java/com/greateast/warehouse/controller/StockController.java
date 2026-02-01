
package com.greateast.warehouse.controller;

import com.greateast.warehouse.constant.TrxCode;
import com.greateast.warehouse.model.response.BaseResponseDto;
import com.greateast.warehouse.service.StockService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<BaseResponseDto<String>> validateStock(
            @RequestBody StockRequest request) {

        BaseResponseDto<String> resp = new BaseResponseDto<>();
        stockService.validateStock(request.variantId, request.quantity);

        resp.setCode(TrxCode.TRX_OK.code());
        resp.setMessage(TrxCode.TRX_OK.description());
        resp.setData("Stock available");

        return ResponseEntity.ok(resp);
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
