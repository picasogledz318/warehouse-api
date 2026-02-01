package com.greateast.warehouse.model.request;

import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;

/**
 * SalesRequest represents item's sales request from warehouse.
 * Example: Sales for many items such as: manuT-Shirt variant Black color, Shoes variant Adidas, Laptop variant Lenovo
 */
@Data
public class SalesRequest {

    private Long itemId;

    private Long variantId;

    private int orderQuantity;

    private BigDecimal totalOrderPrice;
    private BigDecimal totalPayment;
    private BigDecimal changeAmount;


    private String trxStatus;

    private String remark;

}
