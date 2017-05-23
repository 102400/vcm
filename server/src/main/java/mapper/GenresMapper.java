package mapper;

import pojo.Genres;

public interface GenresMapper {
    
    int addGenres(Genres genres);
    Genres findGenresByNameZh(Genres genres);
    Genres findGenresByGenresId(Genres genres);

}
