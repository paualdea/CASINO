CREATE DATABASE CASINO;
USE CASINO;

CREATE TABLE IF NOT EXISTS usuarios (
    id INT auto_increment PRIMARY KEY,
    username varchar(50),
    passwd varchar(50)
);

CREATE TABLE IF NOT EXISTS puntos (
    id_usuario int,
    puntos int,
    PRIMARY KEY (id_usuario),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);

CREATE TABLE IF NOT EXISTS log (
    id int auto_increment,
    accion varchar(500),
	marca_tiempo datetime,
    PRIMARY KEY (id)
);

DELIMITER $
CREATE TRIGGER trigger_añadir_log_usuario AFTER INSERT
ON usuarios FOR EACH ROW
BEGIN
INSERT INTO log (accion, marca_tiempo) VALUES (CONCAT('Usuario ',NEW.username,' añadido'), NOW());
END $
DELIMITER ;

DELIMITER $
CREATE TRIGGER trigger_añadir_log_puntos AFTER INSERT
ON puntos FOR EACH ROW
BEGIN
DECLARE usuario varchar(50);
SELECT username INTO usuario FROM usuarios WHERE id = new.id_usuario ;
INSERT INTO log (accion, marca_tiempo) VALUES (CONCAT(NEW.puntos, ' puntos añadidos al usuario ', usuario), NOW());
END $
DELIMITER ;

INSERT INTO usuarios (username, passwd) VALUES ('paualdea', 'aldea2');
INSERT INTO usuarios (username, passwd) VALUES ('mohammadtufail', 'tufail2');

INSERT INTO puntos VALUES (1, 12000);
INSERT INTO puntos VALUES (2, 12000);