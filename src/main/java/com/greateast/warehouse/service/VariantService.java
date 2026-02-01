
package com.greateast.warehouse.service;

import com.greateast.warehouse.constant.TrxCode;
import com.greateast.warehouse.model.entity.Variant;
import com.greateast.warehouse.model.request.VariantRequest;
import com.greateast.warehouse.model.response.BaseResponseDto;
import com.greateast.warehouse.repository.VariantRepository;
import com.greateast.warehouse.repository.VariantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service layer for Variant business logic.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class VariantService {

    private final VariantRepository variantRepository;

    /**
     * Create or update variant.
     */
    public BaseResponseDto<Variant> save(VariantRequest variantRequest) {
        BaseResponseDto<Variant> resp = new BaseResponseDto<>();
        Variant data = new Variant();
        try{
            BeanUtils.copyProperties(variantRequest, data);
            Variant result = variantRepository.save(data);
            log.info("variant saved:{}",result);
            resp.setCode(TrxCode.TRX_SAVED.code());
            resp.setData(result);
            resp.setErrors(null);
            resp.setMessage(TrxCode.TRX_SAVED.description());
        }catch (Exception err){
            log.error("Error saveVariant:{}, trace:{}",err.getMessage(), err.getStackTrace());
            throw new RuntimeException("Error saveVariant:"+err.getMessage());

        }
        return resp;
    }

    /**
     * Bulk save variants.
     */
    public BaseResponseDto<?> saveAll(List<VariantRequest> requests) {
        BaseResponseDto<?> resp = new BaseResponseDto<>();
        try{
            List<Variant> entities = requests.stream().map(req -> {
                Variant v = new Variant();
                v.setItemId(req.getItemId());
                v.setSku(req.getSku());
                v.setColor(req.getColor());
                v.setSize(req.getSize());
                v.setSalesPrice(req.getPrice());
                v.setStock(req.getStock());
                return v;
            }).toList();

            variantRepository.saveAll(entities);
            log.info("All variant has been saved:{}",entities);
            resp.setCode(TrxCode.TRX_SAVED.code());
            resp.setData(null);
            resp.setErrors(null);
            resp.setMessage(TrxCode.TRX_SAVED.description());
        }catch (Exception err){
            log.error("Error saveAllVariants:{}, trace:{}",err.getMessage(), err.getStackTrace());
            throw new RuntimeException("Error saveAllVariants:"+err.getMessage());

        }
        return resp;
    }

    public BaseResponseDto<Variant> update(long id,  VariantRequest variantRequest) {
        BaseResponseDto<Variant> resp = new BaseResponseDto<>();
        Variant existingVariant = null;
        List<String> listError = new ArrayList<>();
        try{
            existingVariant = findById(id).getData();
            if(existingVariant != null){
                variantRequest.setId(existingVariant.getId());
                resp.setCode(TrxCode.TRX_OK.code());
                resp.setData(save(variantRequest).getData());
                resp.setErrors(null);
                resp.setMessage("Variant id "+id+" successfully updated!");
            } else {
                resp.setCode(TrxCode.TRX_NOT_FOUND.code());
                resp.setMessage(TrxCode.TRX_NOT_FOUND.description() + "by Id: "+id);
                resp.setData(null);
                resp.setErrors(null);
            }
            return resp;
        }catch (Exception err){
            log.error("Error updateVariant:{}, trace:{}",err.getMessage(), err.getStackTrace());
            throw new RuntimeException("Error updateVariant:"+err.getMessage());
        }

    }

    /**
     * Delete by Id.
     */
    public BaseResponseDto<?> deleteById(long id) {
        Boolean isVariantExist = existsById(id);
        BaseResponseDto<Variant> resp = new BaseResponseDto<>();
        try{
            if(isVariantExist){
                deleteById(id);
                resp.setCode(TrxCode.TRX_DELETED.code());
                resp.setData(null);
                resp.setErrors(null);
                resp.setMessage("Item id "+id+" "+TrxCode.TRX_DELETED);

            } else {
                resp.setCode(TrxCode.TRX_NOT_FOUND.code());
                resp.setMessage(TrxCode.TRX_NOT_FOUND.description());
                resp.setData(null);
                resp.setErrors(null);
            }
            return resp;
        }catch (Exception err){
            log.error("Error delete variant:{}, trace:{}",err.getMessage(), err.getStackTrace());
            throw new RuntimeException("Error delete variant:"+err.getMessage());
        }

    }

    /**
     * Retrieve all variants with pagination.
     */
    public BaseResponseDto<Page<Variant>> findAll(int page, int size) {

        BaseResponseDto<Page<Variant>> resp = new BaseResponseDto<>();
        try{
            Page<Variant> pageVariant = variantRepository.findAll(PageRequest.of(page, size));
            resp.setCode(TrxCode.TRX_OK.code());
            resp.setData(pageVariant);
            resp.setErrors(null);
            if(pageVariant != null && !pageVariant.isEmpty()) resp.setMessage("All variants");
            else resp.setMessage("No Data");
        }catch (Exception err){
            log.error("Error find all variants:{}, trace:{}",err.getMessage(), err.getStackTrace());
            throw new RuntimeException("Error find all variants:"+err.getMessage());
        }
        return resp ;
    }

    /**
     * Find variant by id.
     */
    public BaseResponseDto<Variant> findById(long id) {
        BaseResponseDto<Variant> resp = new BaseResponseDto<>();
        boolean isVariantExist = existsById(id);
        if(isVariantExist){
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
     * Check variant existence by id.
     */
    public Boolean existsById(long id) {
        return variantRepository.existsById(id);
    }


}
