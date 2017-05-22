package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import crawler.DoubanMovieSubjectCrawler;
import mapper.GenresMapper;
import mapper.MovieGenresMapper;
import mapper.MovieMapper;
import pojo.Genres;
import pojo.Movie;
import pojo.MovieGenres;
import service.MovieService;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {
    
    @Autowired
    private MovieMapper movieMapper;
    
    @Autowired
    private MovieGenresMapper movieGenresMapper;
    
    @Autowired
    private GenresMapper genresMapper;

    @Override
    public Movie addMovieUseCrawlerByDoubanId(Movie movie) {
        // TODO Auto-generated method stub
        
        List list = DoubanMovieSubjectCrawler.crawer(movie.getDoubanId());
        
        if (list == null || list.get(0) == null || list.get(1) == null) return null;
        
        movie = (Movie) list.get(0);
        List<Genres> genresList = (List<Genres>) list.get(1);
        
        try {
            movieMapper.add(movie);
            for (int i=0; i<genresList.size(); i++) {  //电影分类处理
                Genres genres = genresList.get(i);
                System.out.println(genres.getNameZh());
                Genres fGenres = genresMapper.findGenresByNameZh(genres);
                if (fGenres == null) {  //新建genresDetail
                    genresMapper.addGenres(genres);  //插入后genresId自动赋值
                }
                MovieGenres movieGenres = new MovieGenres();
                movieGenres.setMovieId(movie.getMovieId());
                movieGenres.setGenresId(genres.getGenresId());
                
                movieGenresMapper.addMovieGenres(movieGenres);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        
        return movie;
    }

}
