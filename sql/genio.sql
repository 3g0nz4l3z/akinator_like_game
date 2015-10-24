DROP DATABASE IF EXISTS db_genio;

CREATE DATABASE db_genio CHARACTER SET utf8;

USE db_genio;

CREATE TABLE topico
(
	id_topico VARCHAR(50),
	PRIMARY KEY(id_topico)
) CHARACTER SET 'utf8';

CREATE TABLE respuesta
(
	id_respuesta VARCHAR(50),
	id_topico VARCHAR(50),
	ranking INT,
	PRIMARY KEY(id_respuesta),
	FOREIGN KEY(id_topico) references topico(id_topico)
) CHARACTER SET 'utf8';

CREATE TABLE caracteristica
(
	id INT NOT NULL AUTO_INCREMENT,
	id_caracteristica VARCHAR(50),
	caracteristica VARCHAR(50),
	valor VARCHAR(50),
	pregunta VARCHAR(50),
	peso INT,
	PRIMARY KEY(id),
	FOREIGN KEY(id_caracteristica) references respuesta(id_respuesta)
) CHARACTER SET 'utf8';

CREATE USER 'genio_user'@'*' IDENTIFIED BY 'genio_user';
GRANT SELECT, INSERT, UPDATE ON db_genio . * TO 'genio_user'@'*';
FLUSH PRIVILEGES;