package com.dynamicfilteringapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntityActionException extends RuntimeException {
    public EntityActionException(String entityName, String action, String message) {
        super(String.format("Erro ao %s na entidade '%s': %s", action, entityName, message));
    }
}