package service.impl;

import java.util.List;

import javax.validation.Validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import crawler.DoubanMovieSubjectCollectionsCrawler;
import crawler.DoubanMovieUserCollectCrawler;
import mapper.MovieMapper;
import mapper.UserMapper;
import pojo.Movie;
import pojo.Rating;
import pojo.User;
import service.MovieService;
import service.RatingService;
import service.UserService;

@Service
//@Validated  //告诉MethodValidationPostProcessor此Bean需要开启方法级别验证支持
@Transactional
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
//    public boolean addUser(@Valid User user) {
    public boolean addUser(User userF) {
        // TODO Auto-generated method stub
        User user = userF;
        if (Validation.buildDefaultValidatorFactory().getValidator()
                .validate(user).size() != 0) {
            return false;
        }
        
        userMapper.add(user);
//        System.out.println("userId:" + user.getUserId());  //自增主键
        
        return true;
    }

    @Override
    public User findUserByUsernameOrEmail(User user) {
        // TODO Auto-generated method stub
        User u = null;
        if (user.getEmail()!=null && user.getEmail()!="") {
            u = userMapper.findUserByEmail(user);
        }
        else if (user.getUsername()!=null && user.getUsername()!="") {
            u = userMapper.findUserByUsername(user);
        }
        return u;
    }
    
//    @Autowired
//    private MovieService movieService;
//    
//    @Autowired
//    private MovieMapper movieMapper;
//    
//    @Autowired
//    private RatingService ratingService;

//    @Override
//    public boolean addZombieUserRatingAndMovieByDoubanId(int doubanId) {
//        // TODO Auto-generated method stub
//        List<User> userList = DoubanMovieSubjectCollectionsCrawler.crawer(doubanId);
//        for (User user : userList) {
//            userMapper.add(user);
//            
//            try {
//                List<Rating> ratingList =  DoubanMovieUserCollectCrawler.crawer(user);
//                if(ratingList == null) continue;
//                for(Rating rating : ratingList) {
//                    
//                    try {
//                    
//                        if (rating.getComment() == null) {
//                            rating.setComment("");
//                        }
//                        rating.setUserId(user.getUserId());
//                        
//                        Movie movie = new Movie();
//                        movie.setDoubanId(rating.getMovieId());
//                        
//                        if(movieMapper.findMovieByDoubanId(movie) == null ) {
//                            
//                            movie = movieService.addMovieUseCrawlerByDoubanId(movie);
//                            
//                        }
//                        // 有问题
//                        rating.setMovieId(movie.getMovieId());
//                        ratingService.changeRating(rating);
//                        
//                    }
//                    catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        
//        
//        return false;
//    }

}