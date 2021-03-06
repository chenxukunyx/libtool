package com.shentu.lib_mvp.EventBus;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @date: 2019/3/11
 * @time: 2:41 PM
 * @fuction:
 */
public class EventMessage<T> {

    private int code;
    private T data;

    public EventMessage() {
    }

    public EventMessage(int code) {
        this.code = code;
    }

    public EventMessage(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "EventMessage{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }
}
