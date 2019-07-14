package com.miaoshaproject.service;

import com.miaoshaproject.error.BusinessExcepition;
import com.miaoshaproject.service.model.UserModel;

/**
 * uservice
 *
 * @author
 * @create 2019-04-30 下午6:30
 **/
public interface UserService {

    UserModel getUser(Integer id);

    void register(UserModel userModel) throws BusinessExcepition;

    UserModel login(String telphone ,String password) throws BusinessExcepition;
}
