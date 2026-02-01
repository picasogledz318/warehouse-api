
package com.greateast.warehouse.controller;

import com.greateast.warehouse.constant.TrxCode;
import com.greateast.warehouse.model.request.StockRequest;
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
@RequestMapping("/stock/v1")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @PostMapping("/validate")
    public ResponseEntity<BaseResponseDto<?>> validateStock(
            @RequestBody StockRequest request) {
        BaseResponseDto<?> resp = stockService.validateStock(request.itemId, request.variantId, request.quantity);
        return ResponseEntity.ok(resp);
    }

}
