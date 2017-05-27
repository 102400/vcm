package controller;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

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
import json.DoubanUserJson;
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
	    model.addAttribute("batchNewThreadProgressMap", Status.batchNewThreadProgressMap);
	    
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
	
	@RequestMapping(path = "/batch/stop", method = RequestMethod.POST)
	public @ResponseBody SuccessJson stopBatch(HttpServletRequest request) {
	    SuccessJson successJson = new SuccessJson();
	    if (!(boolean) request.getAttribute("isLogin") || 
                (int) request.getAttribute("userId") != 1) {
            return successJson;
        }
	    
	    Status.batchNewThreadFlag = false;
	    
	    successJson.setIsSuccess(true);
	    
	    return successJson;
	}
	
	//批量抓取豆瓣新建
	@RequestMapping(path = "/batch", method = RequestMethod.POST)
	public @ResponseBody SuccessJson batch(HttpServletRequest request, @RequestBody MovieJson movieJson) {
	    SuccessJson successJson = new SuccessJson();
	    if (!(boolean) request.getAttribute("isLogin") || 
	            (int) request.getAttribute("userId") != 1 ||
	            movieJson == null) {
	        return successJson;
	    }
	    
	    new BatchNewThread(movieJson).start();
	    
	    successJson.setIsSuccess(true);
        return successJson;
	}
	
	//随机批量抓取,从数据库中随机得到(unhandle_rating<10)movie
	@RequestMapping(path = "/batch/random", method = RequestMethod.POST)
    public @ResponseBody SuccessJson batchRandom(HttpServletRequest request) {
        SuccessJson successJson = new SuccessJson();
        if (!(boolean) request.getAttribute("isLogin") || 
                (int) request.getAttribute("userId") != 1) {
            return successJson;
        }
        
        new RandomBatchNewThread().start();
        
        successJson.setIsSuccess(true);
        return successJson;
    }
	
	//将doubanUserId的评分导入自己账户下
	@RequestMapping(path = "/import/doubanUsername", method = RequestMethod.POST)
    public @ResponseBody SuccessJson importDoubanUsername(HttpServletRequest request, @RequestBody DoubanUserJson doubanUserJson) {
        SuccessJson successJson = new SuccessJson();
        
        String doubanUsername = doubanUserJson.getDoubanUsername();
        if (doubanUsername == null) return successJson;
        
        User user = new User();
        user.setUserId((Integer) request.getAttribute("userId"));
        System.out.println("userId" + user.getUserId());
        
        User doubanUser = new User();
        doubanUser.setUsername(doubanUsername);
        
        new ImportDoubanUsernameThread(user, doubanUser).start();
        
        successJson.setIsSuccess(true);
        return successJson;
    }
	
	private class ImportDoubanUsernameThread extends NewThread {
	    
	    private User user;
	    private User doubanUser;
	    
	    public ImportDoubanUsernameThread(User user, User doubanUser) {
	        super();
	        this.user = user;
	        this.doubanUser = doubanUser;
	    }
	    
	    @Override
	    public void run() {
	        try {
                
                Status.batchNewThreadCount++;
                Status.batchNewThreadProgressMap.put(threadId.toString(), 0.0F);
                System.out.println("threadId:" + threadId.toString());
                
                Thread.currentThread().sleep(1000*1);  //启动sleep
                
                addRatingAndMovieByDoubanUsername(user, doubanUser);
	        }
	        catch (Exception e) {
//	            e.printStackTrace();
	        }
	        finally {
                Status.batchNewThreadCount--;
                Status.batchNewThreadProgressMap.remove(threadId.toString());
            }
	    }
	    
	}
	
	private class RandomBatchNewThread extends NewThread {
        
        public RandomBatchNewThread() {
            super();
        }
        
        @Override
        public void run() {
            
            try {
                
                Status.batchNewThreadCount++;
                Status.batchNewThreadProgressMap.put(threadId.toString(), 0.0F);
                System.out.println("threadId:" + threadId.toString());
                
                Thread.currentThread().sleep(1000*1);  //启动sleep
                
                while (Status.batchNewThreadFlag) {
                    
                    try {
                    
                        Movie movie = movieService.randomMovieIfUnhandleRatingsLessThanX();
                        
                        addZombieUserRatingAndMovieByDoubanId(movie.getDoubanId());
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        try {
                            Thread.currentThread().sleep(1000 * 30 * Status.batchNewThreadCount);
                        } catch (InterruptedException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                    
                }
                
            }
            catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            finally {
                Status.batchNewThreadCount--;
                Status.batchNewThreadProgressMap.remove(threadId.toString());
            }
        }
        
    }
	
	private class BatchNewThread extends NewThread {
	    
	    private MovieJson movieJson;
	    
	    public BatchNewThread(MovieJson movieJson) {
	        super();
	        this.movieJson = movieJson;
	    }
	    
	    @Override
	    public void run() {
	        
            try {
                
                Status.batchNewThreadCount++;
                Status.batchNewThreadProgressMap.put(threadId.toString(), 0.0F);
                System.out.println("threadId:" + threadId.toString());
                
                Thread.currentThread().sleep(1000*1);  //启动sleep
                
//              userService.addZombieUserRatingAndMovieByDoubanId(movieJson.getDoubanId());
                addZombieUserRatingAndMovieByDoubanId(movieJson.getDoubanId());
            }
            catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            finally {
                Status.batchNewThreadCount--;
                Status.batchNewThreadProgressMap.remove(threadId.toString());
            }
	    }
	    
	}
	
	private abstract class NewThread extends Thread {
	    
	    private static final String str = "qwertyuiopasdfghjklzxcvbnm0123456789";
	    
        protected final StringBuilder threadId = new StringBuilder();
        
        protected NewThread() {
            Random random = new Random();
            for (int i=0; i<4; i++) {
                threadId.append(str.charAt(random.nextInt(str.length())));
            }
        }
        
        
	    
	    protected boolean addZombieUserRatingAndMovieByDoubanId(int doubanId) {
            // TODO Auto-generated method stub
            List<User> userList = DoubanMovieSubjectCollectionsCrawler.crawer(doubanId);
            
            try {
                Thread.currentThread().sleep(((userList.size() + 1) /20) * 1000 * 2 * Status.batchNewThreadCount);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            
            
            Iterator<User> userIterator = userList.iterator();
            
                while (userIterator.hasNext()) {
                    
                    try {
                
                        User user = userIterator.next();
                        
                        if (!Status.batchNewThreadFlag) {
                            return false;
                        }
                    
                        
                        try {
//                            userService.addUser(user);  //validate
                            userMapper.add(user);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            continue;
                        }
                        
                        addRatingAndMovieByDoubanUsername(user, null);
                        
                }   
                finally {
                    Status.batchNewThreadProgressMap.put(threadId.toString(), 
                            Status.batchNewThreadProgressMap.get(threadId.toString()) + 0.5F);
                }
            }
            return false;
	    }
	    
	    protected boolean addRatingAndMovieByDoubanUsername(User user, User doubanUser) {
            
          
          try {
              //展开的 storyline 抓取不到
//              List<Rating> ratingList =  DoubanMovieUserCollectCrawler.crawer(user);
              List<Rating> ratingList = null;
              if (doubanUser == null) {
                  ratingList =  DoubanMovieUserCollectCrawler.crawer(user);
              }
              else {
                  ratingList =  DoubanMovieUserCollectCrawler.crawer(doubanUser);
              }
              
              if(ratingList == null) return false;
              
              try {
                  Thread.currentThread().sleep(((ratingList.size() + 1) /15) * 1000 * 2 * Status.batchNewThreadCount);
              } catch (InterruptedException e1) {
                  // TODO Auto-generated catch block
                  e1.printStackTrace();
              }
              
              for(Rating rating : ratingList) {
                  
                  if (!Status.batchNewThreadFlag) {
                      return false;
                  }
                  
                  
                  try {
                  
                      if (rating.getComment() == null) {
                          rating.setComment("");
                      }
                      rating.setUserId(user.getUserId());
                      
                      Movie movie = new Movie();
                      movie.setDoubanId(rating.getMovieId());
                      
                      Movie movieT = movieMapper.findMovieByDoubanId(movie);
                      
                      if (movieT == null) {
                          
                          try {
                              Thread.currentThread().sleep(2800 * Status.batchNewThreadCount);
                          } catch (InterruptedException e1) {
                              // TODO Auto-generated catch block
                              e1.printStackTrace();
                          }
                          
                          movieT = movieService.addMovieUseCrawlerByDoubanId(movie);
                          
                          if (movieT == null) continue;
                          
                      }
                      // 有问题?
                      rating.setMovieId(movieT.getMovieId());
                      ratingService.changeRating(rating);
                      
                  }
                  catch (Exception e) {
                      e.printStackTrace();
                      try {
                          Thread.currentThread().sleep(1000 * 30 * Status.batchNewThreadCount);
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
          return true;
      }
	}

}
