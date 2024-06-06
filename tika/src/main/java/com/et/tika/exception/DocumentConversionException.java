package com.et.tika.exception;

/**
 *
 */
public final class DocumentConversionException extends RuntimeException {

    public DocumentConversionException(String message, Exception ex) {
        super(message, ex);
    }
}
