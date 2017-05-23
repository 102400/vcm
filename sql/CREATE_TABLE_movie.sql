CREATE TABLE movie
(
    movie_id INT NOT NULL AUTO_INCREMENT,
    imdb_id INT NOT NULL UNIQUE,  -- "tt" + imdb_id
    douban_id INT NOT NULL UNIQUE,
    ratings FLOAT NOT NULL DEFAULT 0.0,
    users INT NOT NULL DEFAULT 0,
    name_zh VARCHAR(128) NOT NULL,
    name_o VARCHAR(256),  -- 原始name
    storyline VARCHAR(2048) NOT NULL DEFAULT '',
    release_date VARCHAR(512) NOT NULL DEFAULT '?',
    runtime SMALLINT,  -- short
    unhandle_ratings INT NOT NULL DEFAULT 0,  -- 每个用户在此电影评分,此处++,计算总评分后归零
    create_time DATETIME NOT NULL DEFAULT NOW(),
    PRIMARY KEY(movie_id)
)