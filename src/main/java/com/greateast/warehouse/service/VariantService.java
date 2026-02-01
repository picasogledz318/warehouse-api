
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
                if(req.getId() != null) v.setId(req.getId());
                v.setItemId(req.getItemId());
                v.setSku(req.getSku());
                v.setColor(req.getColor());
                v.setSize(req.getSize());
                v.setCapitalPrice(req.getCapitalPrice());
                v.setSalesPrice(req.getSalesPrice());
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

    public BaseResponseDto<Variant> update(long itemId, long id, VariantRequest variantRequest) {
        BaseResponseDto<Variant> resp = new BaseResponseDto<>();
        Variant existingVariant = null;
        List<String> listError = new ArrayList<>();
        try{
            variantRequest.setItemId(itemId);
            variantRequest.setId(id);
            existingVariant = variantRepository.findByItemIdAndId(itemId, id);
            if(existingVariant != null){
                variantRequest.setId(existingVariant.getId());
                resp.setCode(TrxCode.TRX_OK.code());
                resp.setData(save(variantRequest).getData());
                resp.setErrors(null);
                resp.setMessage("Variant id: "+id+", successfully updated!");
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
     * Delete variant by Id.
     */
    public BaseResponseDto<?> deleteById(long id) {
        Boolean isVariantExist = existsById(id);
        BaseResponseDto<Variant> resp = new BaseResponseDto<>();
        try{
            if(isVariantExist){
                variantRepository.deleteById(id);
                resp.setCode(TrxCode.TRX_DELETED.code());
                resp.setData(null);
                resp.setErrors(null);
                resp.setMessage("Variant id: "+id+" "+TrxCode.TRX_DELETED);

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
     * Delete variant by itemId and id.
     */
    public BaseResponseDto<?> deleteByItemIdAndId(long itemId, long id) {
        Boolean isVariantExist = existsByItemIdAndId(itemId, id);
        BaseResponseDto<Variant> resp = new BaseResponseDto<>();
        try{
            if(isVariantExist){
                variantRepository.deleteByIdAndItemId(itemId, id);
                resp.setCode(TrxCode.TRX_DELETED.code());
                resp.setData(null);
                resp.setErrors(null);
                resp.setMessage("Variant by itemId: "+itemId+" and variantId: "+id+", "+TrxCode.TRX_DELETED.description());

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
    public BaseResponseDto<List<Variant>> findById(long id) {
        BaseResponseDto<List<Variant>> resp = new BaseResponseDto<>();
        boolean isVariantExist = existsById(id);
        if(isVariantExist){
            resp.setCode(TrxCode.TRX_OK.code());
            resp.setData(variantRepository.findById(id).stream().toList());
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
     * Find variant by itemId.
     */
    public BaseResponseDto<List<Variant>> findByItemId(long itemId) {
        BaseResponseDto<List<Variant>> resp = new BaseResponseDto<>();
        boolean isVariantExist = existsByItemId(itemId);
        if(isVariantExist){
            resp.setCode(TrxCode.TRX_OK.code());
            resp.setData(variantRepository.findByItemId(itemId));
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
     * Find variant by itemId and variantId.
     */
    public BaseResponseDto<Variant> findByItemIdAndId(long itemId, long id) {
        BaseResponseDto<Variant> resp = new BaseResponseDto<>();
        boolean isVariantExist = existsByItemIdAndId(itemId, id);
        Variant searchVariant = null;
        if(isVariantExist){
            searchVariant = variantRepository.findByItemIdAndId(itemId, id);
            resp.setCode(TrxCode.TRX_OK.code());
            resp.setData(searchVariant);
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

    /**
     * Check variant existence by itemId.
     */
    public Boolean existsByItemId(long id) {
        return variantRepository.existsByItemId(id);
    }

    /**
     * Check variant existence by itemId and Id.
     */
    public Boolean existsByItemIdAndId(long itemId, long id) {
        return variantRepository.existsByItemIdAndId(itemId, id);
    }


}
