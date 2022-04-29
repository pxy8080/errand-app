package com.erradns.Model;

public class Result<T> {

    // 返回状态码(最好三位数方便扩展,和http状态码类似)
    private int code;
    // 返回描述信息(接口描述)
    private String message;
    // 使用泛型T返回前端想要的数据(任意数据类型),通常这里使用Map<String,Object>来接收也可以。
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

