package service;

import java.util.List;

import pojo.Rating;

public interface RatingService {
    
    boolean changeRating(Rating rating);
    Rating findRatingByMovieIdAndUserId(Rating rating);
    List<Object> selectAllRatingAndGenresCountListByUserId(Rating rating);

}
