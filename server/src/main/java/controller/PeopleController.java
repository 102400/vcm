package controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/people/{people_id}")
public class PeopleController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String people(Model model,HttpServletRequest request, @PathVariable int people_id, @RequestParam(value="page", required=true, defaultValue="1") int page) {
		
		
		return "people";
	}

}