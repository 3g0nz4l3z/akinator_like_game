USE db_genio;

INSERT INTO topico VALUES('animal') ON DUPLICATE KEY UPDATE id_topico = id_topico;

INSERT INTO respuesta(id_respuesta, id_topico, ranking) VALUES ('Gato', 'animal', 1) ON DUPLICATE KEY UPDATE id_respuesta = id_respuesta, id_topico = id_topico, ranking = ranking;
INSERT INTO respuesta(id_respuesta, id_topico, ranking) VALUES ('Perro', 'animal', 1) ON DUPLICATE KEY UPDATE id_respuesta = id_respuesta, id_topico = id_topico, ranking = ranking;
INSERT INTO respuesta(id_respuesta, id_topico, ranking) VALUES ('Koi', 'animal', 1) ON DUPLICATE KEY UPDATE id_respuesta = id_respuesta, id_topico = id_topico, ranking = ranking;
INSERT INTO respuesta(id_respuesta, id_topico, ranking) VALUES ('Nandu', 'animal', 1) ON DUPLICATE KEY UPDATE id_respuesta = id_respuesta, id_topico = id_topico, ranking = ranking;

INSERT INTO caracteristica(id_caracteristica, caracteristica, valor, pregunta, peso) VALUES('Gato', 'piel', 'peludo', 'Es un animal %s?', 1) ON DUPLICATE KEY UPDATE id_caracteristica = id_caracteristica, caracteristica = caracteristica, valor = valor, pregunta = pregunta, peso = peso;
INSERT INTO caracteristica(id_caracteristica, caracteristica, valor, pregunta, peso) VALUES('Perro', 'piel', 'peludo', 'Es un animal %s?', 1) ON DUPLICATE KEY UPDATE id_caracteristica = id_caracteristica, caracteristica = caracteristica, valor = valor, pregunta = pregunta, peso = peso;
INSERT INTO caracteristica(id_caracteristica, caracteristica, valor, pregunta, peso) VALUES('Koi', 'piel', 'escamoso', 'Es un animal %s?', 1) ON DUPLICATE KEY UPDATE id_caracteristica = id_caracteristica, caracteristica = caracteristica, valor = valor, pregunta = pregunta, peso = peso;
INSERT INTO caracteristica(id_caracteristica, caracteristica, valor, pregunta, peso) VALUES('Nandu', 'piel', 'emplumado', 'Es un animal %s?', 1) ON DUPLICATE KEY UPDATE id_caracteristica = id_caracteristica, caracteristica = caracteristica, valor = valor, pregunta = pregunta, peso = peso;

INSERT INTO caracteristica(id_caracteristica, caracteristica, valor, pregunta, peso) VALUES('Gato', 'patas', '4', 'Tiene %s patas?', 1) ON DUPLICATE KEY UPDATE id_caracteristica = id_caracteristica, caracteristica = caracteristica, valor = valor, pregunta = pregunta, peso = peso;
INSERT INTO caracteristica(id_caracteristica, caracteristica, valor, pregunta, peso) VALUES('Perro', 'patas', '4', 'Camina en %s patas?', 1) ON DUPLICATE KEY UPDATE id_caracteristica = id_caracteristica, caracteristica = caracteristica, valor = valor, pregunta = pregunta, peso = peso;
INSERT INTO caracteristica(id_caracteristica, caracteristica, valor, pregunta, peso) VALUES('Koi', 'patas', 'aletas', 'Es un animal con %s?', 1) ON DUPLICATE KEY UPDATE id_caracteristica = id_caracteristica, caracteristica = caracteristica, valor = valor, pregunta = pregunta, peso = peso;
INSERT INTO caracteristica(id_caracteristica, caracteristica, valor, pregunta, peso) VALUES('Nandu', 'patas', '2', 'Camina en %s patas?', 1) ON DUPLICATE KEY UPDATE id_caracteristica = id_caracteristica, caracteristica = caracteristica, valor = valor, pregunta = pregunta, peso = peso;

INSERT INTO caracteristica(id_caracteristica, caracteristica, valor, pregunta, peso) VALUES('Gato', 'mascota', 'si', 'Puede ser una mascota?', 1) ON DUPLICATE KEY UPDATE id_caracteristica = id_caracteristica, caracteristica = caracteristica, valor = valor, pregunta = pregunta, peso = peso;
INSERT INTO caracteristica(id_caracteristica, caracteristica, valor, pregunta, peso) VALUES('Perro', 'mascota', 'si', 'Puede ser una mascota?', 1) ON DUPLICATE KEY UPDATE id_caracteristica = id_caracteristica, caracteristica = caracteristica, valor = valor, pregunta = pregunta, peso = peso;
INSERT INTO caracteristica(id_caracteristica, caracteristica, valor, pregunta, peso) VALUES('Koi', 'mascota', 'si', 'Puede ser una mascota?', 1) ON DUPLICATE KEY UPDATE id_caracteristica = id_caracteristica, caracteristica = caracteristica, valor = valor, pregunta = pregunta, peso = peso;
INSERT INTO caracteristica(id_caracteristica, caracteristica, valor, pregunta, peso) VALUES('Nandu', 'mascota', 'no', 'Puede ser una mascota?', 1) ON DUPLICATE KEY UPDATE id_caracteristica = id_caracteristica, caracteristica = caracteristica, valor = valor, pregunta = pregunta, peso = peso;