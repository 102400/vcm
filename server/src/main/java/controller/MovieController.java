package controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/movie/{movie_id}")
public class MovieController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String get(Model model, HttpServletRequest request, @PathVariable int movie_id) {
		
		
		return "movie";
	}

}