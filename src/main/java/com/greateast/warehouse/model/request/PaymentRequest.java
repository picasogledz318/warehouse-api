package com.greateast.warehouse.model.request;

import com.greateast.warehouse.model.auditrail.Auditable;
import lombok.Data;

import java.math.BigDecimal;

/**
 * PaymentRequest represents sales incoming payment during sales transaction.
 * Example: Payment of Sales from many items such as: manuT-Shirt variant Black color, Shoes variant Adidas, Laptop variant Lenovo
 */
@Data
public class PaymentRequest extends Auditable {

    private String currency;
    private BigDecimal totalPayment;
    private String remark;

}
