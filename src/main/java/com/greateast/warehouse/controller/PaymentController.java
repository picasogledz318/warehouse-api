package com.greateast.warehouse.controller;

import com.greateast.warehouse.model.entity.Payment;
import com.greateast.warehouse.model.request.PaymentRequest;
import com.greateast.warehouse.model.response.BaseResponseDto;
import com.greateast.warehouse.service.PaymentService;
import com.greateast.warehouse.service.PaymentService;
import com.greateast.warehouse.util.logresponsetime.LogExecutionTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for payment business logic.
 */
@RestController
@RequestMapping("/payments/v1")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/payment/{salesId}")
    @LogExecutionTime
    public ResponseEntity<BaseResponseDto<?>> payment(@PathVariable("salesId") long salesId, @RequestBody PaymentRequest paymentRequest) throws Exception{
        BaseResponseDto<?> resp = paymentService.payment(salesId, paymentRequest);
        return ResponseEntity.ok(resp);
    }

    @LogExecutionTime
    @GetMapping("/payments")
    public ResponseEntity<BaseResponseDto<?>> getPayments(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        BaseResponseDto<Page<Payment>> resp = paymentService.findAll(page, size);
        return ResponseEntity.ok(resp);
    }

    @LogExecutionTime
    @GetMapping("/payments/{id}")
    public  ResponseEntity<BaseResponseDto<Payment>> findPaymentById(@PathVariable("id") long id) {
        BaseResponseDto<Payment> resp = paymentService.findById(id);
        return ResponseEntity.ok(resp);
    }
}
