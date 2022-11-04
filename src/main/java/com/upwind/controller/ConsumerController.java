package com.upwind.controller;

import com.upwind.pojo.Consumer;
import com.upwind.service.ConsumerService;
import com.upwind.utils.MD5Util;
import com.upwind.utils.ResponseMessage;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;

    @ApiOperation(value="普通用户注册", response= ResponseMessage.class)
    @PostMapping("/register")
    @Transactional
    @ResponseBody
    public ResponseMessage register (@RequestParam("phone") String phone,
                                     @RequestParam("password") String password,
                                     @RequestParam("name") String name,
                                     @RequestParam("gender") String gender) {

        if (consumerService.getConsumerByPhone(phone) != null)
            return ResponseMessage.error("请勿重复注册");
        String psw = MD5Util.getMD5(password);
        Consumer consumer = new Consumer();
        consumer.setId(null);
        consumer.setName(name);
        consumer.setPhone(phone);
        consumer.setGender(gender);
        consumer.setPassword(psw);
        if (consumerService.insertConsumer(consumer) == null)
            return ResponseMessage.error("注册失败");
        return ResponseMessage.success("注册成功");

    }

}
