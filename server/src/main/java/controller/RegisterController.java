package controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pojo.User;
import service.UserService;
import util.MD5;

@Controller
@RequestMapping("/register")
public class RegisterController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String get() {
		return "register";
	}
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.POST)
	public String post(Model model, String nickname, String username, String email, String password) {
//    public String post(Model model, @Valid User user) {
	        
	    User user = new User();
	    user.setNickname(nickname);
	    user.setUsername(username);
	    user.setEmail(email);
	    user.setPassword(MD5.code(password));
	    
//	    user.setPassword(MD5.code(user.getPassword()));
	    model.addAttribute("message", "注册失败");
	    try {
	        if (userService.addUser(user))
	            model.addAttribute("message", "注册成功");
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	    return "register";
	}

}
