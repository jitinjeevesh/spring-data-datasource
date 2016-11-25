package com.spring.datasource.core.exception;

public class DataStoreEntityManagerFactoryException extends RuntimeException {

    private static final long serialVersionUID = -1l;

    public DataStoreEntityManagerFactoryException() {
        super();
    }

    public DataStoreEntityManagerFactoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataStoreEntityManagerFactoryException(String message) {
        super(message);
    }

    public DataStoreEntityManagerFactoryException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

}