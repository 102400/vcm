package service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.GoodMovie;
import mapper.FavoriteGenresMapper;
import mapper.MovieMapper;
import mapper.NearestNeighborMapper;
import mapper.RatingMapper;
import pojo.FavoriteGenres;
import pojo.Movie;
import pojo.NearestNeighbor;
import service.NearestNeighborService;

@Service
@Transactional
public class NearestNeighborServiceImpl implements NearestNeighborService {
    
    @Autowired
    private NearestNeighborMapper nearestNeighborMapper;
    
    @Autowired
    private FavoriteGenresMapper favoriteGenresMapper;
    
    @Autowired
    private RatingMapper ratingMapper;
    
    @Autowired
    private MovieMapper movieMapper;

    @Override
    public List<Movie> findNearestNeighborGoodMovieListByUserId(NearestNeighbor nearestNeighbor) {
        // TODO Auto-generated method stub
        List<Movie> movieList = new ArrayList<>(100);
        
        List<NearestNeighbor> nearestNeighborList = nearestNeighborMapper.findNearestNeighborListByUserId(nearestNeighbor);
        
        FavoriteGenres favoriteGenres = new FavoriteGenres();
        favoriteGenres.setUserId(nearestNeighbor.getUserId());
        List<FavoriteGenres> favoriteGenresList = favoriteGenresMapper.findFavoriteGenresListByUserId(favoriteGenres);
        Integer genresA = favoriteGenresList.get(0).getGenresId();
        Integer genresB = favoriteGenresList.get(1).getGenresId();
        Integer genresC = favoriteGenresList.get(2).getGenresId();
        
        // 总共最多推荐100部
        //  movieId, count #流派相同叠加
        Map<Integer, Integer> movieStatsMap = new HashMap<>();
//        Map<Integer, Integer> movieStatsMap = new TreeMap<>();
        
        
        float totalInverseRatio = 0F;
        for (NearestNeighbor nn : nearestNeighborList) {
            float inverseRatio = 1 / nn.getDistance();
            totalInverseRatio = totalInverseRatio + inverseRatio;
            
            HashMap<String, Object> hashmap = new HashMap<>();
            hashmap.put("genresA", genresA);
            hashmap.put("genresB", genresB);
            hashmap.put("genresC", genresC);
            hashmap.put("nearestNeighbor", nn);
            
            List<GoodMovie> goodMovieList = ratingMapper.findGoodMovieByUserIdAndGenres(hashmap);
            for (GoodMovie goodMovie : goodMovieList) {
                Integer movieId = goodMovie.getMovieId();
//                System.out.println(goodMovie.getMovieId() + ":" + goodMovie.getGenresId());
                if (movieStatsMap.containsKey(movieId)) {
                    movieStatsMap.put(movieId, movieStatsMap.get(movieId) + 1);
                }
                else {
                    movieStatsMap.put(movieId, 1);
                }
            }
        }
        
//        for (NearestNeighbor nn : nearestNeighborList) {
//            int maxMovie = (int) (100 * nn.getDistance() / totalInverseRatio);
//            
//        }
        List<Map.Entry<Integer,Integer>> movieStatsList = new ArrayList<>(movieStatsMap.entrySet());
        
        Collections.sort(movieStatsList, new Comparator<Map.Entry<Integer,Integer>>() {      
            public int compare(Map.Entry<Integer,Integer> o1, Map.Entry<Integer,Integer> o2) {      
                return (o2.getValue() - o1.getValue());      
            }      
        });
        
        int count = 0;
        for (Integer key : movieStatsMap.keySet()) {
//            System.out.println(key + "->" + movieStatsMap.get(key));
            if (count == 100) break;
            Movie movie = new Movie();
            movie.setMovieId(key);
            
            movie = movieMapper.findMovieByMovieId(movie);
            
            movieList.add(movie);
            count++;
        }
        
        
        return movieList;
    }

    @Override
    public List<NearestNeighbor> findNearestNeighborListByUserId(NearestNeighbor nearestNeighbor) {
        // TODO Auto-generated method stub
        return nearestNeighborMapper.findNearestNeighborListByUserId(nearestNeighbor);
    }

}
