package com.upwind.service.impl;

import com.upwind.mapper.ReceivewiseMapper;
import com.upwind.pojo.Receivewise;
import com.upwind.service.ReceivewiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceivewiseServiceImpl implements ReceivewiseService {

    @Autowired
    private ReceivewiseMapper receivewiseMapper;

    @Override
    public Integer insertReceivewise(Receivewise receivewise) {
        int row = receivewiseMapper.insert(receivewise);
        if (row > 0)
            return receivewise.getId();
        else
            return null;
    }

    @Override
    public boolean deleteReceivewiseById(Integer id) {
        return receivewiseMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public boolean updateReceivewise(Receivewise receivewise) {
        return receivewiseMapper.updateByPrimaryKeySelective(receivewise) > 0;
    }

    @Override
    public Receivewise getReceivewiseById(Integer id) {
        return receivewiseMapper.selectByPrimaryKey(id);
    }
}
