package com.greateast.warehouse.repository;

import com.greateast.warehouse.model.entity.Payment;
import com.greateast.warehouse.model.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

/**
    Repository for Payment entity.
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
