package service.impl;

import java.util.ArrayList;
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
        
        List<Object> list = DoubanMovieSubjectCrawler.crawer(movie.getDoubanId());
        
        if (list == null || list.get(0) == null || list.get(1) == null) return null;
        
        movie = (Movie) list.get(0);
        
        // 展开的storyline抓不到，暂时这么处理
        if (movie.getStoryline() == null) {
            movie.setStoryline("");
//            return null;
        }
        if (movie.getRuntime() == null) {
            return null;  //不是movie，是tv或其他
        }
        
        List<Genres> genresList = (List<Genres>) list.get(1);
        
        System.out.println("*****addMovieUseCrawlerByDoubanId");
        System.out.println(movie.getNameZh());
        System.out.println(movie.getDoubanId());
        
        
        try {
            movieMapper.add(movie);
            for (int i=0; i<genresList.size(); i++) {  //电影分类处理
                Genres genres = genresList.get(i);
                System.out.println(genres.getNameZh());
                Genres fGenres = genresMapper.findGenresByNameZh(genres);
                if (fGenres == null) {  //新建genresDetail
                    genresMapper.addGenres(genres);  //插入后genresId自动赋值
                    fGenres = genres;
                }
                MovieGenres movieGenres = new MovieGenres();
                movieGenres.setMovieId(movie.getMovieId());
                movieGenres.setGenresId(fGenres.getGenresId());
                
                movieGenresMapper.addMovieGenres(movieGenres);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        
        return movie;
    }

    @Override
    public List<Object> findMovieAndGenresListByDoubanId(Movie movie) {
        // TODO Auto-generated method stub
        List<Object> list = new ArrayList<>();
        
        movie = movieMapper.findMovieByDoubanId(movie);
        if (movie == null) return null;
        
        MovieGenres movieGenres = new MovieGenres();
        movieGenres.setMovieId(movie.getMovieId());
        
        List<Genres> genresList = new ArrayList<>();
        
        List<MovieGenres> movieGenresList = movieGenresMapper.findMovieGenresByMovieId(movieGenres);
        if (movieGenresList != null) {
            for (int i=0; i<movieGenresList.size(); i++) {
                Genres genres = new Genres();
                genres.setGenresId(movieGenresList.get(i).getGenresId());
                genres = genresMapper.findGenresByGenresId(genres);
                genresList.add(genres);
            }
        }
        else {
            return null;  //movie没有genres
        }
        
        list.add(movie);
        list.add(genresList);
        
        return list;
    }

}
