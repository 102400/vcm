package service;

import pojo.Rating;

public interface RatingService {
    
    boolean changeRating(Rating rating);
    Rating findRatingByMovieIdAndUserId(Rating rating);

}
