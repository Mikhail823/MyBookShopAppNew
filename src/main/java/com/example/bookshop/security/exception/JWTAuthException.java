package com.example.bookshop.security.exception;

import org.springframework.security.core.AuthenticationException;

public class JWTAuthException extends AuthenticationException {
    public JWTAuthException(String msg) {
        super(msg);
    }
}
