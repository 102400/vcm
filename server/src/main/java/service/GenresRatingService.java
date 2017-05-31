package service;

import pojo.GenresRating;

public interface GenresRatingService {
    
    GenresRating findFirstGenresRating();
    int findAndAddFavoriteGenresByGenresRatingId(GenresRating genresRating);
    GenresRating findLastGenresRating();

}
