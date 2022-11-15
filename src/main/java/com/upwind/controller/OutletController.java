package com.upwind.controller;

import com.upwind.DTO.OutletExpressDTO;
import com.upwind.VO.CourierInfoVO;
import com.upwind.VO.OutletInfoVO;
import com.upwind.pojo.Courier;
import com.upwind.pojo.Outlet;
import com.upwind.service.CourierService;
import com.upwind.service.ExpressService;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("outlet")
public class OutletController {

    @Autowired
    private OutletService outletService;
    @Autowired
    private CourierService courierService;
    @Autowired
    private ExpressService expressService;

    @ApiOperation(value = "网点注册", response = ResponseMessage.class)
    @PostMapping("/register")
    @Transactional
    @ResponseBody
    public ResponseMessage register (@RequestParam("title") String title,
                                     @RequestParam("name") String name,
                                     @RequestParam("phone") String phone,
                                     @RequestParam("password") String password,
                                     @RequestParam("province") String province,
                                     @RequestParam("city") String city,
                                     @RequestParam("district") String district,
                                     @RequestParam("detail_addr") String detail_addr) {

        if (outletService.getOutletByTitle(title).size() > 0)
            return ResponseMessage
                    .error("网点账号已存在");
        String psw = MD5Util.getMD5(password);
        Outlet outlet = new Outlet();
        outlet.setId(null);
        outlet.setTitle(title);
        outlet.setPhone(phone);
        outlet.setPassword(psw);
        outlet.setName(name);
        outlet.setProvince(province);
        outlet.setCity(city);
        outlet.setDistrict(district);
        outlet.setDetail_addr(detail_addr);
        if (outletService.insertOutlet(outlet) == null)
            return ResponseMessage.error("注册失败");
        else
            return ResponseMessage.success("注册成功");

    }

