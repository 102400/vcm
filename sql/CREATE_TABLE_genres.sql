CREATE TABLE genres
(
    genres_id INT NOT NULL AUTO_INCREMENT,
    movie_id INT NOT NULL,  -- movie.movie_id
    create_time DATETIME NOT NULL DEFAULT NOW(),
    PRIMARY KEY(genres_id)
)