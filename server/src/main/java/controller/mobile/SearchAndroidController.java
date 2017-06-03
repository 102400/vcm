package controller.mobile;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import json.MovieListJson;
import pojo.Movie;
import service.MovieService;

@Controller
@RequestMapping("/mobile/search")
public class SearchAndroidController {
    
    @Autowired
    private MovieService movieService;
    
    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody MovieListJson post(String q) {
        MovieListJson movieListJson = new MovieListJson();
        
        List<Movie> movieList = new ArrayList<>(1);
        
        List<Object> list = movieService.searchMovieByImdbIdOrDoubanIdOrNameZh(q);
        
        if (list.get(0) != null) {
            Movie imdbMovie = (Movie) list.get(0);
            movieList.add(imdbMovie);
            
            movieListJson.setMovieList(movieList);
            return movieListJson;
        }
        if (list.get(1) != null) {
            Movie doubanMovie = (Movie) list.get(1);
            
            movieList.add(doubanMovie);
            
            movieListJson.setMovieList(movieList);
            return movieListJson;
        }
        
        movieList = (List<Movie>) list.get(2);
        
        movieListJson.setMovieList(movieList);
        
        return movieListJson;
    }

}
