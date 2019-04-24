package com.shentu.lib_http.exception;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @date: 2019/3/15
 * @time: 3:12 PM
 * @fuction:
 */
public class TokenExpireException extends Exception {

    public TokenExpireException(String message, Throwable cause) {
        super(message, cause);
    }
}
