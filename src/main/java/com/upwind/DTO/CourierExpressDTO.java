package com.upwind.DTO;

import com.upwind.pojo.Consumer;
import com.upwind.pojo.Express;
import com.upwind.pojo.Receivewise;
import com.upwind.pojo.Sendwise;

import java.util.Date;

public class CourierExpressDTO {

    private Express express;
    private Sendwise sendwise;
    private Consumer sender;
    private Receivewise receivewise;
    private Consumer receiver;
    // 表示快递订单是经该快递员收寄还是投递
    private String action;

    public CourierExpressDTO(Express express, Sendwise sendwise, Consumer sender, Receivewise receivewise, Consumer receiver, String action) {
        this.express = express;
        this.sendwise = sendwise;
        this.sender = sender;
        this.receivewise = receivewise;
        this.receiver = receiver;
        this.action = action;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "CourierExpressDTO{" +
                "express=" + express +
                ", sendwise=" + sendwise +
                ", sender=" + sender +
                ", receivewise=" + receivewise +
                ", receiver=" + receiver +
                ", action='" + action + '\'' +
                '}';
    }

}
