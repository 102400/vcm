package handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import config.Config;

@Component
public class Handler {
    
    public static final boolean RUN;
    
    static {
        RUN = true;
    }
    
    private static GenresRatingHandler genresRatingHandler;
    
    private static FavoriteGenresHandler favoriteGenresHandler;
    
    private static NearestNeighborHandler nearestNeighborHandler;

    @Autowired
    public void setGenresRatingHandler(GenresRatingHandler genresRatingHandler) {
        Handler.genresRatingHandler = genresRatingHandler;
        if (Config.canGenresRatingHandlerRun) {
            new Thread(genresRatingHandler).start();
            System.out.println("GenresRatingHandler start!!!");
        }
    }

    @Autowired
    public void setFavoriteGenresHandler(FavoriteGenresHandler favoriteGenresHandler) {
        Handler.favoriteGenresHandler = favoriteGenresHandler;
        if (Config.canFavoriteGenresHandlerRun) {
            new Thread(favoriteGenresHandler).start();
            System.out.println("FavoriteGenresHandler start!!!");
        }
    }

    @Autowired
    public void setNearestNeighborHandler(NearestNeighborHandler nearestNeighborHandler) {
        Handler.nearestNeighborHandler = nearestNeighborHandler;
        if (Config.canNearestNeighborHandlerRun) {
            new Thread(nearestNeighborHandler).start();
            System.out.println("NearestNeighborHandler start!!!");
        }
    }
    
    
    
}