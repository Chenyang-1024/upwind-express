package com.upwind.controller;

import com.upwind.DTO.CourierExpressDTO;
import com.upwind.DTO.DetailExpressDTO;
import com.upwind.DTO.OutletExpressDTO;
import com.upwind.VO.CourierExpressVO;
import com.upwind.pojo.*;
import com.upwind.service.*;
import com.upwind.utils.ExpressStatus;
import com.upwind.utils.MD5Util;
import com.upwind.utils.ResponseMessage;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/courier")
public class CourierController {

    @Autowired
    private CourierService courierService;
    @Autowired
    private OutletService outletService;
    @Autowired
    private ExpressService expressService;
    @Autowired
    private ConsumerService consumerService;
    @Autowired
    private SendwiseService sendwiseService;
    @Autowired
    private ReceivewiseService receivewiseService;

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

    @ApiOperation(value = "账号信息", response = ResponseMessage.class)
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
                Map<String, Object> map = new HashMap<>();

                if (courier.getOutlet_id() != null) {
                    Outlet outlet = outletService.getOutletById(courier.getOutlet_id());
                    outlet_title = outlet.getTitle();
                }
                map.put("courier", courierService.getCourierById(courier.getId()));
                map.put("outlet_title", outlet_title);
                return ResponseMessage
                        .success("处理成功")
                        .addObject("accountInfo", map);
            } else {
                return ResponseMessage.error("当前登录身份权限无操作权限");
            }
        } else {
            return ResponseMessage.notLogin();
        }

    }

    @ApiOperation(value = "实名认证", response = ResponseMessage.class)
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
                    return ResponseMessage
                            .error("请勿重复实名认证")
                            .addObject("identity_num", courier.getIdentity_num());
                courier.setIdentity_num(identity_num);
                if (name.equals(courier.getName()) && courierService.updateCourier(courier)) {
                    session.setAttribute("loginUser", courier);
                    return ResponseMessage.success("认证成功");
                }
                return ResponseMessage.error("真实姓名与账号不符");
            } else {
                return ResponseMessage.error("当前登录身份权限无操作权限");
            }
        } else {
            return ResponseMessage.notLogin();
        }

    }

    @ApiOperation(value = "申请加入网点", response = ResponseMessage.class)
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
                if (courier.getIdentity_num() == null)
                    return ResponseMessage.error("请先实名认证");
                List<Outlet> outletList = outletService.getOutletByTitle(outlet_title);
                if (outletList.size() == 0)
                    return ResponseMessage.error("不存在该网点");
                Outlet outlet = outletList.get(0);
                courier.setOutlet_id(outlet.getId());
                courier.setApproved_flag(0);
                if (courierService.updateCourier(courier)) {
                    session.setAttribute("loginUser", courier);
                    return ResponseMessage.success("提交成功");
                }
                return ResponseMessage.error("提交失败");
            } else {
                return ResponseMessage.error("当前登录身份权限无操作权限");
            }
        } else {
            return ResponseMessage.notLogin();
        }

    }

    @ApiOperation(value = "修改手机号", response = ResponseMessage.class)
    @PutMapping("/updatePhone")
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

    @ApiOperation(value = "查询某一物流状态的订单列表", response = ResponseMessage.class)
    @GetMapping("/expressList")
    @Transactional
    @ResponseBody
    public ResponseMessage queryExpressListByStatus (@RequestParam(value = "status", required = false) Integer status,
                                                     @RequestParam(value = "order_no", required = false) String order_no,
                                                     HttpServletRequest request) {

        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null && session.getAttribute("userIdentity") != null) {
            int identity = (int) session.getAttribute("userIdentity");
            if (identity == 1) {
                Courier courier = (Courier) session.getAttribute("loginUser");
                List<CourierExpressVO> courierExpressVOList = new ArrayList<>();
                List<CourierExpressDTO> courierExpressDTOList = new ArrayList<>();
                if (status == null) {
                    courierExpressDTOList = expressService.getCourierExpressByStatus(courier.getId(), null, order_no);
                } else {
                    switch (status) {
                        case 1:
                            courierExpressDTOList = expressService.getCourierExpressByStatus(courier.getId(), ExpressStatus.status_1.getStatus(), order_no);
                            break;
                        case 2:
                            courierExpressDTOList = expressService.getCourierExpressByStatus(courier.getId(), ExpressStatus.status_2.getStatus(), order_no);
                            break;
                        case 3:
                            return ResponseMessage.error("快递员无需查询自己收寄的运送中的快递");
                        case 4:
                            courierExpressDTOList = expressService.getCourierExpressByStatus(courier.getId(), ExpressStatus.status_4.getStatus(), order_no);
                            break;
                        case 5:
                            courierExpressDTOList = expressService.getCourierExpressByStatus(courier.getId(), ExpressStatus.status_5.getStatus(), order_no);
                            break;
                    }
                }
                for (CourierExpressDTO courierExpressDTO : courierExpressDTOList) {
                    Express express = courierExpressDTO.getExpress();
                    System.out.println(express.getOrder_time());
                    Consumer sender = courierExpressDTO.getSender();
                    Consumer receiver = courierExpressDTO.getReceiver();
                    Sendwise sendwise = courierExpressDTO.getSendwise();
                    Receivewise receivewise = courierExpressDTO.getReceivewise();
                    // action 为 0 表示该快递订单由当前快递员收寄，为 1 表示由当前快递员投递
                    Integer action = null;
                    if (Objects.equals(sendwise.getCourier_id(), courier.getId()))
                        action = 0;
                    else if (Objects.equals(receivewise.getCourier_id(), courier.getId()))
                        action = 1;
                    courierExpressVOList.add(
                            new CourierExpressVO(
                                    express.getId(),
                                    express.getOrder_no(),
                                    sender.getName(),
                                    receiver.getName(),
                                    sender.getPhone(),
                                    receiver.getPhone(),
                                    sendwise.getProvince() + sendwise.getCity() + sendwise.getDistrict() + sendwise.getDetail_addr(),
                                    receivewise.getProvince() + receivewise.getCity() + receivewise.getDistrict() + receivewise.getDetail_addr(),
                                    express.getStatus(),
                                    action,
                                    express.getFreight(),
                                    express.getOrder_time(),
                                    express.getSend_time(),
                                    express.getReceive_time()
                            )
                    );
                }
                return ResponseMessage
                        .success("处理成功")
                        .addObject("express_list", courierExpressVOList);
            } else {
                return ResponseMessage.error("当前登录身份权限无操作权限");
            }
        } else {
            return ResponseMessage.notLogin();
        }

    }

    @ApiOperation(value = "揽收快递，更新快递订单信息", response = ResponseMessage.class)
    @PostMapping("/updateExpress/{express_id}")
    @Transactional
    @ResponseBody
    public ResponseMessage updateExpress (@PathVariable Integer express_id,
                                          @RequestParam(value = "category", required = false) String category,
                                          @RequestParam(value = "weight", required = false) Float weight,
                                          @RequestParam(value = "freight", required = false) Float freight,
                                          HttpServletRequest request) {

        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null && session.getAttribute("userIdentity") != null) {
            int identity = (int) session.getAttribute("userIdentity");
            if (identity == 1) {
                Courier courier = (Courier) session.getAttribute("loginUser");
                Express express = expressService.getExpressById(express_id);
                if (express.getStatus() != null && !express.getStatus().equals(ExpressStatus.status_1.getStatus()))
                    return ResponseMessage.error("快递已揽收发出，不可设置重量及运费");
                express.setCategory(category);
                express.setWeight(weight);
                express.setFreight(freight);
                express.setStatus(ExpressStatus.status_3.getStatus());
                express.setSend_time(new Date());
                if (expressService.updateExpress(new DetailExpressDTO(express,
                                sendwiseService.getSendwiseById(express.getSendwise_id()),
                                receivewiseService.getReceivewiseById(express.getReceivewise_id())))) {
                    return ResponseMessage.success("修改成功");
                }
                else
                    return ResponseMessage.error("修改失败");
            } else {
                return ResponseMessage.error("当前登录身份权限无操作权限");
            }
        } else {
            return ResponseMessage.notLogin();
        }

    }

    @ApiOperation(value = "删除快递订单", response = ResponseMessage.class)
    @DeleteMapping("/deleteExpress/{express_id}")
    @Transactional
    @ResponseBody
    // 并不是真正地从数据库删除订单记录，而只是将订单信息中的相应的快递员字段擦除
    public ResponseMessage deleteExpress (@PathVariable Integer express_id,
                                          HttpServletRequest request) {

        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null && session.getAttribute("userIdentity") != null) {
            int identity = (int) session.getAttribute("userIdentity");
            if (identity == 1) {
                Courier courier = (Courier) session.getAttribute("loginUser");
                Sendwise sendwise = sendwiseService.getSendwiseById(expressService.getExpressById(express_id).getSendwise_id());
                if (Objects.equals(sendwise.getCourier_id(), courier.getId())) {
                    sendwise.setCourier_id(null);
                    if (sendwiseService.updateSendwise(sendwise))
                        return ResponseMessage.success("删除成功");
                    else
                        return ResponseMessage.error("删除失败");
                } else {
                    Receivewise receivewise = receivewiseService.getReceivewiseById(expressService.getExpressById(express_id).getReceivewise_id());
                    if (Objects.equals(receivewise.getCourier_id(), courier.getId())) {
                        receivewise.setCourier_id(null);
                        if (receivewiseService.updateReceivewise(receivewise))
                            return ResponseMessage.success("删除成功");
                        else
                            return ResponseMessage.error("删除失败");
                    } else {
                        return ResponseMessage.error("该快递订单未经过当前快递员，无删除权限");
                    }
                }
            } else {
                return ResponseMessage.error("当前登录身份权限无操作权限");
            }
        } else {
            return ResponseMessage.notLogin();
        }

    }

}
