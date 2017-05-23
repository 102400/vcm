package controller;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import crawler.DoubanMovieSubjectCollectionsCrawler;
import crawler.DoubanMovieSubjectCrawler;
import crawler.DoubanMovieUserCollectCrawler;
import json.MovieJson;
import json.ObjectJson;
import json.SuccessJson;
import mapper.MovieMapper;
import mapper.UserMapper;
import pojo.Movie;
import pojo.Rating;
import pojo.User;
import service.MovieService;
import service.RatingService;
import service.UserService;
import status.Status;

@Controller
@RequestMapping("/new")
public class NewController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String get(Model model) {
	
	    model.addAttribute("batchNewThreadCount", Status.batchNewThreadCount);
	    
		return "new";
	}
	
	@Autowired
	private MovieService movieService;
	
	@Autowired
	private UserService userService;
	
	//ajax
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody SuccessJson post(HttpServletRequest request, @RequestBody MovieJson movieJson) {
	    SuccessJson successJson = new SuccessJson();
	    if(movieJson == null) return successJson;
	    
	    System.out.println(movieJson.getDoubanId());
	    
	    Movie movie = new Movie();
	    movie.setDoubanId(movieJson.getDoubanId());
	    
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
	
	@Autowired
	private RatingService ratingService;
	
	@Autowired
	private MovieMapper movieMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	//批量抓取豆瓣新建
	@RequestMapping(path = "/batch", method = RequestMethod.POST)
	public @ResponseBody SuccessJson batch(HttpServletRequest request, @RequestBody MovieJson movieJson) {
	    SuccessJson successJson = new SuccessJson();
	    if (!(boolean) request.getAttribute("isLogin") || 
	            (int) request.getAttribute("userId") != 1 ||
	            movieJson == null) {
	        return successJson;
	    }
	    //开启一个线程
	    new Thread() {
	        @Override
	        public void run() {
	            Status.batchNewThreadCount++;
	            try {
	                Thread.currentThread().sleep(1000*1);
	                
//	                userService.addZombieUserRatingAndMovieByDoubanId(movieJson.getDoubanId());
	                addZombieUserRatingAndMovieByDoubanId(movieJson.getDoubanId());
	            }
	            catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
	            finally {
	                Status.batchNewThreadCount--;
	            }
	        }
	        
	        private boolean addZombieUserRatingAndMovieByDoubanId(int doubanId) {
	            // TODO Auto-generated method stub
	            List<User> userList = DoubanMovieSubjectCollectionsCrawler.crawer(doubanId);
	            
	            try {
                    Thread.currentThread().sleep(((userList.size() + 1) /20) * 1000 * 2);
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
	            
	            
	            
	            Iterator<User> userIterator = userList.iterator();
	            
	            while (userIterator.hasNext()) {
	            
	                User user = userIterator.next();
	            
	                
	                try {
	                    userMapper.add(user);
	                }
	                catch (Exception e) {
	                    e.printStackTrace();
	                }
	                
	                try {
	                    //展开的 storyline 抓取不到
	                    List<Rating> ratingList =  DoubanMovieUserCollectCrawler.crawer(user);
	                    if(ratingList == null) continue;
	                    
	                    try {
	                        Thread.currentThread().sleep(((ratingList.size() + 1) /15) * 1000 * 2);
	                    } catch (InterruptedException e1) {
	                        // TODO Auto-generated catch block
	                        e1.printStackTrace();
	                    }
	                    
	                    for(Rating rating : ratingList) {
	                        
	                        try {
                                Thread.currentThread().sleep(1000*2);
                            } catch (InterruptedException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
	                        
	                        try {
	                        
	                            if (rating.getComment() == null) {
	                                rating.setComment("");
	                            }
	                            rating.setUserId(user.getUserId());
	                            
	                            Movie movie = new Movie();
	                            movie.setDoubanId(rating.getMovieId());
	                            
	                            if(movieMapper.findMovieByDoubanId(movie) == null ) {
	                                
//	                                Thread.currentThread().sleep(2000);
	                                
	                                movie = movieService.addMovieUseCrawlerByDoubanId(movie);
	                                
	                            }
	                            // 有问题?
	                            rating.setMovieId(movie.getMovieId());
	                            ratingService.changeRating(rating);
	                            
	                        }
	                        catch (Exception e) {
	                            e.printStackTrace();
	                            try {
	                                Thread.currentThread().sleep(1000*60);
    	                        } catch (InterruptedException e1) {
    	                            // TODO Auto-generated catch block
    	                            e1.printStackTrace();
    	                        }
	                        }
	                    }
	                }
	                catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	            
	            
	            return false;
	        }
	    }.start();
	    
	    successJson.setIsSuccess(true);
	    return successJson;
	}

}
