package com.greateast.warehouse.controller;

import com.greateast.warehouse.model.entity.Item;
import com.greateast.warehouse.model.entity.Sales;
import com.greateast.warehouse.model.request.ItemRequest;
import com.greateast.warehouse.model.request.PaymentRequest;
import com.greateast.warehouse.model.request.SalesRequest;
import com.greateast.warehouse.model.response.BaseResponseDto;
import com.greateast.warehouse.service.SalesService;
import com.greateast.warehouse.util.logresponsetime.LogExecutionTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for sales business logic.
 */
@RestController
@RequestMapping("/sales/v1")
@RequiredArgsConstructor
@Slf4j
public class SalesController {
    private final SalesService salesService;

    @PostMapping("/order")
    @LogExecutionTime
    public ResponseEntity<BaseResponseDto<Sales>> salesOrder(@RequestBody SalesRequest salesRequest) throws Exception{
        BaseResponseDto<Sales> resp = salesService.salesOrder(salesRequest);
        return ResponseEntity.ok(resp);
    }

    @LogExecutionTime
    @GetMapping("/sales")
    public ResponseEntity<BaseResponseDto<?>> getSales(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        BaseResponseDto<Page<Sales>> resp = salesService.findAll(page, size);
        return ResponseEntity.ok(resp);
    }

    @LogExecutionTime
    @GetMapping("/sales/{id}")
    public  ResponseEntity<BaseResponseDto<Sales>> findSalesById(@PathVariable("id") long id) {
        BaseResponseDto<Sales> resp = salesService.findById(id);
        return ResponseEntity.ok(resp);
    }
}
