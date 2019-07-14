package com.miaoshaproject.controller;

import com.miaoshaproject.controller.viewobject.UserVO;
import com.miaoshaproject.dao.UserDOMapper;
import com.miaoshaproject.error.BusinessExcepition;
import com.miaoshaproject.error.EnBusinessError;
import com.miaoshaproject.response.CommonResponseType;
import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.model.UserModel;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.invoke.empty.Empty;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


/**
 * usercontroller
 *
 * @author
 * @create 2019-04-30 下午6:26
 **/

@Controller
@RequestMapping("user")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    @Autowired
    HttpServletRequest httpServletRequest;


    @RequestMapping(value = "/login",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonResponseType login(@RequestParam(name="telphone")String telphone,
                                    @RequestParam(name="password")String password) throws BusinessExcepition, UnsupportedEncodingException, NoSuchAlgorithmException {
        if(telphone == null||password == null){
            throw new BusinessExcepition("请正确填写信息",EnBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        UserModel userModel = userService.login(telphone,encodeMD5(password));

        //加入到用户session，作为登陆凭证
        httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);

        return CommonResponseType.create(null);


    }


    @RequestMapping(value = "/register",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonResponseType register(@RequestParam(name = "name") String name,
                                       @RequestParam(name ="telphone") String telphone,
                                       @RequestParam(name = "gender") Integer gender,
                                       @RequestParam(name = "age") Integer age,
                                       @RequestParam(name = "otpCode") String otpCode,
                                       @RequestParam(name = "password") String password) throws BusinessExcepition, UnsupportedEncodingException, NoSuchAlgorithmException {

        //验证otpcode一致
        String sessionOtpCode = (String) this.httpServletRequest.getSession().getAttribute(telphone);
        if(!sessionOtpCode.equals(otpCode)){
            throw new BusinessExcepition("短信验证码错误",EnBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setTelphone(telphone);
        userModel.setGender(gender);
        userModel.setAge(age);
        userModel.setRegisterMode("byphone");
        userModel.setEncrptPassword(encodeMD5(password));

        userService.register(userModel);

        return CommonResponseType.create(null);

    }

    public String encodeMD5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();

        String newString = base64Encoder.encode(messageDigest.digest(str.getBytes("utf-8")));
        return newString;
    }


    @RequestMapping(value = "/getOtp",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonResponseType getOtp(@RequestParam(name = "telphone") String telphone) throws BusinessExcepition {

        //生成otp
        int otpNum = new Random().nextInt(99999);
        otpNum +=10000;

        String otpCode = String.valueOf(otpNum);

        httpServletRequest.getSession().setAttribute(telphone,otpCode);

        System.out.println("telphone = "+ telphone + "&otpCode = "+ otpCode );

        return CommonResponseType.create(null);

    }


    @RequestMapping("/get")
    @ResponseBody
    public CommonResponseType getUser(@RequestParam(name = "id") Integer id) throws Exception {

        UserModel userModel = userService.getUser(id);

        if(userModel == null){
            throw new BusinessExcepition(EnBusinessError.USER_NOT_EXIT);
        }

        UserVO userVO = convertFromUserModel(userModel);

        return CommonResponseType.create(userVO);
    }


    private UserVO convertFromUserModel(UserModel userModel){

        if(userModel == null){
            return null;
        }

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel,userVO);
        return userVO;
    }


}
