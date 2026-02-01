package com.greateast.warehouse.model.entity;

import com.greateast.warehouse.model.auditrail.Auditable;
import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Sales represent sales data in warehouse
 */
@Entity
@Table(name="Sales")
@Data
public class Sales extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Column(name = "variant_id", nullable = false)
    private Long variantId;

    private int orderQuantity;

    private BigDecimal totalPriceOrder;

    private String trxStatus;

    private String remark;

    @Column(name = "payment_id", nullable = false)
    private Long paymentId;


    ;

}
