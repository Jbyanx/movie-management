package com.bycorp.moviemanagement.exception;

import lombok.Getter;

public class ObjectNotFoundException extends RuntimeException{
    @Getter
    private String objectName;
    private String cause;

    public ObjectNotFoundException(String objectName) {
        this.objectName = objectName;
        this.cause = null;
    }

    public ObjectNotFoundException(String objectName, Throwable cause) {
        this.objectName = objectName;
        this.cause = cause.toString();

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
