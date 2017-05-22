CREATE TABLE movie_genres
(
    id INT NOT NULL AUTO_INCREMENT,
    genres_id INT NOT NULL,  -- genres.genres_id
    movie_id INT NOT NULL,  -- movie.movie_id
    create_time DATETIME NOT NULL DEFAULT NOW(),
    PRIMARY KEY(id)
)