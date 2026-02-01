
package com.greateast.warehouse.model.response;

import com.greateast.warehouse.model.auditrail.Auditable;
import com.greateast.warehouse.model.entity.Variant;
import com.greateast.warehouse.model.request.VariantRequest;
import lombok.Data;
import org.hibernate.query.Page;

import java.util.List;

/**
 * ItemResponse represents a new/product created Item included variants in the warehouse.
 * Example: T-Shirt, Shoes, Laptop include with their variants
 */
@Data
public class ItemResponse extends Auditable {

    private Long id;

    // Item name
    private String name;

    // Item description
    private String description;

    private List<Variant> variants;
}
