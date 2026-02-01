package com.greateast.warehouse.controller;

import com.greateast.warehouse.model.entity.Variant;
import com.greateast.warehouse.model.request.VariantRequest;
import com.greateast.warehouse.model.response.BaseResponseDto;
import com.greateast.warehouse.service.VariantService;
import com.greateast.warehouse.util.logresponsetime.LogExecutionTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/variant/v1")
@RequiredArgsConstructor
public class VariantController {

    private final VariantService variantService;

    @PostMapping("/create")
    @LogExecutionTime
    public ResponseEntity<BaseResponseDto<Variant>> createVariant(@RequestBody VariantRequest Variant) throws Exception{
        BaseResponseDto<Variant> resp = variantService.save(Variant);
        return ResponseEntity.ok(resp);
    }

    @LogExecutionTime
    @PutMapping("/update/items/{itemId}/variants/{id}")
    public ResponseEntity<BaseResponseDto<Variant>> updateVariant(@PathVariable("itemId") long itemId, @PathVariable("id") long id, @RequestBody VariantRequest variantRequest) {
        BaseResponseDto<Variant> resp = variantService.update(itemId, id, variantRequest);
        return ResponseEntity.ok(resp);
    }

    @LogExecutionTime
    @DeleteMapping("/delete/items/{itemId}/variants/{id}")
    public ResponseEntity<?> deleteVariant(@PathVariable("itemId") long itemId, @PathVariable("id") long id) {
        BaseResponseDto<?> resp = variantService.deleteByItemIdAndId(itemId, id);
        return ResponseEntity.ok(resp);
    }

    @LogExecutionTime
    @GetMapping("/variants")
    public ResponseEntity<BaseResponseDto<?>> getVariants(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        BaseResponseDto<Page<Variant>> resp = variantService.findAll(page, size);
        return ResponseEntity.ok(resp);
    }

    @LogExecutionTime
    @GetMapping("/variants/{id}")
    public  ResponseEntity<BaseResponseDto<List<Variant>>> findVariantById(@PathVariable("id") int id) {
        BaseResponseDto<List<Variant>> resp = variantService.findById(id);
        return ResponseEntity.ok(resp);
    }

    @LogExecutionTime
    @GetMapping("/items/{itemId}/variants")
    public  ResponseEntity<BaseResponseDto<List<Variant>>> findVariantByItemId(@PathVariable("itemId") long itemId) {
        BaseResponseDto<List<Variant>> resp = variantService.findByItemId(itemId);
        return ResponseEntity.ok(resp);
    }

    @LogExecutionTime
    @GetMapping("/items/{itemId}/variants/{id}")
    public  ResponseEntity<BaseResponseDto<Variant>> findVariantByItemIdAndId(@PathVariable("itemId") long itemId, @PathVariable("id") long id) {
        BaseResponseDto<Variant> resp = variantService.findByItemIdAndId(itemId, id);
        return ResponseEntity.ok(resp);
    }
}
