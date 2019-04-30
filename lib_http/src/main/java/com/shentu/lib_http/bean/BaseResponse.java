package com.shentu.lib_http.bean;


public class BaseResponse<T> {

    private String state;
    private String msg;
    private T data;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccessful() {
        return state.equals("1");
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "state='" + state + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
