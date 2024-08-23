package com.dynamicfilteringapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateFieldException extends RuntimeException {
    public DuplicateFieldException(String entityName, String fieldName, String fieldValue) {
        super(String.format("O valor '%s' para o campo '%s' já existe na entidade '%s'.", fieldValue, fieldName, entityName));
    }
}