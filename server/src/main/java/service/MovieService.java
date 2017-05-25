package service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import pojo.Movie;

public interface MovieService {
    
    Movie addMovieUseCrawlerByDoubanId(Movie movie);
    List<Object> findMovieAndGenresListByDoubanId(Movie movie);
    Movie randomMovieIfUnhandleRatingsLessThanX();

}
