package com.greateast.warehouse.repository;

import com.greateast.warehouse.model.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

/**
    Repository for Sales entity.
 */
public interface SalesRepository extends JpaRepository<Sales, Long> {
}
