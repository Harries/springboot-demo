package com.et.geotools.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ResponseResult implements Serializable {

    private static final long serialVersionUID = 2719931935414658118L;

    private final Integer status;

    private final String message;

    @JsonInclude(value = Include.NON_NULL)
    private final Object data;

    @JsonInclude(value = Include.NON_EMPTY)
    private final String[] exceptions;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private final Date timestamp;

    public ResponseResult(Integer status, String message) {

        super();
        this.status = status;
        this.message = message;
        this.data = null;
        this.timestamp = new Date();
        this.exceptions = null;
    }

    public ResponseResult() {

        super();
        this.status = null;
        this.message = null;
        this.data = null;
        this.timestamp = new Date();
        this.exceptions = null;

    }

    public ResponseResult(ResponseMessage rm){

        super();
        this.status = rm.getStatus();
        this.message = rm.getMessage();
        this.data = null;
        this.timestamp = new Date();
        this.exceptions = null;
    }

    public ResponseResult(ResponseMessage rm, Object data){
        super();
        this.status = rm.getStatus();
        this.message = rm.getMessage();
        this.data = data;
        this.timestamp = new Date();
        this.exceptions = null;
    }

    public ResponseResult(ResponseMessage rm, Long data){
        super();
        this.status = rm.getStatus();
        this.message = rm.getMessage();
        HashMap<String, Long> result = new HashMap<>();
        result.put("id", data);
        this.data = result;
        this.timestamp = new Date();
        this.exceptions = null;
    }


    public ResponseResult(Integer status, String message, Object data) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = new Date();
        this.exceptions = null;
    }

    public ResponseResult(Integer status, String message, String key, Object value) {
        super();
        this.status = status;
        this.message = message;
        Map<String, Object> map = new HashMap<String, Object>();

        if (key == null || ("").equals(key)) {
            map.put("key", value);
        } else {
            map.put(key, value);
        }
        this.data = map;
        this.timestamp = new Date();
        this.exceptions = null;

    }

    public ResponseResult(Integer status, Throwable ex) {
        super();
        this.status = status;
        this.message = ex.getMessage();
        this.data = null;
        StackTraceElement[] stackTeanceElement = ex.getStackTrace();
        this.exceptions = new String[stackTeanceElement.length];
        for (int i = 0; i < stackTeanceElement.length; i++) {
            this.exceptions[i] = stackTeanceElement[i].toString();
        }
        this.timestamp = new Date();
    }

    public ResponseResult(Integer status, String message, Throwable ex) {

        super();
        this.status = status;
        this.message = message;
        this.data = null;
        StackTraceElement[] stackTeanceElement = ex.getStackTrace();
        this.exceptions = new String[stackTeanceElement.length];
        for (int i = 0; i < stackTeanceElement.length; i++) {
            this.exceptions[i] = stackTeanceElement[i].toString();
        }
        this.timestamp = new Date();

    }
    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public String[] getExceptions() {
        return exceptions;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
