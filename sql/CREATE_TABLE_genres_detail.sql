CREATE TABLE genres_detail
(
    genres_detail_id INT NOT NULL AUTO_INCREMENT,
    genres_id INT NOT NULL,  -- genres.genres_id
    name_zh VARCHAR(64) NOT NULL,
    create_time DATETIME NOT NULL DEFAULT NOW(),
    PRIMARY KEY(genres_detail_id)
)