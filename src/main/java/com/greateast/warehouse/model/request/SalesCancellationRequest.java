package com.greateast.warehouse.model.request;

import com.greateast.warehouse.model.auditrail.Auditable;
import lombok.Data;

import java.math.BigDecimal;

/**
 * SalesCancellation Request represents cancellation sales request from warehouse.
 * cancellation by salesId with remark
 */
@Data
public class SalesCancellationRequest extends Auditable {

    private Long salesId;

    private String remark;

}
