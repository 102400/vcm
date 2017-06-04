package controller.mobile;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import json.MovieListJson;
import pojo.Movie;
import pojo.NearestNeighbor;
import service.NearestNeighborService;
import util.VerifySource;

@Controller
@RequestMapping("/mobile/recommend")
public class RecommendMobileController {
    
    @Autowired
    private NearestNeighborService nearestNeighborService;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody MovieListJson post(int userId, String verify) {
        MovieListJson movieListJson = new MovieListJson();
        
        if (userId == 0 || verify == null || "".equals(verify)) {
            return null;
        }
        if (!VerifySource.getMD5(userId).equals(verify)) {
            return null;
        }
        List<Movie> goodMovieList = null;
        
        NearestNeighbor nearestNeighbor = new NearestNeighbor();
        nearestNeighbor.setUserId(userId);
        
        try {
            goodMovieList =  
                    nearestNeighborService.findNearestNeighborGoodMovieListByUserId(nearestNeighbor);
        }
        catch (Exception e) {
            return null;
        }
        movieListJson.setMovieList(goodMovieList);
        
        return movieListJson;
    }
    
}
