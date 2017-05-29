package mapper;

import pojo.GenresRating;

public interface GenresRatingMapper {
    
    void deleteGenresRatingByUserId(GenresRating genresRating);
    int addGenresRating(GenresRating genresRating);
    
}
