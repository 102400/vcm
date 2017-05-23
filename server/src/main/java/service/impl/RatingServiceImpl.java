package service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mapper.MovieMapper;
import mapper.RatingMapper;
import mapper.UserMapper;
import pojo.Movie;
import pojo.Rating;
import pojo.User;
import service.RatingService;

@Service
@Transactional
public class RatingServiceImpl implements RatingService {
    
    @Autowired
    private RatingMapper ratingMapper;
    
    @Autowired
    private MovieMapper movieMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    

    @Override
    public boolean changeRating(Rating rating) {
        // TODO Auto-generated method stub
        Rating ratingT = ratingMapper.findRatingByMovieIdAndUserId(rating);
        if (ratingT == null) {  //插入
            ratingMapper.addRating(rating);
        }
        else {  //修改
            ratingMapper.updateRatingAndComment(rating);
        }
        
        Movie movie = new Movie();
        movie.setMovieId(rating.getMovieId());
        movieMapper.plusOneToUnhandleRatingsByMovieId(movie);
        
        User user = new User();
        user.setUserId(rating.getUserId());
        userMapper.plusOneToUnhandleRatingsByUserId(user);
        
        return true;
    }

    @Override
    public Rating findRatingByMovieIdAndUserId(Rating rating) {
        // TODO Auto-generated method stub
        return ratingMapper.findRatingByMovieIdAndUserId(rating);
    }

}
