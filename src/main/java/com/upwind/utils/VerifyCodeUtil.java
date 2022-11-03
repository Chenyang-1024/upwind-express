package com.upwind.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName VerifyCodeUtil
 * @Description 检验登录时验证码
 **/
public class VerifyCodeUtil {

    public static boolean checkVerifyCode(String verifyCodeActual, HttpServletRequest request){
        String verifyCodeExpected = (String)request.getSession().getAttribute("verifyCode");
        System.out.println(verifyCodeExpected+"-------"+verifyCodeActual);
        return verifyCodeActual != null && verifyCodeActual.equals(verifyCodeExpected);
    }

}
