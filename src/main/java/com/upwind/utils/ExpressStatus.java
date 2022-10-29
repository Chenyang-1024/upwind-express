package com.upwind.utils;

public enum ExpressStatus {

    status_1("待揽收"), status_2("待付款"), status_3("运送中"), status_4("正在派件"), status_5("已签收");

    private String status;

    ExpressStatus (String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
