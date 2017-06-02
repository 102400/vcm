package service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import crawler.DoubanMovieSubjectCrawler;
import domain.RatingsAndUsersStats;
import mapper.GenresMapper;
import mapper.MovieGenresMapper;
import mapper.MovieMapper;
import mapper.RatingMapper;
import pojo.Genres;
import pojo.Movie;
import pojo.MovieGenres;
import pojo.Rating;
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
    
    @Autowired
    private RatingMapper ratingMapper;

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
        List<Object> list = new ArrayList<>(2);
        
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

    @Override
    public Movie randomMovieIfUnhandleRatingsLessThanX() {
        // TODO Auto-generated method stub
        List<Movie> movieList = movieMapper.findMovieListIfUnhandleRatingsLessThanX();
        
        return movieList.size() == 0 ? null : movieList.get(new Random().nextInt(movieList.size()));
    }

    @Override
    public List<Object> searchMovieByImdbIdOrDoubanIdOrNameZh(String q) {
        // TODO Auto-generated method stub
        // [0] : imdbId (精确) , [1] : doubanId (精确) , [2] : nameZh (模糊)
        List<Object> list = new ArrayList<>(3);
        list.add(null);
        list.add(null);
        list.add(null);
        
        Integer imdbId = null;
        Integer doubanId = null;
        
        try {
            imdbId = Integer.valueOf(q.split("tt")[1]);
        }
        catch (Exception e) {}
        
        try {
            doubanId = Integer.valueOf(q);
        }
        catch (NumberFormatException e) {}
        
        
        Movie movie = new Movie();
        movie.setImdbId(imdbId);
        movie.setDoubanId(doubanId);
        movie.setNameZh(q);
        
        if (imdbId != null || "".equals(q.split("tt")[0])) {
            list.add(0, movieMapper.findMovieByImdbId(movie));
        }
        if (doubanId != null) {
            list.add(1, movieMapper.findMovieByDoubanId(movie));
        }
        list.add(2, movieMapper.findMovieListByNameZh(movie));
        
        return list;
    }

    @Override
    public boolean calculationAndUpdateRatingsAndUsers() {
        // TODO Auto-generated method stub
        List<Movie> movieList = movieMapper.findMovieListIfUnhandleRatingsMoreThanX();
        
        for (Movie movie : movieList) {
            Rating rating = new Rating();
            rating.setMovieId(movie.getMovieId());
            
            RatingsAndUsersStats ratingsAndUsersStats = ratingMapper.selectRatingsAndUsersStatsByMovieId(rating);
            movie.setRatings(ratingsAndUsersStats.getRatings());
            movie.setUsers(ratingsAndUsersStats.getUsers());
            
            movieMapper.updateRatingsAndUsersByMovieId(movie);
            
            movieMapper.makeUnhandleRatingsZeroByMovieId(movie);
        }
        
        return true;
    }

}
