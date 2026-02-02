package com.greateast.warehouse.controller;

import com.greateast.warehouse.model.entity.Item;
import com.greateast.warehouse.model.request.ItemRequest;
import com.greateast.warehouse.model.response.BaseResponseDto;
import com.greateast.warehouse.model.response.ItemResponse;
import com.greateast.warehouse.service.ItemService;
import com.greateast.warehouse.util.logresponsetime.LogExecutionTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing items.
 */
@RestController
@RequestMapping("/item/v1")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/create")
    @LogExecutionTime
    public ResponseEntity<BaseResponseDto<?>> createItem(@RequestBody ItemRequest item) throws Exception{
        BaseResponseDto<ItemResponse> resp = itemService.saveAndReturnWithVariant(item);
        return ResponseEntity.ok(resp);
    }

    @LogExecutionTime
    @PutMapping("/update/{id}")
    public ResponseEntity<BaseResponseDto<ItemResponse>> updateItem(@PathVariable("id") long id, @RequestBody ItemRequest item) {
        BaseResponseDto<ItemResponse> resp = itemService.update(id, item);
        return ResponseEntity.ok(resp);
    }

    @LogExecutionTime
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable("id") long id) {
        BaseResponseDto<?> resp = itemService.deleteById(id);
        return ResponseEntity.ok(resp);
    }

    @LogExecutionTime
    @GetMapping("/items")
    public ResponseEntity<BaseResponseDto<?>> getItems(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        BaseResponseDto<Page<Item>> resp = itemService.findAll(page, size);
        return ResponseEntity.ok(resp);
    }

    @LogExecutionTime
    @GetMapping("/items/variants")
    public ResponseEntity<BaseResponseDto<?>> getItemsWithVariant(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        BaseResponseDto<Page<ItemResponse>> resp = itemService.findAllWithVariant(page, size);
        return ResponseEntity.ok(resp);
    }

    @LogExecutionTime
    @GetMapping("/items/{id}")
    public  ResponseEntity<BaseResponseDto<Item>> findItemById(@PathVariable("id") int id) {
        BaseResponseDto<Item> resp = itemService.findById(id);
        return ResponseEntity.ok(resp);
    }
}
