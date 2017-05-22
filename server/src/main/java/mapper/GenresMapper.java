package mapper;

import pojo.Genres;

public interface GenresMapper {
    
    Genres findGenresByNameZh(Genres genres);
    int addGenres(Genres genres);

}