    @ApiOperation(value = "账号信息", response = ResponseMessage.class)
    @GetMapping("/accountInfo")
    @Transactional
    @ResponseBody
    // 这个其实不用请求，因为网点账号不像快递员，它要显示的信息已经全部存在 loginUser 里面了
    public ResponseMessage queryOutletAccountInfo (HttpServletRequest request) {

        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null && session.getAttribute("userIdentity") != null) {
            int identity = (int) session.getAttribute("userIdentity");
            if (identity == 2) {
                Outlet outlet = (Outlet) session.getAttribute("loginUser");
                outlet = outletService.getOutletById(outlet.getId());
                return ResponseMessage
                        .success("处理成功")
                        .addObject("outlet", outlet);
            } else {
                return ResponseMessage.error("当前登录身份权限无操作权限");
            }
        } else {
            return ResponseMessage.notLogin();
        }

    }

    @ApiOperation(value = "网点信息修改", response = ResponseMessage.class)
    @PutMapping("/updateOutlet")
    @Transactional
    @ResponseBody
    public ResponseMessage update (@RequestBody OutletInfoVO outletInfoVO, HttpServletRequest request) {

        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null && session.getAttribute("userIdentity") != null) {
            int identity = (int) session.getAttribute("userIdentity");
            if (identity == 2) {
                Outlet outlet = (Outlet) session.getAttribute("loginUser");
                Outlet new_outlet = new Outlet();
                new_outlet.setId(outlet.getId());
                new_outlet.setPhone(outletInfoVO.getPhone());
                new_outlet.setTitle(outletInfoVO.getTitle());
                new_outlet.setProvince(outletInfoVO.getProvince());
                new_outlet.setCity(outletInfoVO.getCity());
                new_outlet.setDistrict(outletInfoVO.getDistrict());
                new_outlet.setDetail_addr(outletInfoVO.getDetail_addr());
                if (!outlet.getName().equals(outletInfoVO.getName())) {          // 转让网点，修改负责人
                    if (outletInfoVO.getPhone() == null || outlet.getPhone().equals(outletInfoVO.getPhone()))
                        return ResponseMessage.error("转让网点需要输入新负责人手机号");
                    new_outlet.setName(outletInfoVO.getName());
                    // 默认初始密码为 123456
                    new_outlet.setPassword(MD5Util.getMD5("123456"));
                }
                if (!outletService.updateOutlet(new_outlet))
                    return ResponseMessage.error("修改失败");
                else {
                    session.setAttribute("loginUser", outletService.getOutletById(outlet.getId()));
                    return ResponseMessage.success("修改成功");
                }
            } else {
                return ResponseMessage.error("当前登录身份权限无操作权限");
            }
        } else {
            return ResponseMessage.notLogin();
        }

    }

    @ApiOperation(value = "查询下属快递员", response = ResponseMessage.class)
    @GetMapping("/courierList")
    @Transactional
    @ResponseBody
    public ResponseMessage queryCourierList (@RequestParam(value = "approved_flag", required = false) Integer approved_flag,
                                             @RequestParam(value = "job_no", required = false) String job_no,
                                             HttpServletRequest request) {

        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null && session.getAttribute("userIdentity") != null) {
            int identity = (int) session.getAttribute("userIdentity");
            if (identity == 2) {
                Outlet outlet = (Outlet) session.getAttribute("loginUser");
                String title = outlet.getTitle();
                List<Courier> courierList = courierService.getCourierByOutletId(outlet.getId());
                List<CourierInfoVO> courierInfoVOList = new ArrayList<>();
                for (Courier courier : courierList) {
                    // approved_flag 等于 0 表示待审核，等于 1 表示已通过，等于 -1 表示已拒绝
                    if (approved_flag != null && !approved_flag.equals(courier.getApproved_flag()))     // 查询某一身份状态的快递员
                        continue;
                    if (job_no != null && courier.getJob_no() != null && !job_no.equals(courier.getJob_no()))       // 查询某一工号的快递员
                        continue;;
                    courierInfoVOList.add(
                            new CourierInfoVO(
                                    courier.getId(),
                                    courier.getName(),
                                    courier.getPhone(),
                                    courier.getJob_no(),
                                    courier.getGender(),
                                    courier.getIdentity_num(),
                                    title
                            )
                    );
                }
                return ResponseMessage
                        .success("查询成功")
                        .addObject("courier_list", courierInfoVOList);
            } else {
                return ResponseMessage.error("当前登录身份权限无法查看列表");
            }
        } else {
            return ResponseMessage.notLogin();
        }

    }

    @ApiOperation(value = "删除下属快递员", response = ResponseMessage.class)
    @DeleteMapping("/deleteCourier/{courier_id}")
    @Transactional
    @ResponseBody
    // 按照逻辑，删除下属快递员只是把快递员的 outlet_id 设置为 null 即可, 但是由于数据库里快递订单是通过快递员与网点联系在一起的
    // 因此这样一来，删除下属快递员会导致一部分网点经手的订单与网点断联，解决方法只能往订单的寄件信息与收件信息中加入 outlet_id
    // 由于往订单的寄件信息与收件信息中加入 outlet_id ，需要改动好些业务逻辑代码，所以就不重构数据库了，直接将 outlet_id 字段设置为 null
    public ResponseMessage deleteCourier (@PathVariable Integer courier_id, HttpServletRequest request) {

        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null && session.getAttribute("userIdentity") != null) {
            int identity = (int) session.getAttribute("userIdentity");
            if (identity == 2) {
                Outlet outlet = (Outlet) session.getAttribute("loginUser");
                Courier courier = courierService.getCourierById(courier_id);
                if (courier.getOutlet_id().equals(outlet.getId()))
                    courier.setOutlet_id(null);
                else
                    return ResponseMessage.error("该快递员非本网点下属快递员");
                if (!courierService.updateCourier(courier))
                    return ResponseMessage.error("删除失败");
                else
                    return ResponseMessage.success("删除成功");
            } else {
                return ResponseMessage.error("当前登录身份权限无操作权限");
            }
        } else {
            return ResponseMessage.notLogin();
        }

    }

    @ApiOperation(value = "审核快递员身份", response = ResponseMessage.class)
    @PostMapping("/checkCourier/{courier_id}")
    @Transactional
    @ResponseBody
    public ResponseMessage checkCourier (@PathVariable Integer courier_id,
                                         @RequestParam("check_op") Integer check_op,
                                         HttpServletRequest request) {

        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null && session.getAttribute("userIdentity") != null) {
            int identity = (int) session.getAttribute("userIdentity");
            if (identity == 2) {
                if (check_op != -1 && check_op != 0 && check_op != 1)
                    return ResponseMessage.error("无效审核意见");
                Outlet outlet = (Outlet) session.getAttribute("loginUser");
                Courier courier = courierService.getCourierById(courier_id);
//                if (courier.getOutlet_id().equals(outlet.getId()))
//                    courier.setOutlet_id(null);
//                else
//                    return ResponseMessage.error("该快递员非本网点下属快递员");
                courier.setApproved_flag(check_op);
                if (!courierService.updateCourier(courier))
                    return ResponseMessage.error("操作失败");
                else
                    return ResponseMessage.success("操作成功");
            } else {
                return ResponseMessage.error("当前登录身份权限无操作权限");
            }
        } else {
            return ResponseMessage.notLogin();
        }

    }

    @ApiOperation(value = "查询网点经手快递", response = ResponseMessage.class)
    @GetMapping("/expressList")
    @Transactional
    @ResponseBody
    public ResponseMessage queryExpressList (@RequestParam(value = "order_no", required = false) String order_no,
                                             HttpServletRequest request) {

        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null && session.getAttribute("userIdentity") != null) {
            int identity = (int) session.getAttribute("userIdentity");
            if (identity == 2) {
                Outlet outlet = (Outlet) session.getAttribute("loginUser");
                List<OutletExpressDTO> outletExpressDTOList = expressService.getExpressByOutletId(outlet.getId(), order_no);
                return ResponseMessage
                        .success("查询成功")
                        .addObject("express_list", outletExpressDTOList);
            } else {
                return ResponseMessage.error("当前登录身份权限无法查看列表");
            }
        } else {
            return ResponseMessage.notLogin();
        }

    }

}
