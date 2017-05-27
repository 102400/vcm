package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import domain.RatingAndGenresCount;
import pojo.Rating;
import service.RatingService;
import service.UserService;

@Controller
@RequestMapping("/people/{people_id}")
public class PeopleController {
    
//    @Autowired
//    private UserService userService;
    
    @Autowired
    private RatingService ratingService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String people(Model model, HttpServletRequest request, @PathVariable Integer people_id) {
		
	    if (people_id == null) return "index";
	    
	    Rating rating = new Rating();
	    rating.setUserId(people_id);
	    
	    List<Object> list = ratingService.selectAllRatingAndGenresCountListByUserId(rating);
		List<RatingAndGenresCount> ratingCountList =  (List<RatingAndGenresCount>) list.get(0);
		List<RatingAndGenresCount> genresCountList = (List<RatingAndGenresCount>) list.get(1);
		
		model.addAttribute("ratingCountList", ratingCountList);
		model.addAttribute("genresCountList", genresCountList);
	    
		return "people";
	}

}