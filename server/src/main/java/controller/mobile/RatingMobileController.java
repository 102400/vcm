package controller.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import json.MovieJson;
import json.SuccessJson;
import pojo.Rating;
import service.RatingService;
import util.VerifySource;

@Controller
@RequestMapping("/mobile/rating")
public class RatingMobileController {
    
    @Autowired
    private RatingService ratingService;
    
    @RequestMapping(path = "/get", method = RequestMethod.POST)
    public @ResponseBody Rating getRating(int userId, String verify, int movieId) {
        
        if (userId == 0 || movieId == 0 || verify == null || "".equals(verify)) {
            return null;
        }
        if (!VerifySource.getMD5(userId).equals(verify)) {
            return null;
        }
        
        Rating rating = new Rating();
        rating.setMovieId(movieId);
        rating.setUserId(userId);
        
        rating = ratingService.findRatingByMovieIdAndUserId(rating);
        
        return rating;
    }
    
    @RequestMapping(path = "/post", method = RequestMethod.POST)
    public @ResponseBody SuccessJson postRating(int userId, String verify, int movieId, int rating, String comment) {
        
        SuccessJson successJson = new SuccessJson();
        
        if (userId == 0 || movieId == 0 || verify == null || "".equals(verify)) {
            return null;
        }
        if (!VerifySource.getMD5(userId).equals(verify)) {
            return null;
        }
        
        Rating r = new Rating();
        r.setMovieId(movieId);
        r.setRating(rating);
        r.setComment(comment);
        r.setUserId(userId);
        
        try {
            ratingService.changeRating(r);
        }
        catch (Exception e) {
            return successJson;
        }
        
        
        successJson.setIsSuccess(true);
        return successJson;
    }

}
