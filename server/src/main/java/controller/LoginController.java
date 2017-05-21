package controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pojo.User;
import service.UserService;
import util.MD5;
import util.VerifySource;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String get() {
	
		return "login";
	}
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.POST)
	public String post(Model model, HttpServletResponse response, String username, String password, String remember) {
	    if(username==null||password==null||username.equals("")||password.equals("")) {
            return "login";
        }
        boolean isEmail = false;
        for(char c : username.toCharArray()) {
            if(c=='@') {
                isEmail = true;
                break;
            }
        }
        
        User user = new User();
        if(isEmail) {
            user.setEmail(username);
        }
        else {
            user.setUsername(username);
        }
        User u = null;
        try {
            u = userService.findUserByUsernameOrEmail(user);
        }
        catch(Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "用户名或密码错误");
            return "login";
        }
        
        if(u==null) {
            //查不到
            model.addAttribute("message", "用户名或密码错误");
            System.out.println("password == null");
            return "login";
        }
        if(u.getPassword().equals(MD5.code(password))) {
            //验证成功,设置cookie
            StringBuilder nickname = new StringBuilder();  //简单加密过后的nickname
            for(char c : u.getNickname().toCharArray()) {
                nickname.append((int)c + "&");
            }
            
            Cookie cookieUserId = new Cookie("user_id", u.getUserId() + "");
            Cookie cookieNickname = new Cookie("nickname", nickname.toString());
            Cookie cookieVerify = new Cookie("verify", VerifySource.getMD5(u.getUserId()));
            if("on".equals(remember)) {
                cookieUserId.setMaxAge(60*60*24*42);
                cookieNickname.setMaxAge(60*60*24*42);
                cookieVerify.setMaxAge(60*60*24*42);
            }
            response.addCookie(cookieUserId);
            response.addCookie(cookieNickname);
            response.addCookie(cookieVerify);
            return "redirect:/";
        }
        else {
            model.addAttribute("message", "用户名或密码错误");
            System.out.println("!password");
            return "login";
        }
	}

}
