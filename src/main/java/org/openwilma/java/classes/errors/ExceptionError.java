package org.openwilma.java.classes.errors;

public class ExceptionError extends Error {

    private Exception exception;

    public ExceptionError(Exception exception) {
        super(exception.getMessage(), ErrorType.Unknown);
        this.exception = exception;
    }
}
