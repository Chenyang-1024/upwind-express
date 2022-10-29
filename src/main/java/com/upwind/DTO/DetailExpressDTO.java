package com.upwind.DTO;

import com.upwind.pojo.Express;
import com.upwind.pojo.Receivewise;
import com.upwind.pojo.Sendwise;

public class DetailExpressDTO {

    private Express express;
    private Sendwise sendwise;
    private Receivewise receivewise;

    public DetailExpressDTO() { }

    public DetailExpressDTO(Express express, Sendwise sendwise, Receivewise receivewise) {
        this.express = express;
        this.sendwise = sendwise;
        this.receivewise = receivewise;
    }

    public Express getExpress() {
        return express;
    }

    public void setExpress(Express express) {
        this.express = express;
    }

    public Sendwise getSendwise() {
        return sendwise;
    }

    public void setSendwise(Sendwise sendwise) {
        this.sendwise = sendwise;
    }

    public Receivewise getReceivewise() {
        return receivewise;
    }

    public void setReceivewise(Receivewise receivewise) {
        this.receivewise = receivewise;
    }

    @Override
    public String toString() {
        return "DetailExpressInfo{" +
                "express=" + express +
                ", sendwise=" + sendwise +
                ", receivewise=" + receivewise +
                '}';
    }

}
