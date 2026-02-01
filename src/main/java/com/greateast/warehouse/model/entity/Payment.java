package com.greateast.warehouse.model.entity;

import com.greateast.warehouse.model.auditrail.Auditable;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Sales represent sales data in warehouse
 */
@Entity
@Table(name="Payment")
@Data
public class Payment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "sales_id", nullable = false)
    private Long salesId;

    private BigDecimal totalPayment;

    private BigDecimal changeAmount;

    private String paymentStatus;

    private String currency;

    private String remark;

}
