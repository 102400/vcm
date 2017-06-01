package service;

import java.util.List;

import pojo.FavoriteGenres;
import pojo.Genres;

public interface FavoriteGenresService {

    FavoriteGenres findLastFavoriteGenres();
    FavoriteGenres findFirstFavoriteGenres();
    int findAndAddNearestNeighbor(FavoriteGenres favoriteGenres);
    List<Genres> findGenresListByUserId(FavoriteGenres favoriteGenres);
    
}
