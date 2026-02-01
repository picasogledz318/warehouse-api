
package com.greateast.warehouse.service;

import com.greateast.warehouse.constant.TrxCode;
import com.greateast.warehouse.model.entity.Variant;
import com.greateast.warehouse.model.response.BaseResponseDto;
import com.greateast.warehouse.repository.VariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service responsible for stock validation logic.
 */
@Service
@RequiredArgsConstructor
public class StockService {

    private final VariantService variantService;

    /**
     * Validates whether enough stock exists for a variant.
     * return response insufficient unit if stock is lesser than quantity, return data not found if out of stock.
     * else return message stock available!
     */
    public BaseResponseDto<?> validateStock(long itemId, long variantId, int quantity) {
        BaseResponseDto<?> resp = new BaseResponseDto<>();
        Variant variant = variantService.findByItemIdAndId(itemId, variantId).getData();
        if(variant == null){
            resp.setCode(TrxCode.TRX_NOT_FOUND.code());
            resp.setMessage(TrxCode.TRX_NOT_FOUND.description());
            resp.setData(null);
            resp.setErrors(null);
        } else if (variant.getStock() < quantity) {
            resp.setCode(TrxCode.TRX_CAMNOT_PROCEED.code());
            resp.setMessage(TrxCode.TRX_CAMNOT_PROCEED.description());
            resp.setData(null);
            resp.setErrors(null);
        } else{
            resp.setCode(TrxCode.TRX_OK.code());
            resp.setMessage("Stock available");
            resp.setData(null);
            resp.setErrors(null);
        }
        return resp;
    }
}
