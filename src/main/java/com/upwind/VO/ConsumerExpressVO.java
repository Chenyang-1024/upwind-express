package com.upwind.VO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ConsumerExpressVO {

    private Integer express_id;
    private String order_no;
    private String sender_name;
    private String receiver_name;
    private String status;
    private Float freight;
    private Date order_time;
    private Date send_time;
    private Date receive_time;

    public ConsumerExpressVO(Integer express_id, String order_no, String sender_name, String receiver_name, String status, Float freight, Date order_time, Date send_time, Date receive_time) {
        this.express_id = express_id;
        this.order_no = order_no;
        this.sender_name = sender_name;
        this.receiver_name = receiver_name;
        this.status = status;
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

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Float getFreight() {
        return freight;
    }

    public void setFreight(Float freight) {
        this.freight = freight;
    }

    @JsonFormat(pattern="yyyy-MM-dd kk:mm:ss",timezone = "GMT+8")
    public Date getOrder_time() {
        return order_time;
    }

    public void setOrder_time(Date order_time) {
        this.order_time = order_time;
    }

    @JsonFormat(pattern="yyyy-MM-dd kk:mm:ss",timezone = "GMT+8")
    public Date getSend_time() {
        return send_time;
    }

    public void setSend_time(Date send_time) {
        this.send_time = send_time;
    }

    @JsonFormat(pattern="yyyy-MM-dd kk:mm:ss",timezone = "GMT+8")
    public Date getReceive_time() {
        return receive_time;
    }

    public void setReceive_time(Date receive_time) {
        this.receive_time = receive_time;
    }

    @Override
    public String toString() {
        return "ConsumerExpressDTO{" +
                "express_id=" + express_id +
                ", order_no='" + order_no + '\'' +
                ", sender_name='" + sender_name + '\'' +
                ", receiver_name='" + receiver_name + '\'' +
                ", status='" + status + '\'' +
                ", freight=" + freight +
                ", order_time=" + order_time +
                ", send_time=" + send_time +
                ", receive_time=" + receive_time +
                '}';
    }

}
