package service.impl;

import javax.validation.Validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mapper.UserMapper;
import pojo.User;
import service.UserService;

@Service
//@Validated  //告诉MethodValidationPostProcessor此Bean需要开启方法级别验证支持
@Transactional
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
//    public boolean addUser(@Valid User user) {
    public boolean addUser(User user) {
        // TODO Auto-generated method stub
        if (Validation.buildDefaultValidatorFactory().getValidator()
                .validate(user).size() != 0) {
            return false;
        }
        
        userMapper.add(user);
//        System.out.println("userId:" + user.getUserId());  //自增主键
        
        return true;
    }

    @Override
    public User findUserByUsernameOrEmail(User user) {
        // TODO Auto-generated method stub
        User u = null;
        if (user.getEmail()!=null && user.getEmail()!="") {
            u = userMapper.findUserByEmail(user);
        }
        else if (user.getUsername()!=null && user.getUsername()!="") {
            u = userMapper.findUserByUsername(user);
        }
        return u;
    }

}