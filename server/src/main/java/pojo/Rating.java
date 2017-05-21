package pojo;

public class Rating {
    
    private Integer ratingId;
    private Integer movieId;
    private Integer userId;
    private Integer rating;
    private String comment;
    
    public Integer getRatingId() {
        return ratingId;
    }
    public void setRatingId(Integer ratingId) {
        this.ratingId = ratingId;
    }
    public Integer getMovieId() {
        return movieId;
    }
    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

}
