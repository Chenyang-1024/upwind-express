package com.upwind.controller;

import com.upwind.DTO.ConsumerExpressDTO;
import com.upwind.DTO.DetailExpressDTO;
import com.upwind.pojo.*;
import com.upwind.service.*;
import com.upwind.utils.MD5Util;
import com.upwind.utils.ResponseMessage;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/user")
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;
    @Autowired
    private SendwiseService sendwiseService;
    @Autowired
    private ReceivewiseService receivewiseService;
    @Autowired
    private ExpressService expressService;
    @Autowired
    private OutletService outletService;

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


    /**
     * 获取寄件信息
     * @param request   HttpServletRequest 对象，用于获取 session 信息
     * @return          用户的寄件信息列表
     */
    @ApiOperation(value = "普通用户获取寄件信息", response = ResponseMessage.class)
    @GetMapping("/getsendinfo")
    @Transactional
    @ResponseBody
    public ResponseMessage getSendwiseList(HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null && session.getAttribute("userIdentity") != null) {
            int identity = (int) session.getAttribute("userIdentity");
            Consumer consumer = (Consumer) session.getAttribute("loginUser");
            if (identity == 0) {
                //当前登录的用户是普通用户

                //获取普通用户 id
                Integer consumerId = consumer.getId();

                //获取寄件信息列表
                List<Sendwise> sendwiseList;
                if((sendwiseList = sendwiseService.getSendwiseBySenderId(consumerId)) != null){
                    //查询成功,返回数据
                    return ResponseMessage.success("查询成功")
                                          .addObject("sendwiseList", sendwiseList);
                }else{
                    return ResponseMessage.error("查询失败");
                }

            }else{
                //当前登录的用户不是普通用户
                return ResponseMessage.error("当前登录身份权限无法查看列表");
            }
        } else {
            //当前请求是由未登录的客户端发起的
            return ResponseMessage.notLogin();
        }
    }


    /**
     * 获取用户收件信息
     * @param request   HttpServletRequest 对象，用于获取 session 信息
     * @return          用户的收件信息列表
     */
    @ApiOperation(value = "普通用户获取收件信息", response = ResponseMessage.class)
    @GetMapping("/getreceivinginfo")
    @Transactional
    @ResponseBody
    public ResponseMessage getReceivewise(HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null && session.getAttribute("userIdentity") != null) {
            int identity = (int) session.getAttribute("userIdentity");
            Consumer consumer = (Consumer) session.getAttribute("loginUser");
            if (identity == 0) {
                //当前登录的用户是普通用户

                //获取普通用户 id
                Integer consumerId = consumer.getId();

                //获取收件信息列表
                List<Receivewise> receivewiseList;
                if((receivewiseList = receivewiseService.getReceivewiseByReceiverId(consumerId)) != null){
                    //查询成功,返回数据
                    return ResponseMessage.success("查询成功")
                                          .addObject("receivewiseList", receivewiseList);
                }else{
                    return ResponseMessage.error("查询失败");
                }


            }else{
                //当前登录的用户不是普通用户
                return ResponseMessage.error("当前登录身份权限无法查看列表");
            }
        } else {
            //当前请求是由未登录的客户端发起的
            return ResponseMessage.notLogin();
        }
    }


    /**
     * 获取用户所有未支付的订单信息
     * @param request   HttpServletRequest 对象，用于获取 session 信息
     * @return          用户未支付的订单列表
     */
    @ApiOperation(value = "普通用户获取所有未支付订单信息", response = ResponseMessage.class)
    @GetMapping("/getnopayinfo")
    @Transactional
    @ResponseBody
    public ResponseMessage getConsumerUnpaidExpress(HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null && session.getAttribute("userIdentity") != null) {
            int identity = (int) session.getAttribute("userIdentity");
            Consumer consumer = (Consumer) session.getAttribute("loginUser");
            if (identity == 0) {
                //当前登录的用户是普通用户

                //获取普通用户 id
                Integer consumerId = consumer.getId();

                //获取收件信息列表
                List<ConsumerExpressDTO> UnpaidExpressList;
                if((UnpaidExpressList = expressService.getConsumerExpressByUnpaid(consumerId, null)) != null){
                    //查询成功,返回数据
                    return ResponseMessage.success("查询成功")
                                          .addObject("UnpaidExpressList", UnpaidExpressList);
                }else{
                    return ResponseMessage.error("查询失败");
                }

            }else{
                //当前登录的用户不是普通用户
                return ResponseMessage.error("当前登录身份权限无法查看列表");
            }
        } else {
            //当前请求是由未登录的客户端发起的
            return ResponseMessage.notLogin();
        }
    }

    /**
     * 修改用户名
     * @param map       解析 json 数据得到的 HashMap 对象，其中新用户名的 key 为 "newUsername"
     * @param request   HttpServletRequest 对象，用于获取 session 信息
     * @return          修改用户名的结果
     */
    @ApiOperation(value = "普通用户更新用户名", response = ResponseMessage.class)
    @PostMapping("/updateusername")
    @Transactional
    @ResponseBody
    public ResponseMessage updateUsername(@RequestBody HashMap<String, String> map, HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null && session.getAttribute("userIdentity") != null) {
            int identity = (int) session.getAttribute("userIdentity");
            Consumer consumer = (Consumer) session.getAttribute("loginUser");
            if (identity == 0) {
                //当前登录的用户是普通用户

                //获取普通用户 id
                Integer consumerId = consumer.getId();

                //获取新用户名和当前用户名
                String newUsername = map.get("newUsername");
                String currentUsername = consumer.getName();

                //修改 session 中的用户名
                consumer.setName(newUsername);

                //修改数据库中该用户的用户名
                if(consumerService.updateConsumer(consumer)){
                    //修改成功,返回信息
                    return ResponseMessage.success("修改成功");
                }else{
                    //修改失败,将 session 中的用户名恢复为原来的用户名
                    consumer.setName(currentUsername);

                    //返回信息
                    return ResponseMessage.error("修改失败");
                }
            }else{
                //当前登录的用户不是普通用户
                return ResponseMessage.error("当前登录身份权限无法修改用户名");
            }
        } else {
            //当前请求是由未登录的客户端发起的
            return ResponseMessage.notLogin();
        }
    }


    /**
     * 普通用户更新手机号码
     * @param map       解析 json 数据得到的 HashMap 对象，其中新手机号的 key 为 "phoneNumberNew"
     * @param request   HttpServletRequest 对象，用于获取 session 信息
     * @return
     */
    @ApiOperation(value = "普通用户更新手机号码", response = ResponseMessage.class)
    @PostMapping("/updatephone")
    @Transactional
    @ResponseBody
    public ResponseMessage updatePhone(@RequestBody HashMap<String, String> map, HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null && session.getAttribute("userIdentity") != null) {
            int identity = (int) session.getAttribute("userIdentity");
            Consumer consumer = (Consumer) session.getAttribute("loginUser");
            if (identity == 0) {
                //当前登录的用户是普通用户

                //获取普通用户 id
                Integer consumerId = consumer.getId();

                //获取新手机号和当前手机号
                String newPhone = map.get("phone");
                String currentPhone = consumer.getPhone();

                //修改 session 中的手机号码
                consumer.setPhone(newPhone);

                //修改数据库中该用户的手机号码
                if(consumerService.updateConsumer(consumer)){
                    //修改成功,返回信息
                    return ResponseMessage.success("修改成功");
                }else{
                    //修改失败,将 session 中的用户名恢复为原来的用户名
                    consumer.setPhone(currentPhone);

                    //返回信息
                    return ResponseMessage.error("修改失败");
                }
            }else{
                //当前登录的用户不是普通用户
                return ResponseMessage.error("当前登录身份权限无法修改手机号码");
            }
        } else {
            //当前请求是由未登录的客户端发起的
            return ResponseMessage.notLogin();
        }
    }


    /**
     * 普通用户提交身份认证信息
     * @param map       解析 json 数据得到的 HashMap 对象，内容为 {"name": 真实姓名, “cardid”: 身份证号码}
     * @param request   HttpServletRequest 对象，用于获取 session 信息
     * @return
     */
    @ApiOperation(value = "用户认证", response = ResponseMessage.class)
    @PostMapping("/credit")
    @Transactional
    @ResponseBody
    public ResponseMessage credit(@RequestBody HashMap<String, String> map, HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null && session.getAttribute("userIdentity") != null) {
            int identity = (int) session.getAttribute("userIdentity");
            Consumer consumer = (Consumer) session.getAttribute("loginUser");
            if (identity == 0) {
                //当前登录的用户是普通用户

                //获取普通用户 id
                Integer consumerId = consumer.getId();

                //获取用户提交的真实姓名和身份证号码 以及 用户当前的真实姓名和身份证号码
                String consumerName = map.get("name");
                String identityId = map.get("cardid");
                String currentConsumerName = consumer.getName();
                String currentIdentityId = consumer.getIdentity_num();

                //修改 session 中的真实姓名和身份证号码
                consumer.setName(consumerName);
                consumer.setIdentity_num(identityId);

                //修改数据库中该用户的手机号码
                if(consumerService.updateConsumer(consumer)){
                    //修改成功,返回信息
                    return ResponseMessage.success("修改成功");
                }else{
                    //修改失败,将 session 中的真实姓名和身份证号码恢复为原来的信息
                    consumer.setName(currentConsumerName);
                    consumer.setIdentity_num(currentIdentityId);

                    //返回信息
                    return ResponseMessage.error("修改失败");
                }
            }else{
                //当前登录的用户不是普通用户
                return ResponseMessage.error("当前登录身份权限无法修改身份认证信息");
            }
        } else {
            //当前请求是由未登录的客户端发起的
            return ResponseMessage.notLogin();
        }
    }


    /**
     * 普通用户创建订单
     * @param map       解析 json 数据得到的 HashMap 对象，内容为
     *                  {
     *                      "name1"     : 寄件人,
     *                      "name2"     : 收件人,
     *                      "number1"   : 寄件人电话号码,
     *                      "number2"   : 收件人电话号码,
     *                      "region1"   : 寄件人 省,
     *                      "region2"   : 寄件人 市,
     *                      "region3"   : 寄件人 县,
     *                      "address1"  : 收件人 省,
     *                      "address2"  : 收件人 市,
     *                      "address3"  : 收件人 县,
     *                      "detail1"   : 收件人详细地址
     *                      "detail2"   : 寄件人详细地址
     *                  }
     * @param request   HttpServletRequest 对象，用于获取 session 信息
     * @return
     */
    @ApiOperation(value = "创建订单", response = ResponseMessage.class)
    @PostMapping("/addorder")
    @Transactional
    @ResponseBody
    public ResponseMessage createExpress(@RequestBody HashMap<String, String> map, HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null && session.getAttribute("userIdentity") != null) {
            int identity = (int) session.getAttribute("userIdentity");
            Consumer consumer = (Consumer) session.getAttribute("loginUser");
            if (identity == 0) {
                //当前登录的用户是普通用户

                //获取普通用户 id
                Integer consumerId = consumer.getId();

                //获取距离寄件人从近到远排列的所有网点信息列表
                List<Outlet> closestOutlet2sender = outletService.getClostestOutlet(map.get("region1"), map.get("region2"), map.get("region3"), map.get("detail2"));
                Integer sendOutletId = closestOutlet2sender.get(0).getId();

                //获取距离收件人从近到远排列的所有网点信息列表
                List<Outlet> closestOutlet2receiver = outletService.getClostestOutlet(map.get("address1"), map.get("address2"), map.get("address3"), map.get("detail1"));
                Integer receiveOutletId = closestOutlet2receiver.get(0).getId();

                //获取寄件信息
                Sendwise sendwise = new Sendwise();
                sendwise.setProvince(map.get("region1"));
                sendwise.setCity(map.get("region2"));
                sendwise.setDistrict(map.get("region3"));
                sendwise.setDetail_addr(map.get("detail2"));

                //获取收件信息
                Receivewise receivewise = new Receivewise();
                receivewise.setProvince(map.get("address1"));
                receivewise.setCity(map.get("address2"));
                receivewise.setDistrict(map.get("address3"));
                receivewise.setDetail_addr(map.get("detail1"));

                DetailExpressDTO detailExpressDTO = new DetailExpressDTO();
                detailExpressDTO.setExpress(new Express());
                detailExpressDTO.setSendwise(sendwise);
                detailExpressDTO.setReceivewise(receivewise);

                //提交创建订单请求
                if(expressService.insertExpress(detailExpressDTO, sendOutletId, receiveOutletId) != null){
                    //创建成功,返回信息
                    return ResponseMessage.success("创建成功");
                }else{
                    //创建失败,返回信息
                    return ResponseMessage.error("创建失败");
                }
            }else{
                //当前登录的用户不是普通用户
                return ResponseMessage.error("当前登录身份权限无法修改身份认证信息");
            }
        } else {
            //当前请求是由未登录的客户端发起的
            return ResponseMessage.notLogin();
        }
    }

}
