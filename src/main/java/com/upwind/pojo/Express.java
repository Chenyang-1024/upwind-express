package com.upwind.pojo;

import java.util.Date;

public class Express {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column express.id
     *
     * @mbg.generated Thu Oct 27 17:00:07 CST 2022
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column express.order_no
     *
     * @mbg.generated Thu Oct 27 17:00:07 CST 2022
     */
    private String order_no;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column express.category
     *
     * @mbg.generated Thu Oct 27 17:00:07 CST 2022
     */
    private String category;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column express.weight
     *
     * @mbg.generated Thu Oct 27 17:00:07 CST 2022
     */
    private Float weight;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column express.freight
     *
     * @mbg.generated Thu Oct 27 17:00:07 CST 2022
     */
    private Float freight;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column express.status
     *
     * @mbg.generated Thu Oct 27 17:00:07 CST 2022
     */
    private String status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column express.order_time
     *
     * @mbg.generated Thu Oct 27 17:00:07 CST 2022
     */
    private Date order_time;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column express.send_time
     *
     * @mbg.generated Thu Oct 27 17:00:07 CST 2022
     */
    private Date send_time;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column express.receive_time
     *
     * @mbg.generated Thu Oct 27 17:00:07 CST 2022
     */
    private Date receive_time;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column express.sendwise_id
     *
     * @mbg.generated Thu Oct 27 17:00:07 CST 2022
     */
    private Integer sendwise_id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column express.receivewise_id
     *
     * @mbg.generated Thu Oct 27 17:00:07 CST 2022
     */
    private Integer receivewise_id;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column express.id
     *
     * @return the value of express.id
     *
     * @mbg.generated Thu Oct 27 17:00:07 CST 2022
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column express.id
     *
     * @param id the value for express.id
     *
     * @mbg.generated Thu Oct 27 17:00:07 CST 2022
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column express.order_no
     *
     * @return the value of express.order_no
     *
     * @mbg.generated Thu Oct 27 17:00:07 CST 2022
     */
    public String getOrder_no() {
        return order_no;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column express.order_no
     *
     * @param order_no the value for express.order_no
     *
     * @mbg.generated Thu Oct 27 17:00:07 CST 2022
     */
    public void setOrder_no(String order_no) {
        this.order_no = order_no == null ? null : order_no.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column express.category
     *
     * @return the value of express.category
     *
     * @mbg.generated Thu Oct 27 17:00:07 CST 2022
     */
    public String getCategory() {
        return category;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column express.category
     *
     * @param category the value for express.category
     *
     * @mbg.generated Thu Oct 27 17:00:07 CST 2022
     */
    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column express.weight
     *
     * @return the value of express.weight
     *
     * @mbg.generated Thu Oct 27 17:00:07 CST 2022
     */
    public Float getWeight() {
        return weight;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column express.weight
     *
     * @param weight the value for express.weight
     *
     * @mbg.generated Thu Oct 27 17:00:07 CST 2022
     */
    public void setWeight(Float weight) {
        this.weight = weight;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column express.freight
     *
     * @return the value of express.freight
     *
     * @mbg.generated Thu Oct 27 17:00:07 CST 2022
     */
    public Float getFreight() {
        return freight;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column express.freight
     *
     * @param freight the value for express.freight
     *
     * @mbg.generated Thu Oct 27 17:00:07 CST 2022
     */
    public void setFreight(Float freight) {
        this.freight = freight;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column express.status
     *
     * @return the value of express.status
     *
     * @mbg.generated Thu Oct 27 17:00:07 CST 2022
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column express.status
     *
     * @param status the value for express.status
     *
     * @mbg.generated Thu Oct 27 17:00:07 CST 2022
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column express.order_time
     *
     * @return the value of express.order_time
     *
     * @mbg.generated Thu Oct 27 17:00:07 CST 2022
     */
    public Date getOrder_time() {
        return order_time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column express.order_time
     *
     * @param order_time the value for express.order_time
     *
     * @mbg.generated Thu Oct 27 17:00:07 CST 2022
     */
    public void setOrder_time(Date order_time) {
        this.order_time = order_time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column express.send_time
     *
     * @return the value of express.send_time
     *
     * @mbg.generated Thu Oct 27 17:00:07 CST 2022
     */
    public Date getSend_time() {
        return send_time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column express.send_time
     *
     * @param send_time the value for express.send_time
     *
     * @mbg.generated Thu Oct 27 17:00:07 CST 2022
     */
    public void setSend_time(Date send_time) {
        this.send_time = send_time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column express.receive_time
     *
     * @return the value of express.receive_time
     *
     * @mbg.generated Thu Oct 27 17:00:07 CST 2022
     */
    public Date getReceive_time() {
        return receive_time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column express.receive_time
     *
     * @param receive_time the value for express.receive_time
     *
     * @mbg.generated Thu Oct 27 17:00:07 CST 2022
     */
    public void setReceive_time(Date receive_time) {
        this.receive_time = receive_time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column express.sendwise_id
     *
     * @return the value of express.sendwise_id
     *
     * @mbg.generated Thu Oct 27 17:00:07 CST 2022
     */
    public Integer getSendwise_id() {
        return sendwise_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column express.sendwise_id
     *
     * @param sendwise_id the value for express.sendwise_id
     *
     * @mbg.generated Thu Oct 27 17:00:07 CST 2022
     */
    public void setSendwise_id(Integer sendwise_id) {
        this.sendwise_id = sendwise_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column express.receivewise_id
     *
     * @return the value of express.receivewise_id
     *
     * @mbg.generated Thu Oct 27 17:00:07 CST 2022
     */
    public Integer getReceivewise_id() {
        return receivewise_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column express.receivewise_id
     *
     * @param receivewise_id the value for express.receivewise_id
     *
     * @mbg.generated Thu Oct 27 17:00:07 CST 2022
     */
    public void setReceivewise_id(Integer receivewise_id) {
        this.receivewise_id = receivewise_id;
    }
}