package com.miaoshaproject;

import com.miaoshaproject.dao.UserDOMapper;
import com.miaoshaproject.dataobject.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * app
 *
 * @author
 * @create 2019-04-29 下午7:27
 **/


@SpringBootApplication(scanBasePackages = {"com.miaoshaproject"})
@RestController
@MapperScan("com.miaoshaproject.dao")
public class AppTest
{

    @Autowired
    private UserDOMapper userDOMapper;

    @RequestMapping("/")
    public String home(){
        UserDO userDO = userDOMapper.selectByPrimaryKey(1);
        if(null == userDO){
            return "用户不存在";
        }else {
            return userDO.getName();
        }
    }

    public static void main(String[] args){
        System.out.println("hello world");
        SpringApplication.run(AppTest.class,args);
    }
}
