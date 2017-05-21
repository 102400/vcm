CREATE TABLE movie
(
    movie_id INT NOT NULL AUTO_INCREMENT,
    imdb_id INT NOT NULL,  -- "tt" + imdb_id
    douban_id INT NOT NULL,
    ratings FLOAT NOT NULL DEFAULT 0.0,
    users INT NOT NULL DEFAULT 0,
    name_zh VARCHAR(128) NOT NULL,
    name_o VARCHAR(256),  -- 原始name
    storyline VARCHAR(2048) NOT NULL DEFAULT '',
    release_date CHAR(10) NOT NULL DEFAULT '?',
    runtime SMALLINT,  -- short
    create_time DATETIME NOT NULL DEFAULT NOW(),
    PRIMARY KEY(movie_id)
)