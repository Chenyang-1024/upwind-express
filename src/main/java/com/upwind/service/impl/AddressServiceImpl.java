package com.upwind.service.impl;

import com.upwind.mapper.AddressMapper;
import com.upwind.pojo.Address;
import com.upwind.pojo.AddressExample;
import com.upwind.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public Integer insertAddress(Address address) {
        int row = addressMapper.insert(address);
        if (row > 0)
            return address.getId();
        else
            return null;
    }

    @Override
    public boolean deleteAddressById(Integer id) {
        return addressMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public boolean updateAddress(Address address) {
        return addressMapper.updateByPrimaryKeySelective(address) > 0;
    }

    @Override
    public List<Address> getAddressByConsumerId(Integer consumer_id) {
        AddressExample addressExample = new AddressExample();
        addressExample.createCriteria().andConsumer_idEqualTo(consumer_id);
        return addressMapper.selectByExample(addressExample);
    }

    @Override
    public Address getDefaultAddressByConsumerId(Integer consumer_id) {
        AddressExample addressExample = new AddressExample();
        addressExample.createCriteria().andConsumer_idEqualTo(consumer_id).andIs_defaultEqualTo(1);
        List<Address> addressList = addressMapper.selectByExample(addressExample);
        if (addressList.size() > 0)
            return addressList.get(0);
        else
            return null;
    }
}
