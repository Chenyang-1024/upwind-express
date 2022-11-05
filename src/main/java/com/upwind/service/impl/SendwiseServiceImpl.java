package com.upwind.service.impl;

import com.upwind.mapper.SendwiseMapper;
import com.upwind.pojo.Sendwise;
import com.upwind.pojo.SendwiseExample;
import com.upwind.service.SendwiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if (sendwise.getCourier_id() == null) {
            Sendwise old_sendwise = sendwiseMapper.selectByPrimaryKey(sendwise.getId());
            sendwise.setCity(sendwise.getCity() == null ? old_sendwise.getCity() : sendwise.getCity());
            sendwise.setProvince(sendwise.getProvince() == null ? old_sendwise.getProvince() : sendwise.getProvince());
            sendwise.setDistrict(sendwise.getDistrict() == null ? old_sendwise.getDistrict() : sendwise.getDistrict());
            sendwise.setDetail_addr(sendwise.getDetail_addr() == null ? old_sendwise.getDetail_addr() : sendwise.getDetail_addr());
            sendwise.setSender_id(sendwise.getSender_id() == null ? old_sendwise.getSender_id() : sendwise.getSender_id());
            return sendwiseMapper.updateByPrimaryKey(sendwise) > 0;

        }
        return sendwiseMapper.updateByPrimaryKeySelective(sendwise) > 0;
    }

    @Override
    public Sendwise getSendwiseById(Integer id) {
        return sendwiseMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Sendwise> getSendwiseBySenderId(Integer sender_id) {
        SendwiseExample sendwiseExample = new SendwiseExample();
        sendwiseExample.createCriteria().andSender_idEqualTo(sender_id);
        return sendwiseMapper.selectByExample(sendwiseExample);
    }

    @Override
    public List<Sendwise> getSendwiseByCourierId(Integer courier_id) {
        SendwiseExample sendwiseExample = new SendwiseExample();
        sendwiseExample.createCriteria().andCourier_idEqualTo(courier_id);
        return sendwiseMapper.selectByExample(sendwiseExample);
    }
}
