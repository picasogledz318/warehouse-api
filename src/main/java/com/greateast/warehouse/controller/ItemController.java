
package com.greateast.warehouse.controller;

import com.greateast.warehouse.constant.TrxCode;
import com.greateast.warehouse.model.request.Item;
import com.greateast.warehouse.model.response.BaseResponseDto;
import com.greateast.warehouse.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * REST controller for managing items.
 */
@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<BaseResponseDto<Item>> createItem(@RequestBody Item item) throws Exception{
        BaseResponseDto<Item> resp = new BaseResponseDto<>();
        resp.setCode(TrxCode.TRX_CREATED.code());
        resp.setData(itemService.save(item));
        resp.setErrors(null);
        resp.setMessage("Item successfully added!");
        return ResponseEntity.ok(resp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseDto<Item>> updateItem(@PathVariable("id") int id, @RequestBody Item item) {
        BaseResponseDto<Item> resp = new BaseResponseDto<>();
        List<String> listError = new ArrayList<>();
        try{
            Item existingItem = itemService.findById(id);
            if(existingItem != null){
                item.setId(existingItem.getId());
                resp.setCode(TrxCode.TRX_OK.code());
                resp.setData(itemService.save(item));
                resp.setErrors(null);
                resp.setMessage("Item id "+id+" successfully updated!");
                return ResponseEntity.ok(resp);
            } else {
                resp.setCode(TrxCode.TRX_NOT_FOUND.code());
                resp.setMessage(TrxCode.TRX_NOT_FOUND.description());
                resp.setData(null);
                resp.setErrors(null);
                return ResponseEntity.ok(resp);
            }
        }catch (Exception err){
            log.error("Error updateItem:{}, trace:{}",err.getMessage(), err.getStackTrace());
            throw new RuntimeException("Error updateItem:"+err.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable("id") int id) {
        Boolean isItemExist = itemService.existsById(id);
        BaseResponseDto<Item> resp = new BaseResponseDto<>();
        try{
            if(isItemExist){
                itemService.deleteById((long) id);
                resp.setCode(TrxCode.TRX_OK.code());
                resp.setData(null);
                resp.setErrors(null);
                resp.setMessage("Item id "+id+" successfully deleted!");
                return ResponseEntity.ok(resp);
            } else {
                resp.setCode(TrxCode.TRX_NOT_FOUND.code());
                resp.setMessage(TrxCode.TRX_NOT_FOUND.description());
                resp.setData(null);
                resp.setErrors(null);
                return ResponseEntity.ok(resp);
            }
        }catch (Exception err){
            log.error("Error delete item:{}, trace:{}",err.getMessage(), err.getStackTrace());
            throw new RuntimeException("Error delete item:"+err.getMessage());
        }
    }

    @GetMapping("/find")
    public ResponseEntity<BaseResponseDto<?>> getItems() {
        BaseResponseDto<List<Item>> resp = new BaseResponseDto<>();
        try{
            List<Item> listItem = itemService.findAll();
            resp.setCode(TrxCode.TRX_CREATED.code());
            resp.setData(listItem);
            resp.setErrors(null);
            resp.setMessage("All items retrieved!");
            return ResponseEntity.ok(resp);
        }catch (Exception err){
            log.error("Error find all items:{}, trace:{}",err.getMessage(), err.getStackTrace());
            throw new RuntimeException("Error find all items:"+err.getMessage());
        }
    }

    @GetMapping("/find/{id}")
    public  ResponseEntity<BaseResponseDto<Item>> findItemById(@PathVariable("id") int id) {
        BaseResponseDto<Item> resp = new BaseResponseDto<>();
        resp.setCode(TrxCode.TRX_OK.code());
        resp.setData(itemService.findById(id));
        resp.setErrors(null);
        resp.setMessage("Data Found!");
        return ResponseEntity.ok(resp);

    }
}
