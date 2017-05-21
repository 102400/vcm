CREATE TABLE rating
(
    rating_id INT NOT NULL AUTO_INCREMENT,
    movie_id INT NOT NULL,  -- movie.movie_id
    user_id INT NOT NULL,  -- user.user_id
    rating TINYINT NOT NULL,
    comment VARCHAR(256) NOT NULL DEFAULT '',
    create_time DATETIME NOT NULL DEFAULT NOW(),
    PRIMARY KEY(rating_id)
)