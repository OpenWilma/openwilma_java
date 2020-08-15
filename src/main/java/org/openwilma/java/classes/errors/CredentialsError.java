package org.openwilma.java.classes.errors;

public class CredentialsError extends Error {

    public CredentialsError() {
        super("Invalid credentials for login", ErrorType.LoginError);
    }
}
