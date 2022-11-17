package com.upwind.controller;

import com.upwind.DTO.OutletExpressDTO;
import com.upwind.pojo.*;
import com.upwind.service.*;
import com.upwind.utils.MD5Util;
import com.upwind.utils.ResponseMessage;
import com.upwind.utils.VerifyCodeUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/*
 * 提供的接口:
 *
 * 获取所有快递员信息
 * 根据网点查询快递员信息
 * 根据快递员 id 查询快递员信息
 * 获取所有快件信息
 * 根据网点 id 查询快件信息
 * 根据快递员 id 查询快件信息
 * 查询所有用户
 * 根据手机号查询用户
 */

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService;
    @Autowired
    private ConsumerService consumerService;
    @Autowired
    private CourierService courierService;
    @Autowired
    private ExpressService expressService;
    @Autowired
    private OutletService outletService;

    @ApiOperation(value = "添加网点", response = ResponseMessage.class)
    @PostMapping("/addOutlet")
    @Transactional
    @ResponseBody
    public ResponseMessage addOutlet(@RequestParam("title")     String title,
                                     @RequestParam("password")  String password,
                                     @RequestParam("name")      String name,
                                     @RequestParam("phone")     String phone,
                                     @RequestParam("province")  String province,
                                     @RequestParam("city")      String city,
                                     @RequestParam("district")  String district,
                                     @RequestParam("address")   String address,
                                     HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null && session.getAttribute("userIdentity") != null) {
            int identity = (int) session.getAttribute("userIdentity");
            if (identity == 3) {
                //当前登录的用户是管理员

                //获取网点信息
                Outlet outlet = new Outlet();
                outlet.setCity(city);
                outlet.setDistrict(district);
                outlet.setName(name);
                outlet.setPhone(phone);
                outlet.setTitle(title);
                outlet.setProvince(province);
                outlet.setPassword(MD5Util.getMD5(password));
                outlet.setDetail_addr(address);

                //添加网点信息
                if(outletService.insertOutlet(outlet) != null){
                    //查询成功,返回信息
                    return ResponseMessage.success("创建成功");
                }else{
                    //查询失败,返回信息
                    return ResponseMessage.error("创建失败");
                }
            }else{
                //当前登录的用户不是系统管理员
                return ResponseMessage.error("当前登录身份权限无法创建网点");
            }
        } else {
            //当前请求是由未登录的客户端发起的
            return ResponseMessage.notLogin();
        }
    }

    /**
     * 获取所有网点信息
     * @param request   HttpServletRequest 对象，用于获取 session 信息
     * @return          所有网点信息列表
     */
    @ApiOperation(value = "获取所有网点信息", response = ResponseMessage.class)
    @GetMapping("/getAllOutlet")
    @Transactional
    @ResponseBody
    public ResponseMessage getAllOutlet(HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null && session.getAttribute("userIdentity") != null) {
            int identity = (int) session.getAttribute("userIdentity");
            if (identity == 3) {
                //当前登录的用户是管理员

                //定义返回的数据
                List<Outlet> outletList;

                //查询网点信息
                if((outletList = outletService.getAllOutlet()) != null){
                    //查询成功,返回信息
                    return ResponseMessage.success("查询成功")
                            .addObject("outletList", outletList);
                }else{
                    //查询失败,返回信息
                    return ResponseMessage.error("查询失败");
                }
            }else{
                //当前登录的用户不是系统管理员
                return ResponseMessage.error("当前登录身份权限无法查询所有网点");
            }
        } else {
            //当前请求是由未登录的客户端发起的
            return ResponseMessage.notLogin();
        }
    }

    /**
     * 获取所有快递员信息
     * @param request   HttpServletRequest 对象，用于获取 session 信息
     * @return          所有快递员信息列表
     */
    @ApiOperation(value = "获取所有快递员信息", response = ResponseMessage.class)
    @GetMapping("/getAllCourier")
    @Transactional
    @ResponseBody
    public ResponseMessage getAllCourier(@RequestParam("approved_flag") Integer approvedFlag, HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null && session.getAttribute("userIdentity") != null) {
            int identity = (int) session.getAttribute("userIdentity");
            if (identity == 3) {
                //当前登录的用户是管理员

                //定义从数据库查询的数据
                List<Courier> courierList;

                //定义返回的数据
                List<CourierDTO> resultDataList = new ArrayList<>();

                //查询快递员信息
                if((courierList = courierService.getAllCourier()) != null){

                    for(Courier courier : courierList){
                        if(courier.getApproved_flag().equals(approvedFlag)){
                            resultDataList.add(new CourierDTO(courier, outletService.getOutletById(courier.getOutlet_id()).getTitle()));
                        }
                    }

                    //查询成功,返回信息
                    return ResponseMessage.success("查询成功")
                            .addObject("courierList", resultDataList);
                }else{
                    //查询失败,返回信息
                    return ResponseMessage.error("查询失败");
                }
            }else{
                //当前登录的用户不是系统管理员
                return ResponseMessage.error("当前登录身份权限无法查询所有快递员");
            }
        } else {
            //当前请求是由未登录的客户端发起的
            return ResponseMessage.notLogin();
        }
    }


    /**
     * 根据网点获取快递员信息
     * @param outletId  管理员选择的网点的 id
     * @param request   HttpServletRequest 对象，用于获取 session 信息
     * @return          网点 id 对应网点内的所有快递员信息
     */
    @ApiOperation(value = "根据网点获取快递员信息", response = ResponseMessage.class)
    @PostMapping("/getCouriersByOutletId")
    @Transactional
    @ResponseBody
    public ResponseMessage getCouriersByOutlet(@RequestParam("outletId") Integer outletId, HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null && session.getAttribute("userIdentity") != null) {
            int identity = (int) session.getAttribute("userIdentity");
            if (identity == 3) {
                //当前登录的用户是管理员

                //定义返回的数据
                List<Courier> courierList;

                //查询快递员信息
                if((courierList = courierService.getCourierByOutletId(outletId)) != null){
                    //查询成功,返回信息
                    return ResponseMessage.success("查询成功")
                                          .addObject("courierList", courierList);
                }else{
                    //查询失败,返回信息
                    return ResponseMessage.error("查询失败");
                }
            }else{
                //当前登录的用户不是系统管理员
                return ResponseMessage.error("当前登录身份权限无法查询快递员信息");
            }
        } else {
            //当前请求是由未登录的客户端发起的
            return ResponseMessage.notLogin();
        }
    }


    /**
     * 根据快递员 id 查询快递员信息
     * @param courierId     系统管理员选择的快递员的 id
     * @param request       HttpServletRequest 对象，用于获取 session 信息
     * @return
     */
    @ApiOperation(value = "根据快递员 id 查询快递员信息", response = ResponseMessage.class)
    @PostMapping("/getCouriersByIdCourierId")
    @Transactional
    @ResponseBody
    public ResponseMessage getCouriersByIdCourierId(@RequestParam("courierId") Integer courierId, HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null && session.getAttribute("userIdentity") != null) {
            int identity = (int) session.getAttribute("userIdentity");
            if (identity == 3) {
                //当前登录的用户是管理员

                //定义返回的数据
                Courier courier;

                //查询快递员信息
                if((courier = courierService.getCourierById(courierId)) != null){
                    //查询成功,返回信息
                    return ResponseMessage.success("查询成功")
                                          .addObject("courier", courier);
                }else{
                    //查询失败,返回信息
                    return ResponseMessage.error("查询失败");
                }
            }else{
                //当前登录的用户不是系统管理员
                return ResponseMessage.error("当前登录身份权限无法查询快递员信息");
            }
        } else {
            //当前请求是由未登录的客户端发起的
            return ResponseMessage.notLogin();
        }
    }


    /**
     * 获取所有快件信息
     * @param request   HttpServletRequest 对象，用于获取 session 信息
     * @return          所有快件信息
     */
    @ApiOperation(value = "获取所有快件信息", response = ResponseMessage.class)
    @GetMapping("/getAllExpress")
    @Transactional
    @ResponseBody
    public ResponseMessage getAllExpress(HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null && session.getAttribute("userIdentity") != null) {
            int identity = (int) session.getAttribute("userIdentity");
            if (identity == 3) {
                //当前登录的用户是管理员

                //定义返回的数据
                List<Express> expressList;


                //查询快递信息
                if((expressList = expressService.getAllExpress()) != null){
                    System.out.println(expressList.get(0).getOrder_time());
                    //查询成功,返回信息
                    return ResponseMessage.success("查询成功")
                                          .addObject("expressList", expressList);
                }else{
                    //查询失败,返回信息
                    return ResponseMessage.error("查询失败");
                }
            }else{
                //当前登录的用户不是系统管理员
                return ResponseMessage.error("当前登录身份权限无法查询快件信息");
            }
        } else {
            //当前请求是由未登录的客户端发起的
            return ResponseMessage.notLogin();
        }
    }


    /**
     * 根据网点 id 查询快件信息
     * @param outletId      系统管理员选择的网点 id
     * @param request       HttpServletRequest 对象，用于获取 session 信息
     * @return
     */
    @ApiOperation(value = "根据网点 id 查询快件信息", response = ResponseMessage.class)
    @PostMapping("/getExpressesByOutletId")
    @Transactional
    @ResponseBody
    public ResponseMessage getExpressesByOutletId(@RequestParam("outletId") Integer outletId, HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null && session.getAttribute("userIdentity") != null) {
            int identity = (int) session.getAttribute("userIdentity");
            if (identity == 3) {
                //当前登录的用户是管理员

                //定义返回的数据
                List<OutletExpressDTO> expressList;

                //查询快递信息
                if((expressList = expressService.getExpressByOutletId(outletId, null)) != null){
                    //查询成功,返回信息
                    return ResponseMessage.success("查询成功")
                                          .addObject("expressList", expressList);
                }else{
                    //查询失败,返回信息
                    return ResponseMessage.error("查询失败");
                }
            }else{
                //当前登录的用户不是系统管理员
                return ResponseMessage.error("当前登录身份权限无法查询快件信息");
            }
        } else {
            //当前请求是由未登录的客户端发起的
            return ResponseMessage.notLogin();
        }
    }


    /**
     * 根据快递员 id 查询快件信息
     * @param courierId     系统管理员选择的快递员 id
     * @param request       HttpServletRequest 对象，用于获取 session 信息
     * @return
     */
    @ApiOperation(value = "根据快递员 id 查询快件信息", response = ResponseMessage.class)
    @PostMapping("/getExpressesByCourierId")
    @Transactional
    @ResponseBody
    public ResponseMessage getExpressesByCourierId(@RequestParam("courierId") Integer courierId, HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null && session.getAttribute("userIdentity") != null) {
            int identity = (int) session.getAttribute("userIdentity");
            if (identity == 3) {
                //当前登录的用户是管理员

                //定义返回的数据
                List<OutletExpressDTO> expressList;

                //获取快递员所属的网点 id
                Integer outletId = courierService.getCourierById(courierId).getOutlet_id();

                //查询快递信息
                if((expressList = expressService.getExpressByOutletId(outletId, null)) != null){
                    //查询成功,删去非目标快递员的快递
                    expressList.removeIf(outletExpressDTO -> !outletExpressDTO.getCourier().getId().equals(courierId));
                    //返回信息
                    return ResponseMessage.success("查询成功")
                                          .addObject("expressList", expressList);
                }else{
                    //查询失败,返回信息
                    return ResponseMessage.error("查询失败");
                }
            }else{
                //当前登录的用户不是系统管理员
                return ResponseMessage.error("当前登录身份权限无法查询快件信息");
            }
        } else {
            //当前请求是由未登录的客户端发起的
            return ResponseMessage.notLogin();
        }
    }


    /**
     * 获取所有普通用户信息
     * @param request   HttpServletRequest 对象，用于获取 session 信息
     * @return          所有普通用户的信息
     */
    @ApiOperation(value = "获取所有普通用户信息", response = ResponseMessage.class)
    @GetMapping("/getAllConsumer")
    @Transactional
    @ResponseBody
    public ResponseMessage getAllConsumer(HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null && session.getAttribute("userIdentity") != null) {
            int identity = (int) session.getAttribute("userIdentity");
            if (identity == 3) {
                //当前登录的用户是管理员

                //定义返回的数据
                List<Consumer> consumerList;

                //获取所有用户信息
                if((consumerList = consumerService.getAllConsumer()) != null){
                    //查询成功,返回信息
                    return ResponseMessage.success("查询成功")
                                          .addObject("consumerList", consumerList);
                }else{
                    //查询失败,返回信息
                    return ResponseMessage.error("查询失败");
                }
            }else{
                //当前登录的用户不是系统管理员
                return ResponseMessage.error("当前登录身份权限无法查询用户信息");
            }
        } else {
            //当前请求是由未登录的客户端发起的
            return ResponseMessage.notLogin();
        }
    }


    /**
     * 根据手机号获取用户信息
     * @param phone     系统管理员输入的手机号
     * @param request   HttpServletRequest 对象，用于获取 session 信息
     * @return          查询出的用户信息
     */
    @ApiOperation(value = "根据手机号码获取用户信息", response = ResponseMessage.class)
    @PostMapping("/getConsumerByPhone")
    @Transactional
    @ResponseBody
    public ResponseMessage getConsumerByPhone(@RequestParam("phone") String phone, HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null && session.getAttribute("userIdentity") != null) {
            int identity = (int) session.getAttribute("userIdentity");
            if (identity == 3) {
                //当前登录的用户是管理员

                //定义返回的数据
                Consumer consumer;

                //获取所有用户信息
                if((consumer = consumerService.getConsumerByPhone(phone)) != null){
                    //查询成功,返回信息
                    return ResponseMessage.success("查询成功")
                                          .addObject("consumer", consumer);
                }else{
                    //查询失败,返回信息
                    return ResponseMessage.error("查询失败");
                }
            }else{
                //当前登录的用户不是系统管理员
                return ResponseMessage.error("当前登录身份权限无法查询用户信息");
            }
        } else {
            //当前请求是由未登录的客户端发起的
            return ResponseMessage.notLogin();
        }
    }

    private static class CourierDTO{

        public CourierDTO(Courier courier, String title) {
            this.title          = title;
            this.id             = courier.getId();
            this.approved_flag  = courier.getApproved_flag();
            this.outlet_id      = courier.getOutlet_id();
            this.gender         = courier.getGender();
            this.identity_num   = courier.getIdentity_num();
            this.job_no         = courier.getJob_no();
            this.phone          = courier.getPhone();
            this.name           = courier.getName();
            this.password       = courier.getPassword();

        }

        private String title;

        private Integer id;

        private String job_no;

        private String phone;

        private String password;

        private String name;

        private String gender;

        private String identity_num;

        private Integer approved_flag;

        private Integer outlet_id;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getJob_no() {
            return job_no;
        }

        public void setJob_no(String job_no) {
            this.job_no = job_no == null ? null : job_no.trim();
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone == null ? null : phone.trim();
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password == null ? null : password.trim();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name == null ? null : name.trim();
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender == null ? null : gender.trim();
        }

        public String getIdentity_num() {
            return identity_num;
        }

        public void setIdentity_num(String identity_num) {
            this.identity_num = identity_num == null ? null : identity_num.trim();
        }

        public Integer getApproved_flag() {
            return approved_flag;
        }


        public void setApproved_flag(Integer approved_flag) {
            this.approved_flag = approved_flag;
        }


        public Integer getOutlet_id() {
            return outlet_id;
        }


        public void setOutlet_id(Integer outlet_id) {
            this.outlet_id = outlet_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

}
