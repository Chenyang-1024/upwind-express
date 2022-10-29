package com.upwind.service;

import com.upwind.DTO.ConsumerExpressDTO;
import com.upwind.DTO.CourierExpressDTO;
import com.upwind.DTO.DetailExpressDTO;
import com.upwind.DTO.OutletExpressDTO;
import com.upwind.pojo.Express;
import com.upwind.pojo.Receivewise;
import com.upwind.pojo.Sendwise;

import java.util.List;

public interface ExpressService {

    /**
     * 新增快递订单
     * 用户下单时，物流状态为 待收寄
     * @param detailExpressDTO
     * @param outlet_id
     * @return
     */
    Integer insertExpress (DetailExpressDTO detailExpressDTO, Integer outlet_id);

    /**
     * 删除快递订单
     * @param id
     * @return
     */
    boolean deleteExpressById (Integer id);

    /**
     * 物流状态更新时，需要更新快递订单信息（物流状态更新让 Controller 在封装 DTO 的时候完成就好）
     * 1. 用户下单后，需要更新 生成快递单号、下单时间、物流状态（待揽收）、收寄快递员（系统自动安排）、派件快递员（系统自动安排）
     * 2. 收寄快递员收寄时，需要更新订单的 重量、运费、物流状态（待付款）、收寄时间
     * 3. 用户支付订单费用后，更新 物流状态（运送中）
     * 4. 派件快递员派件时，需要更新订单的 物流状态（正在派件），这个状态不一定要，因为正常这一步是需要快递入库后更新 正在派件，而本系统中不涉及入库
     * 5. 用户签收后，需要更新订单的 物流状态（已签收）、签收时间
     * @param detailExpressDTO
     * @return
     */
    boolean updateExpress (DetailExpressDTO detailExpressDTO);

    /**
     * 用户端
     * 查询寄出订单或收到订单，可用订单号加以筛选
     * flag = 0 时，查询当前用户寄出订单列表
     * @param flag
     * @param order_no
     * @return
     */
    List<ConsumerExpressDTO> getConsumerExpressByOrder (Integer consumer_id, int flag, String order_no);

    /**
     * 用户端
     * 查询某用户（寄出）待付款的快递订单列表
     * @param consumer_id
     * @param order_no
     * @return
     */
    List<ConsumerExpressDTO> getConsumerExpressByUnpaid (Integer consumer_id, String order_no);

    /**
     * 快递员端
     * 可以查询两种状态：待揽收、待妥投
     * 当 status 为空时，查询该快递员经手的全部快递订单
     * @param courier_id
     * @param status
     * @param order_no
     * @return
     */
    List<CourierExpressDTO> getCourierExpressByStatus (Integer courier_id, String status, String order_no);

    /**
     * 网点端
     * 查询该网点发出、投递的所有快递订单
     * @param outlet_id
     * @param order_no
     * @return
     */
    List<OutletExpressDTO> getExpressByOutletId (Integer outlet_id, String order_no);

    /**
     * 查询系统所有快递订单
     * @return
     */
    List<Express> getAllExpress ();

    /**
     * 获取某一快递订单的详情信息
     * @param id
     * @return
     */
    DetailExpressDTO getDetailExpressById (Integer id);

}
