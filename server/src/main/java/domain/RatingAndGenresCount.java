package domain;

public class RatingAndGenresCount {
    
    private Integer rating;
    private Integer count;
    private Integer genresId;
    private String genresNameZh;
    
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

}
