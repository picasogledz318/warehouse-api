package com.greateast.warehouse.model.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BaseResponseDto<T> {

    private String code;
    private String message;
    private List<String> errors = new ArrayList<>();
    private T data;
}
