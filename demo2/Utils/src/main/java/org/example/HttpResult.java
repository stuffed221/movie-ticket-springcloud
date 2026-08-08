package org.example;



public class HttpResult {
    private Object data; // 返回值
    private String msg; //提示信息
    private Integer code; //状态码

    public HttpResult() {
    }

    public HttpResult(Object data, String msg, Integer code) {
        this.data = data;
        this.msg = msg;
        this.code = code;
    }
    public static HttpResult successResult(Object object){
        return new HttpResult(object,"成功",200);
    }
    public static HttpResult errorResult(String msg){
        return new HttpResult(null,msg,100);
    }

    public static HttpResult nologinResult( String msg) {
        return new HttpResult(null,msg,300);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
