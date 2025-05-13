CREATE DATABASE laberinto;
USE laberinto;

CREATE TABLE usuarios (
	id INT PRIMARY KEY AUTO_INCREMENT,
	usuario VARCHAR(25) NOT NULL,
    contraseña VARCHAR(25) NOT NULL
);

INSERT INTO usuarios (usuario, contraseña) VALUES 
  ('admin', 'root'), 
  ('usuario1', 'user123');

CREATE TABLE laberintos (
	id INT PRIMARY KEY AUTO_INCREMENT,
    ancho INT NOT NULL,
    alto INT NOT NULL,
    num_cocodrilos INT NOT NULL,
    daño_cocodrilos INT NOT NULL,
    num_botiquines INT NOT NULL,
    vida_botiquines INT NOT NULL,
    tiempo_pregunta INT NOT NULL,
    daño_pregunta INT NOT NULL,
    num_preguntas INT NOT NULL
);

CREATE TABLE disposiciones (
	id INT PRIMARY KEY AUTO_INCREMENT,
    id_laberinto INT,
    FOREIGN KEY (id_laberinto) REFERENCES laberintos(id)
);

CREATE TABLE preguntas (
	id INT PRIMARY KEY AUTO_INCREMENT,
	pregunta VARCHAR(100) NOT NULL,
	respuesta VARCHAR(40) NOT NULL,
	pista VARCHAR(60) NOT NULL
);

CREATE TABLE partidas(
	id_usuario INT,
    id_laberinto INT,
    id_disposicion INT,
    victoria BOOLEAN,
    vida INT,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
    FOREIGN KEY (id_laberinto) REFERENCES laberintos(id),
	FOREIGN KEY (id_disposicion) REFERENCES disposiciones(id)
);

CREATE TABLE disposiciones_matriz (
	cord_x INT NOT NULL,
    cord_y INT NOT NULL,
    id_disposicion INT NOT NULL,
    -- 1: Cocodrilo, 2: Botiquín, 3: Muro
    elemento ENUM('1', '2', '3'),
	PRIMARY KEY (cord_x, cord_y, id_disposicion),
    FOREIGN KEY (id_disposicion) REFERENCES disposiciones(id)
);
