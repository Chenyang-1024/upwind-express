package com.upwind.controller;

import com.upwind.pojo.Consumer;
import com.upwind.pojo.Courier;
import com.upwind.pojo.Manager;
import com.upwind.pojo.Outlet;
import com.upwind.service.ConsumerService;
import com.upwind.service.CourierService;
import com.upwind.service.ManagerService;
import com.upwind.service.OutletService;
import com.upwind.utils.MD5Util;
import com.upwind.utils.ResponseMessage;
import com.upwind.utils.VerifyCodeUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private ConsumerService consumerService;
    @Autowired
    private CourierService courierService;
    @Autowired
    private OutletService outletService;
    @Autowired
    private ManagerService managerService;

    @ApiOperation(value = "系统登录", response = ResponseMessage.class)
    @PostMapping("/login")
    @ResponseBody
    @Transactional
    public ResponseMessage login (@RequestParam("phone") String phone,
                                  @RequestParam("password") String password,
                                  @RequestParam("identity") int identity,
                                  @RequestParam("verifyCode") String verifyCode,
                                  HttpServletRequest request) {

        if ("".equals(phone) || "".equals(password))
            return ResponseMessage.error("账号/密码输入为空");
        HttpSession session = request.getSession();
        // 验证用户输入的验证码是否正确
        if (!VerifyCodeUtil.checkVerifyCode(verifyCode, request))
            return ResponseMessage
                    .error("验证码输入有误")
                    .addObject("verifyCodeExpected", session.getAttribute("verifyCode"));
        // identity 为 0 表示普通用户身份，为 1 表示快递员身份，为 2 表示网点负责人身份，为 3 表示系统管理员
        // 前端单选框控制 identity 只为 0, 1, 2
        if (identity == 0) {
            Consumer consumer = consumerService.consumerLogin(phone, MD5Util.getMD5(password));
            if (consumer == null)
                return ResponseMessage.error("输入账号/密码有误");
            consumer.setPassword(null);
            session.setAttribute("userIdentity", identity);
            session.setAttribute("loginUser", consumer);
            return ResponseMessage
                    .success("登录成功")
                    .addObject("consumer", consumer);
        } else if (identity == 1) {
            Courier courier = courierService.courierLogin(phone, MD5Util.getMD5(password));
            if (courier == null)
                return ResponseMessage.error("输入账号/密码有误");
            courier.setPassword(null);
            session.setAttribute("userIdentity", identity);
            session.setAttribute("loginUser", courier);
            return ResponseMessage
                    .success("登录成功")
                    .addObject("courier", courier);
        } else {
            Outlet outlet = outletService.outletLogin(phone, MD5Util.getMD5(password));
            if (outlet == null)
                return ResponseMessage.error("输入账号/密码有误");
            outlet.setPassword(null);
            session.setAttribute("userIdentity", identity);
            session.setAttribute("loginUser", outlet);
            return ResponseMessage
                    .success("登录成功")
                    .addObject("outlet", outlet);
        }

    }

    @ApiOperation(value = "管理员登录", response = ResponseMessage.class)
    @PostMapping("/manager/login")
    @ResponseBody
    @Transactional
    public ResponseMessage managerLogin (@RequestParam("account") String account,
                                         @RequestParam("password") String password,
                                         @RequestParam("verifyCode") String verifyCode,
                                         HttpServletRequest request) {

        if ("".equals(account) || "".equals(password))
            return ResponseMessage.error("账号/密码输入为空");
        HttpSession session = request.getSession();
        if (!VerifyCodeUtil.checkVerifyCode(verifyCode, request))
            return ResponseMessage
                    .error("验证码输入有误")
                    .addObject("verifyCodeExpected", session.getAttribute("verifyCode"));
        Manager manager = managerService.managerLogin(account, MD5Util.getMD5(password));
        if (manager == null)
            return ResponseMessage.error("输入账号/密码有误");
        manager.setPassword(null);
        session.setAttribute("userIdentity", 3);
        session.setAttribute("loginUser", manager);
        return ResponseMessage
                .success("登录成功")
                .addObject("manager", manager);

    }

    @ApiOperation(value="系统登出", response=ResponseMessage.class)
    @PostMapping("/logout")
    @ResponseBody
    @Transactional
    public ResponseMessage logout (HttpServletRequest request) {

        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") == null)
            return ResponseMessage
                    .error("请勿重复登出");
        else {
            session.setAttribute("loginUser", null);
            session.setAttribute("userIdentity", null);
            return ResponseMessage
                    .success("登出成功");
        }

    }

}
