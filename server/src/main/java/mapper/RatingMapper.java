package mapper;

import java.util.HashMap;
import java.util.List;

import domain.GenresRatingStats;
import domain.GoodMovie;
import domain.RatingStats;
import pojo.GenresRating;
import pojo.Rating;

public interface RatingMapper {
    
    int addRating(Rating rating);
    Rating findRatingByMovieIdAndUserId(Rating rating);
    int updateRatingAndComment(Rating rating);
    List<GenresRatingStats> selectRatingCountListByUserId(Rating rating);
    List<GenresRatingStats> selectGenresStatsListByUserId(Rating rating);
    RatingStats selectRatingStatsByUserId(Rating rating);
    List<GenresRating> selectGenresRatingListByUserId(Rating rating);
    List<GoodMovie> findGoodMovieByUserIdAndGenres(HashMap<String, Object> hashmap);

}
