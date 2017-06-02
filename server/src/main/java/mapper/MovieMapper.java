package mapper;

import java.util.List;

import pojo.Movie;

public interface MovieMapper {
    
    int add(Movie movie);
    Movie findMovieByDoubanId(Movie movie);
    Movie findMovieByMovieId(Movie movie);
    int plusOneToUnhandleRatingsByMovieId(Movie movie);
    int makeUnhandleRatingsZeroByMovieId(Movie movie);
    List<Movie> findMovieListIfUnhandleRatingsLessThanX();
    Movie findMovieByImdbId(Movie movie);
    List<Movie> findMovieListByNameZh(Movie movie);
    List<Movie> findMovieListIfUnhandleRatingsMoreThanX();
    void updateRatingsAndUsersByMovieId(Movie movie);

}
