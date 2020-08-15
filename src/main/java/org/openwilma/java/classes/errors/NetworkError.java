package org.openwilma.java.classes.errors;

public class NetworkError extends Error {

    private Exception exception;

    public NetworkError(Exception exception) {
        super(exception.getMessage(), ErrorType.NetworkError);
        this.exception = exception;
    }
}
