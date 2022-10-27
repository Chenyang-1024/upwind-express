package com.upwind.service;

import com.upwind.pojo.Outlet;

import java.util.List;

public interface OutletService {

    /**
     * 新增快递网点
     * @param outlet
     * @return
     */
    Integer insertOutlet (Outlet outlet);

    /**
     * 根据 id 注销网点
     * 一般不会用到，只有两种情景，一个是系统管理员删除网点，一个是网点负责人注销网点
     * 加以限制，必须网点下已经没有快递员，没有正在收寄的快递
     * @param id
     * @return
     */
    boolean deleteOutletById (Integer id);

    /**
     * 网点账号登录
     * @param phone
     * @param password
     * @return
     */
    Outlet outletLogin (String phone, String password);

    /**
     * 根据 id 获取网点信息
     * @param id
     * @return
     */
    Outlet getOutletById (Integer id);

    /**
     * 根据网点名称获取网点
     * @param title
     * @return
     */
    List<Outlet> getOutletByTitle (String title);

    /**
     * 获取距离用户最近的网点
     * @param consumer_id
     * @param province
     * @param city
     * @param strict
     * @param detail_addr
     * @return
     */
    List<Outlet> getClostestOutlet (Integer consumer_id, String province, String city, String strict, String detail_addr);

//    /**
//     * 转让网点（修改网点负责人）
//     * 进行转让时需要当前负责人输出当前账号和密码，确定转让者后，新负责人账号密码初始化为默认密码：123456
//     * 新负责人需要尽快登录，其第一次登录时需要修改账号密码
//     * @param name
//     * @param phone
//     * @return
//     */
//    boolean updateChargeman (String name, String phone);
//
//    /**
//     * 网点地址搬迁（修改网点地址）
//     * @param province
//     * @param city
//     * @param strict
//     * @param detail_addr
//     * @return
//     */
//    boolean updateAddress (String province, String city, String strict, String detail_addr);

    /**
     * 修改网点信息
     * 1. 网点转让，修改负责人信息
     * 2. 网点搬迁，修改网点地址
     * @param outlet
     * @return
     */
    boolean updateOutlet (Outlet outlet);

}
