package mapper;

import java.util.List;

import domain.RatingAndGenresCount;
import pojo.Rating;

public interface RatingMapper {
    
    int addRating(Rating rating);
    Rating findRatingByMovieIdAndUserId(Rating rating);
    int updateRatingAndComment(Rating rating);
    List<RatingAndGenresCount> selectRatingCountListByUserId(Rating rating);
    List<RatingAndGenresCount> selectGenresCountListByUserId(Rating rating);

}
