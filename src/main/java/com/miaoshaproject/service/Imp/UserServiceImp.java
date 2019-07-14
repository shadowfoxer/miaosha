package com.miaoshaproject.service.Imp;

import com.miaoshaproject.dao.UserDOMapper;
import com.miaoshaproject.dao.UserPasswordDOMapper;
import com.miaoshaproject.dataobject.UserDO;
import com.miaoshaproject.dataobject.UserPasswordDO;
import com.miaoshaproject.error.BusinessExcepition;
import com.miaoshaproject.error.CommonError;
import com.miaoshaproject.error.EnBusinessError;
import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.model.UserModel;
import com.miaoshaproject.validator.ValidationResult;
import com.miaoshaproject.validator.ValidatorImp;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * useserviceimp
 *
 * @author
 * @create 2019-04-30 下午6:31
 **/

@Service
public class UserServiceImp implements UserService {


    @Autowired
    UserDOMapper userDOMapper;

    @Autowired
    UserPasswordDOMapper userPasswordDOMapper;

    @Autowired
    ValidatorImp validatorImp;

    @Override
    public UserModel getUser(Integer id) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);

        if(userDO == null){
            return null;
        }

        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());

        UserModel userModel = convertFromUserDO(userDO,userPasswordDO);

        return userModel;
    }

    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessExcepition {
        if(userModel == null){
            throw new BusinessExcepition(EnBusinessError.PARAMETER_VALIDATION_ERROR);
        }
//        if(userModel.getName()==null||
//                userModel.getTelphone()==null||
//                userModel.getAge()==null){
//            throw new BusinessExcepition(EnBusinessError.PARAMETER_VALIDATION_ERROR);
//        }

        ValidationResult result = validatorImp.validate(userModel);
        if (result.isHasErrors()){
            throw new BusinessExcepition(result.getErrorMsg(),EnBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        UserDO userDO = convertFromUserModle(userModel);
        userDOMapper.insertSelective(userDO);

        userModel.setId(userDO.getId());

        UserPasswordDO userPasswordDO = convertUserPwFromUserModle(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);

        return;
    }

    @Override
    public UserModel login(String telphone, String encrptPassword) throws BusinessExcepition {
        if(telphone ==null ||  encrptPassword== null){
            throw new BusinessExcepition(EnBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        UserDO userDO = userDOMapper.selectByTelphone(telphone);
        if(userDO == null){
            throw new BusinessExcepition("用户不存在",EnBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        UserPasswordDO  userPasswordDO=  userPasswordDOMapper.selectByUserId(userDO.getId());

        UserModel userModel = convertFromUserDO(userDO,userPasswordDO);

        if(!userPasswordDO.getEncrptPassword().equals(encrptPassword)){
            throw new BusinessExcepition("登陆失败",EnBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        return userModel;


    }

    private UserDO convertFromUserModle(UserModel userModel){
        if(userModel == null){
            return null;
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel,userDO);
        return userDO;
    }

    private UserPasswordDO convertUserPwFromUserModle(UserModel userModel){
        if(userModel == null){
            return null;
        }
        UserPasswordDO userPasswordDO = new UserPasswordDO();

        userPasswordDO.setEncrptPassword(userModel.getEncrptPassword());
        userPasswordDO.setUserId(userModel.getId());

        return userPasswordDO;
    }

    private UserModel convertFromUserDO(UserDO userDO, UserPasswordDO userPasswordDO){
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO,userModel);
        userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());

        return userModel;
    }
}
