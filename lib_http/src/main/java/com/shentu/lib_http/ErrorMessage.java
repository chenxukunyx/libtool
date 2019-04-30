package com.shentu.lib_http;


public class ErrorMessage {

    public ErrorMessage(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    private String errorCode;
    private String message;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
