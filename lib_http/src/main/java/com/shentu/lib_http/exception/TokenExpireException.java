package com.shentu.lib_http.exception;


public class TokenExpireException extends Exception {

    public TokenExpireException(String message, Throwable cause) {
        super(message, cause);
    }
}
