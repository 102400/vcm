package service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.GenresRatingStats;
import domain.RatingStats;
import mapper.GenresRatingMapper;
import mapper.MovieMapper;
import mapper.RatingMapper;
import mapper.UserMapper;
import pojo.GenresRating;
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
    
    @Autowired
    private GenresRatingMapper genresRatingMapper;
    
    

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

    @Override
    public boolean selectAndAddGenresRatingListToGenresRatingByUserId(Rating rating) {
        // TODO Auto-generated method stub
        
        RatingStats ratingStats = ratingMapper.selectRatingStatsByUserId(rating);
        List<GenresRating> genresRatingList = ratingMapper.selectGenresRatingListByUserId(rating);
        
        Integer userId = rating.getUserId();
        int ratingCount = ratingStats.getRatingCount();
        float avgRating = ratingStats.getAvgRating();
        
//        for (GenresRating gr : genresRatingList) {
//            gr.setUserId(userId);
//            gr.setRatio((gr.getCount() + 0.0F) / ratingCount);
//            gr.setAvgDifference(gr.getAvgRating() - avgRating);
//            gr.setAvgRatio(gr.getAvgRating() / avgRating);
//        }
        
        Iterator<GenresRating> grIterator = genresRatingList.iterator();
        
        while (grIterator.hasNext()) {
            GenresRating gr = grIterator.next();
            
            gr.setUserId(userId);
            gr.setRatio((gr.getCount() + 0.0F) / ratingCount);
            gr.setAvgDifference(gr.getAvgRating() - avgRating);
            gr.setAvgRatio(gr.getAvgRating() / avgRating);
            
            if (gr.getCount() < 10 || gr.getAvgDifference() < 0) {  //流派评分总数小于10或流派评分小于平均评分的移除不处理
                grIterator.remove();
            }
        }
        if (genresRatingList.size() < 3) return false;  //不足3的不能进行
        
        genresRatingMapper.deleteGenresRatingByUserId(genresRatingList.get(0));
        
        for (GenresRating gr : genresRatingList) {
            genresRatingMapper.addGenresRating(gr);
        }
        
        return true;
    }

}
