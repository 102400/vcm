package mapper;

import java.util.List;

import domain.GenresRatingStats;
import domain.RatingStats;
import pojo.Rating;

public interface RatingMapper {
    
    int addRating(Rating rating);
    Rating findRatingByMovieIdAndUserId(Rating rating);
    int updateRatingAndComment(Rating rating);
    List<GenresRatingStats> selectRatingCountListByUserId(Rating rating);
    List<GenresRatingStats> selectGenresStatsListByUserId(Rating rating);
    RatingStats selectRatingStatsByUserId(Rating rating);

}
