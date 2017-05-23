package mapper;

import pojo.Rating;

public interface RatingMapper {
    
    int addRating(Rating rating);
    Rating findRatingByMovieIdAndUserId(Rating rating);
    int updateRatingAndComment(Rating rating);

}
