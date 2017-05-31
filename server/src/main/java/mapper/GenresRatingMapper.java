package mapper;

import java.util.HashMap;
import java.util.List;

import pojo.GenresRating;

public interface GenresRatingMapper {
    
    void deleteGenresRatingByUserId(GenresRating genresRating);
    int addGenresRating(GenresRating genresRating);
    GenresRating findFirstGenresRating();
    GenresRating findGenresRatingByGenresRatingId(GenresRating genresRating);
    List<GenresRating> findGenresRatingListByUserId(GenresRating genresRating);
//    List<GenresRating> findGenresRatingListByUserIdAndGenresId(GenresRating genresRating, Integer genresA, Integer genresB, Integer genresC);
    List<GenresRating> findGenresRatingListByUserIdAndGenresId(HashMap<String, Object> hashmap);
    GenresRating findLastGenresRating();
    
}
