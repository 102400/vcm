package pojo;

public class Movie {
    
    private Integer movieId;
    private Integer imdbId;
    private Integer doubanId;
    private Float ratings;
    private Integer users;
    private String nameZh;
    private String nameO;
    private String storyline;
    private String releaseDate;
    private Short runtime;
    private Integer unhandleRatings;
    
    public Integer getMovieId() {
        return movieId;
    }
    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }
    public Integer getImdbId() {
        return imdbId;
    }
    public void setImdbId(Integer imdbId) {
        this.imdbId = imdbId;
    }
    public Integer getDoubanId() {
        return doubanId;
    }
    public void setDoubanId(Integer doubanId) {
        this.doubanId = doubanId;
    }
    public Float getRatings() {
        return ratings;
    }
    public void setRatings(Float ratings) {
        this.ratings = ratings;
    }
    public Integer getUsers() {
        return users;
    }
    public void setUsers(Integer users) {
        this.users = users;
    }
    public String getNameZh() {
        return nameZh;
    }
    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }
    public String getNameO() {
        return nameO;
    }
    public void setNameO(String nameO) {
        this.nameO = nameO;
    }
    public String getStoryline() {
        return storyline;
    }
    public void setStoryline(String storyline) {
        this.storyline = storyline;
    }
    public String getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
    public Short getRuntime() {
        return runtime;
    }
    public void setRuntime(Short runtime) {
        this.runtime = runtime;
    }
    public Integer getUnhandleRatings() {
        return unhandleRatings;
    }
    public void setUnhandleRatings(Integer unhandleRatings) {
        this.unhandleRatings = unhandleRatings;
    }
    
    
}
