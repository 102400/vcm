package mapper;

import java.util.HashMap;
import java.util.List;

import pojo.FavoriteGenres;
import pojo.Genres;

public interface FavoriteGenresMapper {
    
    int addFavoriteGenres(FavoriteGenres favoriteGenres);
    void deleteFavoriteGenresByUserId(FavoriteGenres favoriteGenres);
    FavoriteGenres findLastFavoriteGenres();
    FavoriteGenres findFirstFavoriteGenres();
    FavoriteGenres findFavoriteGenresByFavoriteGenresId(FavoriteGenres favoriteGenres);
    List<FavoriteGenres> findFavoriteGenresListByUserId(FavoriteGenres favoriteGenres);
//    List<FavoriteGenres> findTheSameFavoriteGenres(Integer genresA, Integer genresB, Integer genresC);
    List<FavoriteGenres> findTheSameFavoriteGenres(HashMap<String, Object> hashmap);
    List<Genres> findGenresListByUserId(FavoriteGenres favoriteGenres);

}
