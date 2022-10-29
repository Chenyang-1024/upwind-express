package com.upwind.service.impl;

import com.upwind.mapper.CourierMapper;
import com.upwind.pojo.Consumer;
import com.upwind.pojo.ConsumerExample;
import com.upwind.pojo.Courier;
import com.upwind.pojo.CourierExample;
import com.upwind.service.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourierServiceImpl implements CourierService {

    @Autowired
    public CourierMapper courierMapper;

    @Override
    public Courier getCourierById(Integer id) {
        return courierMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateCourier(Courier courier) {
        return courierMapper.updateByPrimaryKeySelective(courier) > 0;
    }

    @Override
    public boolean courierLogin(String phone, String password) {
        //从数据库中查询出手机号对应的快递员信息
        CourierExample courierExample = new CourierExample();
        courierExample.createCriteria().andPhoneEqualTo(phone);
        List<Courier> result = courierMapper.selectByExample(courierExample);

        //查询到信息且密码一致的时候返回 true，否则返回 false
        return result.size() != 0 && result.get(0).getPassword().equals(password);
    }

    @Override
    public List<Courier> getCourierByOutletId(Integer id) {
        CourierExample courierExample = new CourierExample();
        courierExample.createCriteria().andOutlet_idEqualTo(id);
        return courierMapper.selectByExample(courierExample);
    }

    @Override
    public List<Courier> getAllCourier() {
        return courierMapper.selectByExample(null);
    }

}