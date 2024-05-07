CREATE DATABASE IF NOT EXISTS CASINO;
USE CASINO;

CREATE TABLE IF NOT EXISTS usuarios (
	id INT AUTO_INCREMENT,
    username varchar(50),
    password varchar(50),
    PRIMARY KEY (id)
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

INSERT INTO usuarios (username, password) VALUES ('paualdea', 'aldea2');
INSERT INTO usuarios (username, password) VALUES ('mohammadtufail', 'tufail2');

INSERT INTO puntos VALUES (1, 12000);
INSERT INTO puntos VALUES (2, 12000);