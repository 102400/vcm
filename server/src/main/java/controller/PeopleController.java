package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import domain.GenresRatingStats;
import domain.RatingStats;
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
	    
	    Rating peopleRating = new Rating();
	    peopleRating.setUserId(people_id);
	    
	    List<Object> pList = ratingService.selectAllGenresRatingStatsListByUserId(peopleRating);
		List<GenresRatingStats> pRatingCountList =  (List<GenresRatingStats>) pList.get(0);
		List<GenresRatingStats> pGenresStatsList = (List<GenresRatingStats>) pList.get(1);
		RatingStats pRatingStats = (RatingStats) pList.get(2);
		
		model.addAttribute("pRatingCountList", pRatingCountList);
		model.addAttribute("pGenresStatsList", pGenresStatsList);
		model.addAttribute("pRatingStats", pRatingStats);
		
		if ((boolean) request.getAttribute("isLogin")) {
		    int userId = (int) request.getAttribute("userId");
		    
		    if (userId == people_id) return "people";
		    
		    Rating myRating = new Rating();
		    myRating.setUserId(userId);
		    
		    List<Object> mList = ratingService.selectAllGenresRatingStatsListByUserId(myRating);
	        List<GenresRatingStats> mRatingCountList =  (List<GenresRatingStats>) mList.get(0);
	        List<GenresRatingStats> mGenresStatsList = (List<GenresRatingStats>) mList.get(1);
	        RatingStats mRatingStats = (RatingStats) mList.get(2);
	        
	        model.addAttribute("mRatingCountList", mRatingCountList);
	        model.addAttribute("mGenresStatsList", mGenresStatsList);
	        model.addAttribute("mRatingStats", mRatingStats);
	        
	        float mAvgRating = mRatingStats.getAvgRating();
	        float mpRatingRatio = mAvgRating / pRatingStats.getAvgRating();
	        model.addAttribute("mpRatingRatio", mpRatingRatio);
	        
	        List<GenresRatingStats> wpGenresStatsList = new ArrayList<>(pGenresStatsList.size());
	        for (int i=0; i<pGenresStatsList.size(); i++) {
	            GenresRatingStats grs = new GenresRatingStats();
	            GenresRatingStats pgrs = pGenresStatsList.get(i);
	            float newAvgRating = pgrs.getAvgRating() * mpRatingRatio;
	            grs.setCount(pgrs.getCount());
	            grs.setGenresId(pgrs.getGenresId());
	            grs.setGenresNameZh(pgrs.getGenresNameZh());
	            grs.setRatio(pgrs.getRatio());
	            grs.setAvgRating(newAvgRating);
	            grs.setAvgDifference(newAvgRating - mAvgRating);
	            grs.setAvgRatio(pgrs.getAvgRatio());
	            
	            wpGenresStatsList.add(grs);
	        }
	        
	        model.addAttribute("wpGenresStatsList", wpGenresStatsList);
		}
		
	    
		return "people";
	}

}