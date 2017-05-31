package service;

import pojo.FavoriteGenres;

public interface FavoriteGenresService {

    FavoriteGenres findLastFavoriteGenres();
    FavoriteGenres findFirstFavoriteGenres();
    int findAndAddNearestNeighbor(FavoriteGenres favoriteGenres);
    
}
