CREATE TABLE IF NOT EXISTS usuarios (
	id INT PRIMARY KEY,
    username varchar(50),
    password varchar(50)
);

CREATE TABLE IF NOT EXISTS puntos (
	id_usuario int,
    puntos int,
    PRIMARY KEY (id_usuario),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS registro (
	id int auto_increment,
    id_juego varchar(20),
    apuesta int,
    resultado int,
    PRIMARY KEY (id)
);

INSERT INTO usuarios VALUES (1, 'paualdea', 'aldea2');
INSERT INTO usuarios VALUES (2, 'mohammadtufail', 'tufail2');

INSERT INTO puntos VALUES (1, 12000);
INSERT INTO puntos VALUES (2, 12000);