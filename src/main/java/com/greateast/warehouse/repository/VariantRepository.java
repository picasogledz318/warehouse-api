
package com.greateast.warehouse.repository;

import com.greateast.warehouse.model.request.Variant;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Variant entity.
 */
public interface VariantRepository extends JpaRepository<Variant, Long> {
}
