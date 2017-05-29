CREATE TABLE genres_rating
(
    genres_rating_id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    genres_id INT NOT NULL,
    count INT NOT NULL,
    ratio FLOAT NOT NULL,
    avg_rating FLOAT NOT NULL,
    avg_difference FLOAT NOT NULL,
    avg_ratio FLOAT NOT NULL,
    create_time DATETIME NOT NULL DEFAULT NOW(),
    PRIMARY KEY(genres_rating_id)
);