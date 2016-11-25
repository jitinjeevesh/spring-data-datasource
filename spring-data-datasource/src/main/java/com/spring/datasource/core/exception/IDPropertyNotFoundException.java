package com.spring.datasource.core.exception;

public class IDPropertyNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -1l;

    public IDPropertyNotFoundException() {
        super();
    }

    public IDPropertyNotFoundException(String message) {
        super(message);
    }

    public IDPropertyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public IDPropertyNotFoundException(Throwable cause) {
        super(cause);
    }

    protected IDPropertyNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
