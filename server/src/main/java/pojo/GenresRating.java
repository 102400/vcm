package pojo;

public class GenresRating {
    
    private Integer genresRatingId;
    private Integer userId;
    private Integer genresId;
    private Integer count;  // genresCount
    private Float ratio;  // genresCount / allCount
    private Float avgRating;
    private Float avgDifference;
    private Float avgRatio;
    
    public Integer getGenresRatingId() {
        return genresRatingId;
    }
    public void setGenresRatingId(Integer genresRatingId) {
        this.genresRatingId = genresRatingId;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getGenresId() {
        return genresId;
    }
    public void setGenresId(Integer genresId) {
        this.genresId = genresId;
    }
    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }
    public Float getRatio() {
        return ratio;
    }
    public void setRatio(Float ratio) {
        this.ratio = ratio;
    }
    public Float getAvgRating() {
        return avgRating;
    }
    public void setAvgRating(Float avgRating) {
        this.avgRating = avgRating;
    }
    public Float getAvgDifference() {
        return avgDifference;
    }
    public void setAvgDifference(Float avgDifference) {
        this.avgDifference = avgDifference;
    }
    public Float getAvgRatio() {
        return avgRatio;
    }
    public void setAvgRatio(Float avgRatio) {
        this.avgRatio = avgRatio;
    }

}
