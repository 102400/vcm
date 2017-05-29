package handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Handler {
    
    public static final boolean RUN;
    
    static {
        RUN = true;
    }
    
    private static GenresRatingHandler genresRatingHandler;

    @Autowired
    public void setGenresRatingHandler(GenresRatingHandler genresRatingHandler) {
        Handler.genresRatingHandler = genresRatingHandler;
        new Thread(genresRatingHandler).start();
        System.out.println("GenresRatingHandler start!!!");
    }
    
}