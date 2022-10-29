package com.upwind.service;

import com.upwind.pojo.Sendwise;

import java.util.List;

public interface SendwiseService {

    /**
     * 新增快递订单寄件信息
     * @param sendwise
     * @return
     */
    Integer insertSendwise (Sendwise sendwise);

    /**
     * 删除订单时需要删除订单的寄件信息
     * @param id
     * @return
     */
    boolean deleteSendwiseById (Integer id);

    /**
     * 收寄的时候，需要向寄件信息中更新收寄快递员 id
     * @param sendwise
     * @return
     */
    boolean updateSendwise (Sendwise sendwise);

    /**
     * 查询订单信息时，需要根据快递的寄件信息 id 查询寄件信息
     * @param id
     * @return
     */
    Sendwise getSendwiseById (Integer id);

    /**
     * 根据寄件人 id 查询寄件信息
     * @param sender_id
     * @return
     */
    List<Sendwise> getSendwiseBySenderId (Integer sender_id);

    /**
     * 根据收寄快递员 id 获取寄件信息
     * @param courier_id
     * @return
     */
    List<Sendwise> getSendwiseByCourierId (Integer courier_id);

}
