CREATE TABLE nearest_neighbor
(
    nearest_neighbor_id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    neighbor_id INT NOT NULL,
    create_time DATETIME NOT NULL DEFAULT NOW(),
    PRIMARY KEY(nearest_neighbor_id)
)