package controller.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import json.LoginSuccessJson;
import pojo.User;
import service.UserService;
import util.MD5;
import util.VerifySource;

@Controller
@RequestMapping("/mobile/login")
public class LoginAndroidController {
    
    @Autowired
    private UserService userService;
    
//    @RequestMapping(method = RequestMethod.GET)
//    public String get() {
//        return "index";
//    }
    
    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody LoginSuccessJson post(String usernameOrEmail, String password) {
        LoginSuccessJson loginSuccessJson = new LoginSuccessJson();
        
//        System.out.println(usernameOrEmail + ":" + password);
        
        if (usernameOrEmail==null || password==null || usernameOrEmail.equals("") || password.equals("")) {
            return loginSuccessJson;
        }
        boolean isEmail = false;
        for(char c : usernameOrEmail.toCharArray()) {
            if(c=='@') {
                isEmail = true;
                break;
            }
        }
        
        User user = new User();
        if(isEmail) {
            user.setEmail(usernameOrEmail);
        }
        else {
            user.setUsername(usernameOrEmail);
        }
        User u = null;
        try {
            u = userService.findUserByUsernameOrEmail(user);
        }
        catch (Exception e) {
            loginSuccessJson.setMessage("异常");
            return loginSuccessJson;
        }
        if (u == null) {
            //查不到
            loginSuccessJson.setMessage("用户名不存在");
            return loginSuccessJson;
        }
        if (u.getPassword().equals(MD5.code(password))) {
            //验证成功
            loginSuccessJson.setUserId(u.getUserId());
            loginSuccessJson.setNickname(u.getNickname());
            loginSuccessJson.setVerify(VerifySource.getMD5(u.getUserId()));
            loginSuccessJson.setIsLoginSuccess(true);
            return loginSuccessJson;
        }
        loginSuccessJson.setMessage("用户名或密码错误");
        return loginSuccessJson;
    }

}
