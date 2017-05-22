CREATE TABLE genres
(
    genres_id INT NOT NULL AUTO_INCREMENT,
    name_zh VARCHAR(64) NOT NULL,
    create_time DATETIME NOT NULL DEFAULT NOW(),
    PRIMARY KEY(genres_id)
)