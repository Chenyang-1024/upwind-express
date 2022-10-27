package com.upwind.service.impl;

import com.upwind.mapper.SendwiseMapper;
import com.upwind.pojo.Sendwise;
import com.upwind.service.SendwiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendwiseServiceImpl implements SendwiseService {

    @Autowired
    private SendwiseMapper sendwiseMapper;

    @Override
    public Integer insertSendwise(Sendwise sendwise) {
        int row = sendwiseMapper.insert(sendwise);
        if (row > 0)
            return sendwise.getId();
        else
            return null;
    }

    @Override
    public boolean deleteSendwiseById(Integer id) {
        return sendwiseMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public boolean updateSendwise(Sendwise sendwise) {
        return sendwiseMapper.updateByPrimaryKeySelective(sendwise) > 0;
    }

    @Override
    public Sendwise getSendwiseById(Integer id) {
        return sendwiseMapper.selectByPrimaryKey(id);
    }
}
