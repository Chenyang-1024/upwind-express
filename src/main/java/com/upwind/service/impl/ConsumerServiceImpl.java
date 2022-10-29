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
    public boolean consumerLogin(String phone, String password){
        //从数据库中查询出手机号对应的用户信息
        ConsumerExample consumerExample = new ConsumerExample();
        consumerExample.createCriteria().andPhoneEqualTo(phone);
        List<Consumer> result = consumerMapper.selectByExample(consumerExample);

        //查询到信息且密码一致的时候返回 true，否则返回 false
        return result.size() != 0 && result.get(0).getPassword().equals(password);
    }

    @Override
    public List<Consumer> getAllConsumer() {
        return consumerMapper.selectByExample(null);
    }

}
