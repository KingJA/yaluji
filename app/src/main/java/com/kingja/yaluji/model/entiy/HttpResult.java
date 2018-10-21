package com.kingja.yaluji.model.entiy;

import java.io.Serializable;

/**
 * Description：TODO
 * Create Time：2016/10/620:32
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class HttpResult<T>implements Serializable {

    private int code;
    private String msg;

    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

}
