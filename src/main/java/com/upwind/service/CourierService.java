package com.upwind.service;

import com.upwind.pojo.Courier;
import io.swagger.models.auth.In;

import java.util.List;

public interface CourierService {

    /**
     * 新增快递员信息，等待网点审核
     * 返回 id 给 Controller ，Controller 拿到 id 后合成工号，再请求更新快递员信息
     * @param courier
     * @return
     */
    Integer insertCourier (Courier courier);

    /**
     * 根据快递员 id 获取快递员信息
     * @param id    快递员 id
     * @return      快递员对象
     */
    Courier getCourierById(Integer id);

    /**
     * 更新快递员的信息
     * @param courier   快递员对象
     * @return          上传成功 true , 上传失败 false
     */
    boolean updateCourier (Courier courier);

    /**
     * 快递员登录
     * @param phone     快递员输入的手机号
     * @param password  快递员输入的密码
     * @return          快递员输入的 id 和密码是否匹配。 true 表示匹配成功，false 表示匹配失败
     */
    Courier courierLogin(String phone, String password);

    /**
     * 根据网点 id 获取网点内的所有快递员
     * @param id        网点 id
     * @return          网点内的快递员列表
     */
    List<Courier> getCourierByOutletId(Integer id);

    /**
     * 获取所有快递员信息
     * @return 所有快递员信息
     */
    List<Courier> getAllCourier();

}
