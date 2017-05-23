package service;

import java.util.List;

import pojo.Movie;

public interface MovieService {
    
    Movie addMovieUseCrawlerByDoubanId(Movie movie);
    List<Object> findMovieAndGenresListByDoubanId(Movie movie);

}
