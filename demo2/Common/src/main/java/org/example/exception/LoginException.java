package org.example.exception;

public class LoginException extends RuntimeException {
    private final Integer code;

    public LoginException(String message) {
        super(message);
        this.code = 401;
    }

    public LoginException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
