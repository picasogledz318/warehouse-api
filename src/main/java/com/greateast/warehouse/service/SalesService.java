
package com.greateast.warehouse.service;

import com.greateast.warehouse.constant.TrxCode;
import com.greateast.warehouse.constant.TrxStatus;
import com.greateast.warehouse.model.entity.Item;
import com.greateast.warehouse.model.entity.Payment;
import com.greateast.warehouse.model.entity.Sales;
import com.greateast.warehouse.model.entity.Variant;
import com.greateast.warehouse.model.request.PaymentRequest;
import com.greateast.warehouse.model.request.SalesRequest;
import com.greateast.warehouse.model.response.BaseResponseDto;
import com.greateast.warehouse.repository.SalesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Service responsible for sales logic.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SalesService {

    private final SalesRepository salesRepository;
    private final StockService stockService;

    /**
     * Sales order from stock in warehouse.
     * return if stock sufficient then return salesResponse with transaction status pending (waiting for payment)
     * Throws exception if stock is insufficient.
     */
    public BaseResponseDto<Sales> salesOrder(SalesRequest salesRequest) {
        BaseResponseDto<Sales> resp = new BaseResponseDto<>();
        try{
            stockService.validateStock(salesRequest.getItemId() ,salesRequest.getVariantId(), salesRequest.getOrderQuantity());
            Sales sales = new Sales();
            BeanUtils.copyProperties(salesRequest, sales);
            sales = salesRepository.save(sales);
            log.info("Sales saved:{}",sales);
            resp.setCode(TrxCode.TRX_PENDING.code());
            sales.setTrxStatus(TrxStatus.PENDING.name());
            resp.setData(sales);
            resp.setErrors(null);
            resp.setMessage(TrxCode.TRX_PENDING.description());
        }catch (Exception err){
            log.error("Error sales:{}, trace:{}",err.getMessage(), err.getStackTrace());
            throw new RuntimeException("Error sales:"+err.getMessage());
        }

        return  resp;
    }




    /**
     * Retrieve all sales with pagination.
     */
    public BaseResponseDto<Page<Sales>> findAll(int page, int size) {

        BaseResponseDto<Page<Sales>> resp = new BaseResponseDto<>();
        try{
            Page<Sales> pageSales = salesRepository.findAll(PageRequest.of(page, size));
            resp.setCode(TrxCode.TRX_OK.code());
            resp.setData(pageSales);
            resp.setErrors(null);
            if(pageSales != null && !pageSales.isEmpty()) resp.setMessage("All Sales");
            else resp.setMessage("No Data");
        }catch (Exception err){
            log.error("Error find all sales:{}, trace:{}",err.getMessage(), err.getStackTrace());
            throw new RuntimeException("Error find all sales:"+err.getMessage());
        }
        return resp ;
    }

    /**
     * Find sales by id.
     */
    public BaseResponseDto<Sales> findById(long id) {
        BaseResponseDto<Sales> resp = new BaseResponseDto<>();
        boolean isSalesExist = existsById(id);
        if(isSalesExist){
            resp.setCode(TrxCode.TRX_OK.code());
            resp.setData(findById(id).getData());
            resp.setErrors(null);
            resp.setMessage("Data Found!");
        } else {
            resp.setCode(TrxCode.TRX_NOT_FOUND.code());
            resp.setData(null);
            resp.setErrors(null);
            resp.setMessage(TrxCode.TRX_NOT_FOUND.description());
        }

        return resp;
    }

    /**
     * Check sales existence by id.
     */
    public Boolean existsById(long id) {
        return salesRepository.existsById(id);
    }


}
