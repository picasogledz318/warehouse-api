
package com.greateast.warehouse.model.request;

import com.greateast.warehouse.model.auditrail.Auditable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * ItemRequest represents request of a new/product in the warehouse.
 * Example: T-Shirt, Shoes, Laptop
 */
@Data
public class ItemRequest extends Auditable {

    private Long id;

    // Item name
    private String name;

    // Item description
    private String description;

    private List<VariantRequest> variants;
}
