package com.upwind.service.impl;

import com.upwind.mapper.ConsumerMapper;
import com.upwind.pojo.Consumer;
import com.upwind.pojo.ConsumerExample;
import com.upwind.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Autowired
    private ConsumerMapper consumerMapper;

    @Override
    public Consumer getConsumerById(Integer id) {
        return consumerMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateConsumer (Consumer consumer){
        return consumerMapper.updateByPrimaryKeySelective(consumer) > 0;
    }

    @Override
    public Consumer consumerLogin(String phone, String password){
        //从数据库中查询出手机号对应的用户信息
        ConsumerExample consumerExample = new ConsumerExample();
        consumerExample.createCriteria().andPhoneEqualTo(phone);
        List<Consumer> consumerList = consumerMapper.selectByExample(consumerExample);
        if (consumerList.size() > 0) {
            Consumer consumer = consumerList.get(0);
            if (password.equals(consumer.getPassword()))
                return consumer;
        }
        return null;
    }

    @Override
    public List<Consumer> getAllConsumer() {
        return consumerMapper.selectByExample(null);
    }

}
