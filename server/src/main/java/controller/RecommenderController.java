package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/recommender")
public class RecommenderController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String get() {
	
		return "recommender";
	}

}
