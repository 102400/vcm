package controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import crawler.DoubanMovieSubjectCrawler;
import json.DoubanJson;
import json.ObjectJson;
import json.SuccessJson;
import pojo.Movie;
import service.MovieService;

@Controller
@RequestMapping("/new")
public class NewController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String get() {
	
		return "new";
	}
	
	@Autowired
	private MovieService movieService;
	
	//ajax
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody SuccessJson post(HttpServletRequest request, @RequestBody DoubanJson doubanJson) {
	    SuccessJson successJson = new SuccessJson();
	    if(doubanJson == null) return successJson;
	    
	    System.out.println(doubanJson.getDoubanId());
	    
	    Movie movie = new Movie();
	    movie.setDoubanId(doubanJson.getDoubanId());
	    
	    movie = movieService.addMovieUseCrawlerByDoubanId(movie);
	    
//	    DoubanMovieSubjectCrawler subjectCrawler = new DoubanMovieSubjectCrawler();
//	    
//	    movie = subjectCrawler.crawer(doubanJson.getDoubanId());
	    
	    if (movie != null) {
    	    successJson.setObject(movie);
    	    successJson.setIsSuccess(true);
	    }
	    return successJson;
	}
	
	//批量抓取豆瓣新建
	@RequestMapping(path = "/batch", method = RequestMethod.POST)
	public String batch() {
	    
	    //开启一个线程
	    
	    return "new";
	}

}
