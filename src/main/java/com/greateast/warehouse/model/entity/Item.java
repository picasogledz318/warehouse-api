
package com.greateast.warehouse.model.entity;

import com.greateast.warehouse.model.auditrail.Auditable;
import jakarta.persistence.*;
import lombok.Data;

/**
 * Item represents a product in the warehouse.
 * Example: T-Shirt, Shoes, Laptop
 */
@Entity
@Table(name="item")
@Data
public class Item extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Item name
    private String name;

    // Item description
    private String description;

}
