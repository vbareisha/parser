package com.vbareisha.parser.core.exception;

public class NotFoundParseTypeException extends RuntimeException {
    public NotFoundParseTypeException() {
        super();
    }

    public NotFoundParseTypeException(String message) {
        super(message);
    }

    public NotFoundParseTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundParseTypeException(Throwable cause) {
        super(cause);
    }

    protected NotFoundParseTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
