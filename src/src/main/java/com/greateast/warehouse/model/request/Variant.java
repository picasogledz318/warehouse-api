package com.greateast.warehouse.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "variant")
@Data
public class Variant extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_id", nullable = false)
    private Long itemId;

    private String sku;
    private String color;
    private String size;
    private BigDecimal price;
    private Integer stock;
}