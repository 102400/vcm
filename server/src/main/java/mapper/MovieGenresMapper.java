package mapper;

import java.util.List;

import pojo.MovieGenres;

public interface MovieGenresMapper {
    
    int addMovieGenres(MovieGenres movieGenres);
    List<MovieGenres> findMovieGenresByMovieId(MovieGenres movieGenres);

}
