package com.virgin.dao.core.exception;

public class DataStoreEntityManagerException extends RuntimeException {

    private static final long serialVersionUID = -1l;

    public DataStoreEntityManagerException() {
        super();
    }

    public DataStoreEntityManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataStoreEntityManagerException(String message) {
        super(message);
    }

    public DataStoreEntityManagerException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

}