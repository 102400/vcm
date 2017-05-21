package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/message")
public class MessageController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String get() {
		
		
		return "message";
	}

}