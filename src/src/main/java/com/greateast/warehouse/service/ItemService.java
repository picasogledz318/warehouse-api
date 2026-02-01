
package com.greateast.warehouse.service;

import com.greateast.warehouse.constant.TrxCode;
import com.greateast.warehouse.model.request.Item;
import com.greateast.warehouse.model.request.Variant;
import com.greateast.warehouse.model.response.BaseResponseDto;
import com.greateast.warehouse.repository.ItemRepository;
import com.greateast.warehouse.repository.VariantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service layer for Item business logic.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;
    private final VariantService variantService;

    /**
     * Create or update item.
     */
    public BaseResponseDto<Item> save(Item item) {
        BaseResponseDto<Item> resp = new BaseResponseDto<>();
        try{
            Item result = itemRepository.save(item);
            log.info("item saved:{}",result);
            for (Variant variant: item.getVariants()) {
                //variant.setItemId(item.getId());
                variantService.save(variant);
                log.info("variant saved:{}",variant);
            }
            resp.setCode(TrxCode.TRX_SAVED.code());
            resp.setData(result);
            resp.setErrors(null);
            resp.setMessage(TrxCode.TRX_SAVED.description());
        }catch (Exception err){
            log.error("Error saveItem:{}, trace:{}",err.getMessage(), err.getStackTrace());
            throw new RuntimeException("Error saveItem:"+err.getMessage());

        }
        return resp;
    }

    public BaseResponseDto<Item> update(long id,  Item item) {
        BaseResponseDto<Item> resp = new BaseResponseDto<>();
        Item existingItem = null;
        List<String> listError = new ArrayList<>();
        try{
            existingItem = findById(id).getData();
            if(existingItem != null){
                item.setId(existingItem.getId());
                resp.setCode(TrxCode.TRX_OK.code());
                resp.setData(save(item).getData());
                resp.setErrors(null);
                resp.setMessage("Item id "+id+" successfully updated!");
            } else {
                resp.setCode(TrxCode.TRX_NOT_FOUND.code());
                resp.setMessage(TrxCode.TRX_NOT_FOUND.description() + "by Id: "+id);
                resp.setData(null);
                resp.setErrors(null);
            }
            return resp;
        }catch (Exception err){
            log.error("Error updateItem:{}, trace:{}",err.getMessage(), err.getStackTrace());
            throw new RuntimeException("Error updateItem:"+err.getMessage());
        }

    }

    /**
     * Delete by Id.
     */
    public BaseResponseDto<?> deleteById(long id) {
        Boolean isItemExist = existsById(id);
        BaseResponseDto<Item> resp = new BaseResponseDto<>();
        try{
            if(isItemExist){
                deleteById(id);
                resp.setCode(TrxCode.TRX_OK.code());
                resp.setData(null);
                resp.setErrors(null);
                resp.setMessage("Item id "+id+" successfully deleted!");

            } else {
                resp.setCode(TrxCode.TRX_NOT_FOUND.code());
                resp.setMessage(TrxCode.TRX_NOT_FOUND.description());
                resp.setData(null);
                resp.setErrors(null);
            }
            return resp;
        }catch (Exception err){
            log.error("Error delete item:{}, trace:{}",err.getMessage(), err.getStackTrace());
            throw new RuntimeException("Error delete item:"+err.getMessage());
        }

    }

    /**
     * Retrieve all items with pagination.
     */
    public BaseResponseDto<Page<Item>> findAll(int page, int size) {

        BaseResponseDto<Page<Item>> resp = new BaseResponseDto<>();
        try{
            Page<Item> pageItem = itemRepository.findAll(PageRequest.of(page, size));
            resp.setCode(TrxCode.TRX_OK.code());
            resp.setData(pageItem);
            resp.setErrors(null);
            if(pageItem != null && !pageItem.isEmpty()) resp.setMessage("All items");
            else resp.setMessage("No Data");
        }catch (Exception err){
            log.error("Error find all items:{}, trace:{}",err.getMessage(), err.getStackTrace());
            throw new RuntimeException("Error find all items:"+err.getMessage());
        }
        return resp ;
    }

    /**
     * Find item by id.
     */
    public BaseResponseDto<Item> findById(long id) {
        BaseResponseDto<Item> resp = new BaseResponseDto<>();
        boolean isItemExist = existsById(id);
        if(isItemExist){
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
     * Check item existence by id.
     */
    public Boolean existsById(long id) {
        return itemRepository.existsById(id);
    }


}
