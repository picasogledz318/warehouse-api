
package com.greateast.warehouse.model.request;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

/**
 * Item represents a product in the warehouse.
 * Example: T-Shirt, Shoes, Laptop
 */
@Entity
@Data
public class Item extends Auditable {

    @Id
    @GeneratedValue
    private Long id;

    // Item name
    private String name;

    // Item description
    private String description;

    /**
     * One item can have multiple variants.
     * Cascade ensures variants are saved together with the item.
     */
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Variant> variants;
}
