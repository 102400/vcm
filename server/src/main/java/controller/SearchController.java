package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import mapper.MovieMapper;
import pojo.Movie;
import service.MovieService;

@Controller
@RequestMapping("/search")
public class SearchController {
    
    @Autowired
    private MovieService movieService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String get(Model model, String q) {
	    
	    if ("".equals(q)) return "search";
	    
	    
	    List<Object> list = movieService.searchMovieByImdbIdOrDoubanIdOrNameZh(q);
	    
	    
//	    if (list.get(0) != null) {
//	        Movie imdbMovie = (Movie) list.get(0);
//        }
	    
	    if (list.get(1) != null) {
	        Movie doubanMovie = (Movie) list.get(1);
	        return "redirect:/movie/" + doubanMovie.getDoubanId();
	    }
	    
	    
	    model.addAttribute("imdbMovie", list.get(0));
	    model.addAttribute("movieList", list.get(2));
	    
		return "search";
	}

}
