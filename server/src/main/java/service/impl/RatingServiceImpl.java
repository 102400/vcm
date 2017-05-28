package service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.GenresRatingStats;
import domain.RatingStats;
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
        userMapper.plusOneToUnhandleRatingsAndRatingCountByUserId(user);
        
        return true;
    }

    @Override
    public Rating findRatingByMovieIdAndUserId(Rating rating) {
        // TODO Auto-generated method stub
        return ratingMapper.findRatingByMovieIdAndUserId(rating);
    }

    @Override
    public List<Object> selectAllGenresRatingStatsListByUserId(Rating rating) {
        // TODO Auto-generated method stub
        List<Object> list = new ArrayList<>();
        List<GenresRatingStats> ratingCountList = ratingMapper.selectRatingCountListByUserId(rating);
        List<GenresRatingStats> genresStatsList = ratingMapper.selectGenresStatsListByUserId(rating);
        RatingStats ratingStats = ratingMapper.selectRatingStatsByUserId(rating);
        
        User user = new User();
        user.setUserId(rating.getUserId());
        
//        user = userMapper.findUserByUserId(user);
//        int ratingCount = user.getRatingCount();
        int ratingCount = ratingStats.getRatingCount();
        float avgRating = ratingStats.getAvgRating();
        
        for (GenresRatingStats grs : genresStatsList) {
            grs.setRatio((grs.getCount() + 0.0F) / ratingCount);
            grs.setAvgDifference(grs.getAvgRating() - avgRating);
            grs.setAvgRatio(grs.getAvgRating() / avgRating);
        }
        
        
        list.add(ratingCountList);
        list.add(genresStatsList);
        list.add(ratingStats);
        
        return list;
    }

}
