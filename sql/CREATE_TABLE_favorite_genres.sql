CREATE TABLE favorite_genres
(
    favorite_genres_id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    genres_id INT NOT NULL,
    create_time DATETIME NOT NULL DEFAULT NOW(),
    PRIMARY KEY(favorite_genres_id)
)