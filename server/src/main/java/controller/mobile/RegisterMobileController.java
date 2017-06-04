package controller.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import json.SuccessJson;
import pojo.User;
import service.UserService;
import util.MD5;

@Controller
@RequestMapping("/mobile/register")
public class RegisterMobileController {
    
    @Autowired
    private UserService userService;
    
    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody SuccessJson post(String nickname, String username, String email, String password) {
        SuccessJson successJson = new SuccessJson();
        
        if (nickname == null || username == null || email == null || password == null ||
                "".equals(nickname) || "".equals(username) || "".equals(email) || "".equals(password)) {
            return successJson;
        }
        
        User user = new User();
        user.setNickname(nickname);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(MD5.code(password));
        
        try {
            userService.addUser(user);
            successJson.setInfo("注册成功");
            successJson.setIsSuccess(true);
        }
        catch (Exception e) {
            successJson.setInfo("注册失败");
        }
        
        return successJson;
    }

}
