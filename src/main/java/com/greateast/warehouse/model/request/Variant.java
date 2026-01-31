
package com.greateast.warehouse.model.request;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

/**
 * Variant represents a sellable version of an item.
 * Example: T-Shirt size M color Red
 */
@Entity
@Data
public class Variant {

    @Id
    @GeneratedValue
    private Long id;

    // Stock keeping unit
    private String sku;

    private String color;
    private String size;

    // Price of this variant
    private BigDecimal price;

    // Current available stock
    private Integer stock;

    /**
     * Many variants belong to one item.
     */
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
}
