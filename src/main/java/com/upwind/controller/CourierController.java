package com.upwind.controller;

import com.upwind.DTO.OutletExpressDTO;
import com.upwind.pojo.Courier;
import com.upwind.pojo.Outlet;
import com.upwind.service.CourierService;
import com.upwind.service.OutletService;
import com.upwind.utils.MD5Util;
import com.upwind.utils.ResponseMessage;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/courier")
public class CourierController {

    @Autowired
    private CourierService courierService;
    @Autowired
    private OutletService outletService;

    @ApiOperation(value = "快递员注册", response = ResponseMessage.class)
    @PostMapping("/register")
    @Transactional
    @ResponseBody
    public ResponseMessage register (@RequestParam("name") String name,
                                     @RequestParam("phone") String phone,
                                     @RequestParam("gender") String gender,
                                     @RequestParam("password") String password) {

        if (courierService.getCourierByPhone(phone) != null)
            return ResponseMessage.error("请勿重复注册");
        String psw = MD5Util.getMD5(password);
        Courier courier = new Courier();
        courier.setId(null);
        courier.setName(name);
        courier.setPhone(phone);
        courier.setGender(gender);
        courier.setPassword(psw);
        courier.setApproved_flag(0);
        Integer courier_id = courierService.insertCourier(courier);
        if (courier_id == null)
            return ResponseMessage.error("注册失败");
        String job_no = "C" + String.format("%010d", courier_id);
        courier.setJob_no(job_no);
        if (!courierService.updateCourier(courier)) {
            courierService.deleteCourierById(courier_id);
            return ResponseMessage.error("注册失败");
        }
        return ResponseMessage
                .success("注册成功")
                .addObject("job_no", job_no);

    }

    @ApiOperation(value = "快递员账号信息", response = ResponseMessage.class)
    @GetMapping("/accountInfo")
    @Transactional
    @ResponseBody
    public ResponseMessage queryCourierAccountInfo (HttpServletRequest request) {

        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null && session.getAttribute("userIdentity") != null) {
            int identity = (int) session.getAttribute("userIdentity");
            if (identity == 1) {
                Courier courier = (Courier) session.getAttribute("loginUser");
                String outlet_title = null;
                if (courier.getOutlet_id() != null) {
                    Outlet outlet = outletService.getOutletById(courier.getOutlet_id());
                    outlet_title = outlet.getTitle();
                }
                return ResponseMessage
                        .success("处理成功")
                        .addObject("outlet_title", outlet_title);
            } else {
                return ResponseMessage.error("当前登录身份权限无操作权限");
            }
        } else {
            return ResponseMessage.notLogin();
        }

    }

    @ApiOperation(value = "快递员实名认证", response = ResponseMessage.class)
    @PostMapping("/authenticate")
    @Transactional
    @ResponseBody
    public ResponseMessage authenticate (@RequestParam("name") String name,
                                         @RequestParam("identity_num") String identity_num,
                                         HttpServletRequest request) {

        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null && session.getAttribute("userIdentity") != null) {
            int identity = (int) session.getAttribute("userIdentity");
            if (identity == 1) {
                Courier courier = (Courier) session.getAttribute("loginUser");
                if (courier.getIdentity_num() != null)
                    return ResponseMessage.error("请勿重复实名认证");
                courier.setIdentity_num(identity_num);
                if (name.equals(courier.getName()) && courierService.updateCourier(courier)) {
                    session.setAttribute("loginUser", courier);
                    return ResponseMessage.success("认证成功");
                }
                return ResponseMessage.error("处理失败");
            } else {
                return ResponseMessage.error("当前登录身份权限无操作权限");
            }
        } else {
            return ResponseMessage.notLogin();
        }

    }

    @ApiOperation(value = "快递员申请加入网点", response = ResponseMessage.class)
    @PostMapping("/joinOutlet")
    @Transactional
    @ResponseBody
    // 这里应该模糊查询返回列表让（预）快递员选择然后再提交，或者至少返回一个网点信息让（预）快递员确认后再提交，但是才疏学浅就只能先直接提交了
    public ResponseMessage joinOutlet (@RequestParam("outlet_title") String outlet_title,
                                       HttpServletRequest request) {

        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null && session.getAttribute("userIdentity") != null) {
            int identity = (int) session.getAttribute("userIdentity");
            if (identity == 1) {
                Courier courier = (Courier) session.getAttribute("loginUser");
                Outlet outlet = outletService.getOutletByTitle(outlet_title).get(0);
                courier.setOutlet_id(outlet.getId());
                courier.setApproved_flag(0);
                if (courierService.updateCourier(courier)) {
                    session.setAttribute("loginUser", courier);
                    return ResponseMessage.success("处理成功");
                }
                return ResponseMessage.error("处理失败");
            } else {
                return ResponseMessage.error("当前登录身份权限无操作权限");
            }
        } else {
            return ResponseMessage.notLogin();
        }

    }

    @ApiOperation(value = "快递员修改手机号", response = ResponseMessage.class)
    @PostMapping("/updatePhone")
    @Transactional
    @ResponseBody
    public ResponseMessage updatePhone (@RequestParam("phone") String phone,
                                       HttpServletRequest request) {

        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null && session.getAttribute("userIdentity") != null) {
            int identity = (int) session.getAttribute("userIdentity");
            if (identity == 1) {
                Courier courier = (Courier) session.getAttribute("loginUser");
                courier.setPhone(phone);
                if (courierService.updateCourier(courier)) {
                    session.setAttribute("loginUser", courier);
                    return ResponseMessage.success("处理成功");
                }
                return ResponseMessage.error("处理失败");
            } else {
                return ResponseMessage.error("当前登录身份权限无操作权限");
            }
        } else {
            return ResponseMessage.notLogin();
        }

    }



}
