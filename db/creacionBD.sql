CREATE TABLE IF NOT EXISTS usuarios (
    id INT PRIMARY KEY,
    usuario VARCHAR(100),
    passwd VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS puntos (
    id INT PRIMARY KEY,
    puntos INT,
    FOREIGN KEY (id) REFERENCES usuarios(id)
);

INSERT INTO usuarios VALUES (1, 'paualdea', 'aldea2');
INSERT INTO puntos VALUES (1, 12000);