package com.zuo.common;

import lombok.Data;

/**
 * @author 橙宝cc
 * @date 2022/12/3 - 16:47
 */
@Data
public class R {
    //信息返回状态码
    private int code;
    //信息返回内容
    private String message;
    //信息返回状态
    private String type;
    //是否成功返回
    private Boolean success;
    //附带数据
    private Object data;

    public static R success(String message)
    {
        R r = new R();
        r.setCode(200);
        r.setMessage(message);
        r.setSuccess(true);
        r.setType("success");
        r.setData(null);
        return r;
    }

    public static R success(String message, Object data) {
        R r = success(message);
        r.setData(data);
        return r;
    }

    public static R warning(String message) {
        R r = error(message);
        r.setType("warning");
        return r;
    }

    public static R error(String message) {
        R r = success(message);
        r.setSuccess(false);
        r.setType("error");
        return r;
    }

    public static R fatal(String message) {
        R r = error(message);
        r.setCode(500);
        return r;
    }
}
