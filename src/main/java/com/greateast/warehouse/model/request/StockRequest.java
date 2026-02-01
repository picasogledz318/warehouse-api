package com.greateast.warehouse.model.request;

import com.greateast.warehouse.model.auditrail.Auditable;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Simple DTO for stock validation request.
 */
@Data
public class StockRequest {
    public Long itemId;
    public Long variantId;
    public int quantity;
}
