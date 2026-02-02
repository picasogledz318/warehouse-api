
package com.greateast.warehouse.service;

import com.greateast.warehouse.constant.TrxCode;
import com.greateast.warehouse.model.entity.Variant;
import com.greateast.warehouse.model.response.BaseResponseDto;
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
    public BaseResponseDto<Variant> validateStock(long itemId, long variantId, int quantity) {
        BaseResponseDto<Variant> resp = new BaseResponseDto<>();
        Variant variant = variantService.findByItemIdAndId(itemId, variantId).getData();
        if(variant == null){
            resp.setCode(TrxCode.TRX_NOT_FOUND.code());
            resp.setMessage(TrxCode.TRX_NOT_FOUND.description());
            resp.setData(null);
            resp.setErrors(null);
        } else if (variant.getStock() < quantity) {
            resp.setCode(TrxCode.TRX_INSUFFICIENT_STOCK.code());
            resp.setMessage(TrxCode.TRX_INSUFFICIENT_STOCK.description());
            resp.setData(null);
            resp.setErrors(null);
        } else{
            resp.setCode(TrxCode.TRX_STOCK_AVAILABLE.code());
            resp.setMessage(TrxCode.TRX_STOCK_AVAILABLE.description());
            resp.setData(variant);
            resp.setErrors(null);
        }
        return resp;
    }
}
