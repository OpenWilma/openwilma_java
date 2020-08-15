package org.openwilma.java.classes.errors;

public enum ErrorType {
    Unknown(-1),
    NetworkError(0),
    NoContent(1),
    InvalidContent(2),
    WilmaError(3);

    private int errorCode;

    ErrorType(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
