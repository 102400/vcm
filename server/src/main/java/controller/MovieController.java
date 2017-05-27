package controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import config.Config;
import json.MovieJson;
import json.SuccessJson;
import mapper.RatingMapper;
import pojo.Genres;
import pojo.Movie;
import pojo.Rating;
import service.MovieService;
import service.RatingService;

@Controller
@RequestMapping("/movie")
public class MovieController {
    
    @Autowired
    private MovieService movieService;
    
    @Autowired
    private RatingService ratingService;
	
	@RequestMapping(path = "/{douban_id}", method = RequestMethod.GET)
	public String get(Model model, HttpServletRequest request, @PathVariable int douban_id) {
		
	    Movie movie = new Movie();
	    movie.setDoubanId(douban_id);
	    
	    List<Object> list = movieService.findMovieAndGenresListByDoubanId(movie);
	    movie = (Movie) list.get(0);
	    List<Genres> genresList = (List<Genres>) list.get(1);
	    
	    Rating rating = null;
	    if ((boolean) request.getAttribute("isLogin")) {
	        rating = new Rating();
	        rating.setMovieId(movie.getMovieId());
	        rating.setUserId((Integer) request.getAttribute("userId"));
	        
	        rating = ratingService.findRatingByMovieIdAndUserId(rating);
	    }
	    
	    model.addAttribute("movie", movie);
	    model.addAttribute("genresList", genresList);
	    model.addAttribute("rating", rating);
		
		return "movie";
	}
	
	@RequestMapping(path = "/cover/{douban_id}", method = RequestMethod.GET)
	public void movieCover(HttpServletRequest request, HttpServletResponse response, @PathVariable int douban_id) {
	    Path file = Paths.get(Config.MOVIE_COVER_PATH, douban_id + ".jpg");
        try {
            Files.copy(file, response.getOutputStream());
        } catch (IOException e) {
//          e.printStackTrace();
        }
	}
	
	@RequestMapping(path = "/rating", method = RequestMethod.POST)
	public @ResponseBody SuccessJson rating(HttpServletRequest request ,@RequestBody MovieJson movieJson) {
	    SuccessJson successJson = new SuccessJson();
	    
	    if (movieJson.getRating() >10 || movieJson.getRating() < 0 || movieJson.getComment().length() > 256) {
	        return successJson;
	    }
	    
	    Integer movieId = movieJson.getMovieId();
	    Integer userId = (Integer) request.getAttribute("userId");
	    
	    Rating rating = new Rating();
	    rating.setMovieId(movieId);
	    rating.setRating(movieJson.getRating());
	    rating.setComment(movieJson.getComment());
	    rating.setUserId(userId);
	    
//	    System.out.println("rating userId" + rating.getUserId());
	    
	    ratingService.changeRating(rating);
	    
	    
	    
	    
	    
	    successJson.setIsSuccess(true);
	    return successJson;
	}

}