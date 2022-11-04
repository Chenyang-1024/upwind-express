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
    public Integer insertCourier(Courier courier) {
        int row = courierMapper.insert(courier);
        if (row > 0)
            return courier.getId();
        else
            return null;
    }

    @Override
    public boolean deleteCourierById(Integer id) {
        return courierMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public Courier getCourierById(Integer id) {
        return courierMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateCourier(Courier courier) {
        return courierMapper.updateByPrimaryKeySelective(courier) > 0;
    }

    @Override
    public Courier courierLogin(String phone, String password) {
        //从数据库中查询出手机号对应的快递员信息
        CourierExample courierExample = new CourierExample();
        courierExample.createCriteria().andPhoneEqualTo(phone);
        List<Courier> courierList = courierMapper.selectByExample(courierExample);
        if (courierList.size() > 0) {
            Courier courier = courierList.get(0);
            if (password.equals(courier.getPassword()))
                return courier;
        }
        return null;
    }

    @Override
    public List<Courier> getCourierByOutletId(Integer id) {
        CourierExample courierExample = new CourierExample();
        courierExample.createCriteria().andOutlet_idEqualTo(id);
        return courierMapper.selectByExample(courierExample);
    }

    @Override
    public Courier getCourierByPhone(String phone) {
        CourierExample courierExample = new CourierExample();
        courierExample.createCriteria().andPhoneEqualTo(phone);
        List<Courier> courierList = courierMapper.selectByExample(courierExample);
        if (courierList.size() > 0)
            return courierList.get(0);
        return null;
    }

    @Override
    public List<Courier> getAllCourier() {
        return courierMapper.selectByExample(null);
    }

}