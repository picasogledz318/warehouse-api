
package com.greateast.warehouse.model.request;

import com.greateast.warehouse.model.auditrail.Auditable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * VariantRequest represents a sellable version of an item variant's request.
 * Example: T-Shirt size M color Red
 */

@Data
public class VariantRequest extends Auditable {

    private Long id;
    private Long itemId;

    // Stock keeping unit
    private String sku;

    private String color;
    private String size;

    // Capital Price of this variant
    private BigDecimal capitalPrice;

    // Sales Price of this variant
    private BigDecimal salesPrice;

    // Current available stock
    private Integer stock;

}
