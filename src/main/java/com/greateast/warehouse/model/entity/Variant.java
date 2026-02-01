
package com.greateast.warehouse.model.entity;

import com.greateast.warehouse.model.auditrail.Auditable;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Variant represents a sellable version of an item.
 * Example: T-Shirt size M color Red
 */
@Entity
@Table(name="variant")
@Data
public class Variant extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_id", nullable = false)
    private Long itemId;
    // Stock keeping unit
    private String sku;

    private String color;
    private String size;

    // Capital price of this variant
    private BigDecimal capitalPrice;

    // Sales Price of this variant
    private BigDecimal salesPrice;

    // Current available stock
    private Integer stock;

}
