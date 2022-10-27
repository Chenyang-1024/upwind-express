package com.upwind.service;

import com.upwind.pojo.Address;

import java.util.List;

public interface AddressService {

    /**
     * 新增用户地址
     * @param address
     * @return
     */
    Integer insertAddress (Address address);

    /**
     * 根据 id 删除地址簿中的地址
     * @param id
     * @return
     */
    boolean deleteAddressById (Integer id);

    /**
     * 修改地址簿中某条地址的信息
     * @param address
     * @return
     */
    boolean updateAddress (Address address);

    /**
     * 查询用户地址簿中的所有地址
     * @param consumer_id
     * @return
     */
    List<Address> getAddressByConsumerId (Integer consumer_id);

    /**
     * 查询用户的默认地址
     * @param consumer_id
     * @return
     */
    Address getDefaultAddressByConsumerId (Integer consumer_id);
}
