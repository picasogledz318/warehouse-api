
package com.greateast.warehouse.service;

import com.greateast.warehouse.repository.VariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service responsible for stock validation logic.
 */
@Service
@RequiredArgsConstructor
public class StockService {

    private final VariantRepository variantRepository;

    /**
     * Validates whether enough stock exists for a variant.
     * Throws exception if stock is insufficient.
     */
    public void validateStock(Long variantId, int quantity) {
        var variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new IllegalArgumentException("Variant not found"));

        if (variant.getStock() < quantity) {
            throw new IllegalStateException("Insufficient stock");
        }
    }
}
