package com.aummn.suburb.exception;

public class SuburbNotFoundException extends RuntimeException {

    public SuburbNotFoundException() {
        super();
    }
    
    public SuburbNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public SuburbNotFoundException(String message) {
        super(message);
    }
    
    public SuburbNotFoundException(Throwable cause) {
        super(cause);
    }
}
