package com.aummn.suburb.exception;

public class SuburbExistsException extends RuntimeException {

    public SuburbExistsException() {
        super();
    }
    
    public SuburbExistsException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public SuburbExistsException(String message) {
        super(message);
    }
    
    public SuburbExistsException(Throwable cause) {
        super(cause);
    }
}
