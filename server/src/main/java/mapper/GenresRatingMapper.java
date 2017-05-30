package mapper;

import java.util.List;

import pojo.GenresRating;

public interface GenresRatingMapper {
    
    void deleteGenresRatingByUserId(GenresRating genresRating);
    int addGenresRating(GenresRating genresRating);
    GenresRating findFirstGenresRating();
    GenresRating findGenresRatingByGenresRatingId(GenresRating genresRating);
    List<GenresRating> findGenresRatingListByUserId(GenresRating genresRating);
    
}
