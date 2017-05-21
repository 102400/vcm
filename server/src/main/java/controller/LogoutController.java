package controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/logout")
public class LogoutController {
	
	@RequestMapping("")
	public String logout(HttpServletResponse response) {
		
		Cookie cookieUserId = new Cookie("user_id", "");
		Cookie cookieNickname = new Cookie("nickname", "");
		Cookie cookieVerify = new Cookie("verify", "");
		
		cookieUserId.setMaxAge(0);
		cookieNickname.setMaxAge(0);
		cookieVerify.setMaxAge(0);
			
		response.addCookie(cookieUserId);
		response.addCookie(cookieNickname);
		response.addCookie(cookieVerify);
		
		return "redirect:/";
	}

}