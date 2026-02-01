
package com.greateast.warehouse.repository;

import com.greateast.warehouse.model.entity.Variant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for Variant entity.
 */
public interface VariantRepository extends JpaRepository<Variant, Long> {
    boolean existsByItemId(long id);
    boolean existsByItemIdAndId(long itemId, long id);
    Variant findByItemIdAndId(long itemId, long id);
    List<Variant> findById(long id);
    List<Variant> findByItemId(long id);
    void deleteByIdAndItemId(long id, long itemId);
}
