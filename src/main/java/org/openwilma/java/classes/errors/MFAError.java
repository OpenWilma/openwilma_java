package org.openwilma.java.classes.errors;

public class MFAError extends Error {

    public MFAError() {
        super("Wilma user has 2FA enabled", ErrorType.LoginError);
    }
}
