package domain;

public class GenresRatingStats {
    
    private Integer rating;
    private Integer count;
    private Integer genresId;
    private String genresNameZh;
    private Float ratio;
    private Float avgRating;
    private Float avgDifference;
    private Float avgRatio;
    
    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }
    public Integer getGenresId() {
        return genresId;
    }
    public void setGenresId(Integer genresId) {
        this.genresId = genresId;
    }
    public String getGenresNameZh() {
        return genresNameZh;
    }
    public void setGenresNameZh(String genresNameZh) {
        this.genresNameZh = genresNameZh;
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
