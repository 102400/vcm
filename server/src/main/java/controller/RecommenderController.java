package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pojo.FavoriteGenres;
import pojo.Genres;
import pojo.Movie;
import pojo.NearestNeighbor;
import service.FavoriteGenresService;
import service.NearestNeighborService;

@Controller
@RequestMapping("/recommender")
public class RecommenderController {
    
    @Autowired
    private NearestNeighborService nearestNeighborService;
    
    @Autowired
    private FavoriteGenresService favoriteGenresService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String get(Model model, HttpServletRequest request) {
	    
	    Integer userId = (Integer) request.getAttribute("userId");
	    
	    NearestNeighbor nearestNeighbor = new NearestNeighbor();
	    nearestNeighbor.setUserId(userId);
	    
	    FavoriteGenres favoriteGenres = new FavoriteGenres();
	    favoriteGenres.setUserId(userId);
	    
	    List<Genres> genresList = null;
	    List<NearestNeighbor> nearestNeighborList = null;
	    List<Movie> goodMovieList = null;
	    
	    try {
	        genresList = favoriteGenresService.findGenresListByUserId(favoriteGenres);
	        nearestNeighborList = nearestNeighborService.findNearestNeighborListByUserId(nearestNeighbor);
	        goodMovieList =  
	            nearestNeighborService.findNearestNeighborGoodMovieListByUserId(nearestNeighbor);
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    model.addAttribute("genresList", genresList);
	    model.addAttribute("nearestNeighborList", nearestNeighborList);
	    model.addAttribute("goodMovieList", goodMovieList);
	    
		return "recommender";
	}

}
