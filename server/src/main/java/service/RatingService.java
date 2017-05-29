package service;

import java.util.List;

import pojo.GenresRating;
import pojo.Rating;

public interface RatingService {
    
    boolean changeRating(Rating rating);
    Rating findRatingByMovieIdAndUserId(Rating rating);
    List<Object> selectAllGenresRatingStatsListByUserId(Rating rating);
    boolean selectAndAddGenresRatingListToGenresRatingByUserId(Rating rating);

}
