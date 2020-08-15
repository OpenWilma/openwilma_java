package org.openwilma.java.classes.errors;

public enum ErrorType {
    Unknown(-1),
    NetworkError(0),
    InvalidContent(1),
    WilmaError(2),
    NoContent(3),
    LoginError(4);


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
