package com.upwind.VO;

import java.util.Date;

public class CourierExpressVO {

    private Integer express_id;
    private String sender_name;
    private String receiver_name;
    private String sender_phone;
    private String receiver_phone;
    private String sender_addr;
    private String receiver_addr;
    // 表示订单是经该快递员收寄还是经快递员投递
    private String status;
    private String action;
    private Float freight;
    private Date order_time;
    private Date send_time;
    private Date receive_time;

    public CourierExpressVO(Integer express_id, String sender_name, String receiver_name, String sender_phone, String receiver_phone, String sender_addr, String receiver_addr, String status, String action, Float freight, Date order_time, Date send_time, Date receive_time) {
        this.express_id = express_id;
        this.sender_name = sender_name;
        this.receiver_name = receiver_name;
        this.sender_phone = sender_phone;
        this.receiver_phone = receiver_phone;
        this.sender_addr = sender_addr;
        this.receiver_addr = receiver_addr;
        this.status = status;
        this.action = action;
        this.freight = freight;
        this.order_time = order_time;
        this.send_time = send_time;
        this.receive_time = receive_time;
    }

    public Integer getExpress_id() {
        return express_id;
    }

    public void setExpress_id(Integer express_id) {
        this.express_id = express_id;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getSender_phone() {
        return sender_phone;
    }

    public void setSender_phone(String sender_phone) {
        this.sender_phone = sender_phone;
    }

    public String getReceiver_phone() {
        return receiver_phone;
    }

    public void setReceiver_phone(String receiver_phone) {
        this.receiver_phone = receiver_phone;
    }

    public String getSender_addr() {
        return sender_addr;
    }

    public void setSender_addr(String sender_addr) {
        this.sender_addr = sender_addr;
    }

    public String getReceiver_addr() {
        return receiver_addr;
    }

    public void setReceiver_addr(String receiver_addr) {
        this.receiver_addr = receiver_addr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Float getFreight() {
        return freight;
    }

    public void setFreight(Float freight) {
        this.freight = freight;
    }

    public Date getOrder_time() {
        return order_time;
    }

    public void setOrder_time(Date order_time) {
        this.order_time = order_time;
    }

    public Date getSend_time() {
        return send_time;
    }

    public void setSend_time(Date send_time) {
        this.send_time = send_time;
    }

    public Date getReceive_time() {
        return receive_time;
    }

    public void setReceive_time(Date receive_time) {
        this.receive_time = receive_time;
    }

    @Override
    public String toString() {
        return "CourierExpressDTO{" +
                "express_id=" + express_id +
                ", sender_name='" + sender_name + '\'' +
                ", receiver_name='" + receiver_name + '\'' +
                ", sender_phone='" + sender_phone + '\'' +
                ", receiver_phone='" + receiver_phone + '\'' +
                ", sender_addr='" + sender_addr + '\'' +
                ", receiver_addr='" + receiver_addr + '\'' +
                ", status='" + status + '\'' +
                ", action='" + action + '\'' +
                ", freight=" + freight +
                ", order_time=" + order_time +
                ", send_time=" + send_time +
                ", receive_time=" + receive_time +
                '}';
    }

}
