package com.upwind.DTO;

import com.upwind.pojo.Consumer;
import com.upwind.pojo.Express;
import com.upwind.pojo.Receivewise;
import com.upwind.pojo.Sendwise;

import java.sql.Timestamp;
import java.util.Date;

public class ConsumerExpressDTO {

    private Express express;
    private Sendwise sendwise;
    private Consumer sender;
    private Receivewise receivewise;
    private Consumer receiver;

    public ConsumerExpressDTO(Express express, Sendwise sendwise, Consumer sender, Receivewise receivewise, Consumer receiver) {
        this.express = express;
        this.sendwise = sendwise;
        this.sender = sender;
        this.receivewise = receivewise;
        this.receiver = receiver;
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

    @Override
    public String toString() {
        return "ConsumerExpressDTO{" +
                "express=" + express +
                ", sendwise=" + sendwise +
                ", sender=" + sender +
                ", receivewise=" + receivewise +
                ", receiver=" + receiver +
                '}';
    }

}
