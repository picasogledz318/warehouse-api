package com.greateast.warehouse.model.request;

import lombok.Data;

import java.math.BigDecimal;

/**
 * SalesRequest represents item's sales request from warehouse.
 * Example: Sales for many items such as: manuT-Shirt variant Black color, Shoes variant Adidas, Laptop variant Lenovo
 */
@Data
public class PaymentRequest {

    private String currency;
    private BigDecimal totalPayment;
    private String remark;

}
