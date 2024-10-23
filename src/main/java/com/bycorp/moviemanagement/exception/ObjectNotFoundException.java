package com.bycorp.moviemanagement.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends RuntimeException{
    @Getter
    private final String objectName;
    private final Throwable cause;

    public ObjectNotFoundException(String objectName) {
        this.objectName = objectName;
        this.cause = null;
    }

    public ObjectNotFoundException(String objectName, Throwable cause) {
        this.objectName = objectName;
        this.cause = cause;

    }

    @Override
    public String getMessage() {
        String message = super.getMessage();

        if(message == null){
            message = "";
        }

        return message
                .concat("(objeto no encontrado:")
                .concat(objectName).concat(")");
    }
}
