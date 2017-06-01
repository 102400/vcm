package domain;

public class GoodMovie {
    
    private Integer movieId;
    private Integer rating;
    private Integer genresId;
    
    public Integer getMovieId() {
        return movieId;
    }
    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }
    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public Integer getGenresId() {
        return genresId;
    }
    public void setGenresId(Integer genresId) {
        this.genresId = genresId;
    }
    
}
