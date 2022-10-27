package com.upwind.service;

import com.upwind.pojo.Receivewise;

public interface ReceivewiseService {

    /**
     * 新增快递订单的收件信息
     * @param receivewise
     * @return
     */
    Integer insertReceivewise (Receivewise receivewise);

    /**
     * 删除订单时，需要删除订单的收件信息
     * @param id
     * @return
     */
    boolean deleteReceivewiseById (Integer id);

    /**
     * 派件的时候，需要向收件信息中更新派件快递员 id
     * @param receivewise
     * @return
     */
    boolean updateReceivewise (Receivewise receivewise);

    /**
     * 查询订单信息时，需要根据快递的收件信息 id 查询收件信息
     * @param id
     * @return
     */
    Receivewise getReceivewiseById (Integer id);
}
