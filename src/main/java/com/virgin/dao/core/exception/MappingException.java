package com.virgin.dao.core.exception;

public class MappingException extends RuntimeException {

    private static final long serialVersionUID = -1l;

    public MappingException() {
        super();
    }

    public MappingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MappingException(String message) {
        super(message);
    }

    public MappingException(Throwable cause) {
        super(cause);
    }

}
