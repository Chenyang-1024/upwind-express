package com.upwind.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ResponseMessage
 * @Description 对响应数据进行封装
 **/
public class ResponseMessage {

    private String code;
    private String msg;
    private Map<String, Object> objectMap = new HashMap<>();

    public ResponseMessage() {}

    public ResponseMessage(String code, String msg, Map<String, Object> objectMap) {
        this.code = code;
        this.msg = msg;
        this.objectMap = objectMap;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getObjectMap() {
        return objectMap;
    }

    public void setObjectMap(Map<String, Object> objectMap) {
        this.objectMap = objectMap;
    }

    public ResponseMessage addObject (String key, Object value) {
        this.objectMap.put(key, value);
        return this;
    }

    public static ResponseMessage notLogin () {
        return new ResponseMessage("300", "未登录", null);
    }

    public static ResponseMessage success (String handleMsg) {
        ResponseMessage resMsg = new ResponseMessage();
        resMsg.setCode("200");
        resMsg.setMsg(handleMsg);
        return resMsg;
    }

    public static ResponseMessage error (String handleMsg) {
        ResponseMessage resMsg = new ResponseMessage();
        resMsg.setCode("100");
        resMsg.setMsg(handleMsg);
        return resMsg;
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", objectMap=" + objectMap +
                '}';
    }
}
