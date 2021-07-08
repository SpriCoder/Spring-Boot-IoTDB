package com.njuzhy.demo.constant;

public class ServerException extends RuntimeException{
    // The exception of Server
    private int code;
    private String msg;

    public ServerException() {
    }

    public ServerException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ServerException(String message, int code, String msg) {
        super(message);
        this.code = code;
        this.msg = msg;
    }

    public ServerException(String message, Throwable cause, int code, String msg) {
        super(message, cause);
        this.code = code;
        this.msg = msg;
    }

    public ServerException(Throwable cause, int code, String msg) {
        super(cause);
        this.code = code;
        this.msg = msg;
    }

    public ServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code, String msg) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.msg = msg;
    }

    public String getMessage(){
        return this.msg;
    }

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
}

