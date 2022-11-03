package com.upwind.service;

import com.upwind.pojo.Consumer;

import java.util.List;

public interface ConsumerService {

    /**
     * 根据 id 获取用户信息
     * @param id
     * @return
     */
    Consumer getConsumerById (Integer id);

    /**
     * 更新用户信息
     * @param consumer  用户对象
     * @return          上传成功 true , 上传失败 false
     */
    boolean updateConsumer (Consumer consumer);

    /**
     * 用户登录
     * @param phone     用户输入的 手机号
     * @param password  用户输入的密码
     * @return          用户输入的 id 和密码是否匹配。 true 表示匹配成功，false 表示匹配失败
     */
    Consumer consumerLogin(String phone, String password);

    /**
     * 获取所有用户信息
     * @return 所有用户信息
     */
    List<Consumer> getAllConsumer();

}
