package com.upwind.DTO;

import com.upwind.pojo.*;

public class OutletExpressDTO {

    private Express express;
    private Sendwise sendwise;
    private Consumer sender;
    private Receivewise receivewise;
    private Consumer receiver;
    private Courier courier;

    public OutletExpressDTO(Express express, Sendwise sendwise, Consumer sender, Receivewise receivewise, Consumer receiver, Courier courier) {
        this.express = express;
        this.sendwise = sendwise;
        this.sender = sender;
        this.receivewise = receivewise;
        this.receiver = receiver;
        this.courier = courier;
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

    public Consumer getSender() {
        return sender;
    }

    public void setSender(Consumer sender) {
        this.sender = sender;
    }

    public Receivewise getReceivewise() {
        return receivewise;
    }

    public void setReceivewise(Receivewise receivewise) {
        this.receivewise = receivewise;
    }

    public Consumer getReceiver() {
        return receiver;
    }

    public void setReceiver(Consumer receiver) {
        this.receiver = receiver;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    @Override
    public String toString() {
        return "OutletExpressDTO{" +
                "express=" + express +
                ", sendwise=" + sendwise +
                ", sender=" + sender +
                ", receivewise=" + receivewise +
                ", receiver=" + receiver +
                ", courier=" + courier +
                '}';
    }
}
