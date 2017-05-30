package config;

import util.MD5;

public class Config {
    
    public static final String DOUBAN_HOST = "https://movie.douban.com";
    public static final String MOVIE_COVER_PATH = "C:\\vcm\\movie_cover";  //cover使用doubanId作为前缀
    public static final String CONFIG_PATH = "C:/vcm/config";
    
    //ZOMBIE_USER_EMAIL = username + ZOMBIE_USER_EMAIL_SUFFIX
    public static final String ZOMBIE_USER_EMAIL_SUFFIX = "@vcm.com";
    public static final String ZOMBIE_USER_PASSWORD = "pAssW0Rd";
    public static final String ZOMBIE_USER_PASSWORD_MD5 = MD5.code(ZOMBIE_USER_PASSWORD);
    
    public static boolean canGenresRatingHandlerRun = false;
    public static boolean canFavoriteGenresHandlerRun = false;
    public static boolean canNearestNeighborHandlerRun = true;
            
    
    static {
        
    }
    
    private Config() {}

}
