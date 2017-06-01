package service;

import java.util.List;

import pojo.Movie;
import pojo.NearestNeighbor;

public interface NearestNeighborService {
    
    List<Movie> findNearestNeighborGoodMovieListByUserId(NearestNeighbor nearestNeighbor);
    List<NearestNeighbor> findNearestNeighborListByUserId(NearestNeighbor nearestNeighbor);

}
