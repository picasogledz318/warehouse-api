
package com.greateast.warehouse.service;

import com.greateast.warehouse.constant.TrxCode;
import com.greateast.warehouse.constant.TrxStatus;
import com.greateast.warehouse.model.entity.Payment;
import com.greateast.warehouse.model.entity.Sales;
import com.greateast.warehouse.model.entity.Variant;
import com.greateast.warehouse.model.request.PaymentRequest;
import com.greateast.warehouse.model.request.SalesRequest;
import com.greateast.warehouse.model.response.BaseResponseDto;
import com.greateast.warehouse.repository.PaymentRepository;
import com.greateast.warehouse.repository.SalesRepository;
import com.greateast.warehouse.repository.VariantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Service responsible for sales payment logic.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final SalesRepository salesRepository;
    private final PaymentRepository paymentRepository;

    private final VariantService variantService;
    private final VariantRepository variantRepository;

    /**
     * Sales payment from sales order.
     * Check if sales data exist by salesId then process payment and return salesResponse with transaction status SUCCESS (Paid)
     * else return sales data not found
     * Throwable any exceptions.
     */
    public BaseResponseDto<?> payment(long salesId, PaymentRequest paymentRequest) {
        BaseResponseDto<Payment> resp = new BaseResponseDto<>();
        BaseResponseDto<Sales> salesResp = new BaseResponseDto<>();
        Sales sales = null; Payment payment = new Payment();
        try{
            sales = salesRepository.findById(salesId) != null && !salesRepository.findById(salesId).isEmpty() ? salesRepository.findById(salesId).get() : null;
            if(sales != null){
                BigDecimal totalChange = paymentRequest.getTotalPayment().subtract(sales.getTotalOrderPrice());
                if(totalChange.compareTo(BigDecimal.ZERO) >= 0){
                    BeanUtils.copyProperties(paymentRequest, payment);
                    payment.setPaymentStatus(TrxStatus.SUCCESS.name());
                    payment.setChangeAmount(totalChange);
                    payment.setSalesId(salesId);
                    payment = paymentRepository.save(payment);
                    log.info("Payment saved:{}",payment);

                    //update sales data
                    sales.setPaymentId(payment.getId());
                    sales.setTrxStatus(TrxStatus.SUCCESS.name());
                    sales = salesRepository.save(sales);
                    log.info("Sales updated:{}",sales);

                    //update stock
                    //if variant still exists then update variant/unit quantity after payment process success
                    Variant targetVariant = variantService.findByItemIdAndId(sales.getItemId(), sales.getVariantId()).getData();
                    log.info("targetVariant: {}",targetVariant);
                    if(targetVariant!= null){
                        int remainingStock = targetVariant.getStock() - sales.getOrderQuantity();
                        targetVariant.setStock(remainingStock);
                        variantRepository.save(targetVariant);
                        log.info("Variant after sales got updated:{}",targetVariant);
                    }

                    //return payment response
                    resp.setCode(TrxCode.TRX_SUCCESS.code());
                    sales.setTrxStatus(TrxStatus.PENDING.name());
                    resp.setData(payment);
                    resp.setErrors(null);
                    resp.setMessage(TrxCode.TRX_SUCCESS.description());
                } else {
                    salesResp.setCode(TrxCode.TRX_INSUFFICIENT_PAYMENT.code());
                    salesResp.setData(sales);
                    salesResp.setErrors(null);
                    salesResp.setMessage(TrxCode.TRX_INSUFFICIENT_PAYMENT.description() + " for transaction salesId: "+salesId);
                    return salesResp;
                }
            } else {
                salesResp.setCode(TrxCode.TRX_NOT_FOUND.code());
                salesResp.setData(sales);
                salesResp.setErrors(null);
                salesResp.setMessage(TrxCode.TRX_NOT_FOUND.description());
                return salesResp;
            }
        }catch (Exception err){
            log.error("Error sales:{}, trace:{}",err.getMessage(), err.getStackTrace());
            throw new RuntimeException("Error sales:"+err.getMessage());
        }

        return  resp;
    }

    /**
     * Retrieve all payments with pagination.
     */
    public BaseResponseDto<Page<Payment>> findAll(int page, int size) {

        BaseResponseDto<Page<Payment>> resp = new BaseResponseDto<>();
        try{
            Page<Payment> pagePayments = paymentRepository.findAll(PageRequest.of(page, size));
            resp.setCode(TrxCode.TRX_OK.code());
            resp.setData(pagePayments);
            resp.setErrors(null);
            if(pagePayments != null && !pagePayments.isEmpty()) resp.setMessage("All Payments");
            else resp.setMessage("No Data");
        }catch (Exception err){
            log.error("Error find all payments:{}, trace:{}",err.getMessage(), err.getStackTrace());
            throw new RuntimeException("Error find all payments:"+err.getMessage());
        }
        return resp ;
    }

    /**
     * Find payments by id.
     */
    public BaseResponseDto<Payment> findById(long id) {
        BaseResponseDto<Payment> resp = new BaseResponseDto<>();
        boolean isPaymentExist = existsById(id);
        if(isPaymentExist){
            resp.setCode(TrxCode.TRX_OK.code());
            resp.setData(paymentRepository.findById(id).get());
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
     * Check payment existence by id.
     */
    public Boolean existsById(long id) {
        return paymentRepository.existsById(id);
    }


}
