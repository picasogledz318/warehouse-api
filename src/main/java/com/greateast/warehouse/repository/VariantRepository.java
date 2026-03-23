
package com.greateast.warehouse.repository;

import com.greateast.warehouse.model.entity.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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
    @Modifying
    @Transactional
    @Query("DELETE FROM Variant v WHERE v.id = :id AND v.itemId = :itemId")
    int deleteVariantByIdAndItemId(@Param("id") long id, @Param("itemId") long itemId);
}
