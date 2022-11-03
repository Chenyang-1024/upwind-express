package com.upwind.controller;

import com.upwind.service.ConsumerService;
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

        // TODO
        return null;

    }

}
