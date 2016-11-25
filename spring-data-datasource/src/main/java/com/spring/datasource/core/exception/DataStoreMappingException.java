package com.spring.datasource.core.exception;

public class DataStoreMappingException extends RuntimeException {

    private static final long serialVersionUID = -1l;

    public DataStoreMappingException() {
        super();
    }

    public DataStoreMappingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataStoreMappingException(String message) {
        super(message);
    }

    public DataStoreMappingException(Throwable cause) {
        super(cause);
    }

}
