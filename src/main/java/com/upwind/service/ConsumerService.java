package com.upwind.service;

import com.upwind.pojo.Consumer;

public interface ConsumerService {


    /**
     * 根据 id 获取用户信息
     * @param id
     * @return
     */
    Consumer getConsumerById (Integer id);

}
