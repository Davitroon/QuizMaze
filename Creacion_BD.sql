DROP DATABASE IF EXISTS laberinto25;

CREATE DATABASE laberinto25;
USE laberinto25;

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
	pista VARCHAR(150) NOT NULL
);

INSERT INTO preguntas (pregunta, respuesta, pista) VALUES
('¿Qué gas respiramos para vivir?', 'Oxígeno', 'No se ve ni se huele, pero sin él no duraríamos ni unos minutos despiertos.'),
('¿Qué instrumento tiene teclas blancas y negras?', 'Piano', 'Sus teclas blancas y negras bailan bajo las manos del artista.'),
('¿Cuál es el océano más grande del mundo?', 'Océano Pacífico', 'Su nombre promete paz, pero puede ocultar grandes tormentas.'),
('¿Qué animal pone huevos y tiene pico, aunque no sea un ave?', 'Ornitorrinco', 'Animal raro de Australia, parece una mezcla de varios.'),
('¿Cuál es el idioma más hablado del mundo por número de hablantes nativos?', 'Chino mandarín', 'Lo hablan cientos de millones; cada palabra puede tener varios tonos.'),
('¿Cuál es el continente más pequeño?', 'Oceanía', 'Rodeado de agua, hogar de canguros, kiwis y cultura aborigen.'),
('¿Qué órgano del cuerpo humano bombea sangre?', 'Corazón', 'Late sin que lo pensemos; si se detiene, todo se detiene.'),
('¿En qué país se inventó el papel?', 'China', 'De allí vinieron la pólvora, la seda y también el papel.'),
('¿Cuál es la capital de Japón?', 'Tokio', 'Ciudad de neones y templos con tradición milenaria.'),
('¿Cuántos colores tiene el arcoíris?', '7', 'El sol pinta siete tonos en el cielo tras la lluvia.'),
('¿Qué animal es conocido como el "rey de la selva"?', 'León', 'Su melena lo distingue y su rugido impone respeto.'),
('¿Qué continente tiene el desierto más grande del mundo?', 'África', 'Un vasto mar de arena donde el sol se siente con furia.'),
('¿En qué año llegó el hombre a la luna?', '1969', 'Un pequeño paso para el hombre, un gran salto para la humanidad.'),
('¿Cuál es el océano más pequeño?', 'Océano Ártico', 'Pequeño y cubierto de hielo la mayor parte del año.'),
('¿Qué inventó Thomas Edison?', 'La bombilla', 'Pasamos de la oscuridad a la luz con su invento.'),
('¿Quién pintó la Mona Lisa?', 'Leonardo da Vinci', 'Su sonrisa enigmática fue pintada por un genio renacentista.'),
('¿Qué instrumento musical tiene 88 teclas?', 'Piano', 'Tiene 88 teclas que crean melodías inolvidables.'),
('¿En qué país se encuentra el Machu Picchu?', 'Perú', 'Allí están las famosas ruinas incas en lo alto de los Andes.'),
('¿Cuál es el nombre del río más largo de África?', 'El Nilo', 'Este río fue el eje de civilizaciones antiguas y cruza varios países.'),
('¿Quién fue el primer presidente de los Estados Unidos?', 'George Washington', 'Tuvo una estatua y sentó las bases de una nación.'),
('¿Qué día de la semana sigue al lunes?', 'Martes', 'Es el segundo día de la semana.'),
('¿En qué continente se encuentran los Andes?', 'América del Sur', 'Cordillera que cruza varios países del sur del continente americano.'),
('¿Cómo se llama el continente donde está el país más grande del mundo?', 'Asia', 'Contiene grandes imperios, montañas y el país más extenso.'),
('¿Qué país es conocido por sus canales y tulipanes?', 'Países Bajos', 'Tiene molinos, canales y flores que pintan el paisaje.'),
('¿Cuál es el deporte más popular del mundo?', 'Fútbol', 'Un balón une a millones bajo un mismo grito de gol.'),
('¿Cuál es la moneda de los Estados Unidos?', 'Dólar', 'Es usada en varios países y su símbolo es mundialmente conocido.'),
('¿Qué país es famoso por su comida italiana, como pizza y pasta?', 'Italia', 'La comida es arte en cada plato, de Roma a Venecia.'),
('¿Quién pintó La última cena?', 'Leonardo da Vinci', 'Obra renacentista entre las más reconocidas del mundo.'),
('¿Qué animal es el símbolo de la paz?', 'Paloma', 'Ave blanca que lleva una rama como símbolo de paz.'),
('¿Qué continente tiene el mayor número de países?', 'África', 'Diverso en culturas, lenguas y paisajes.'),
('¿Cuántos jugadores tiene un equipo de fútbol en el campo?', '11', 'Son los que juegan y deben trabajar en equipo.'),
('¿Qué planeta es conocido como el "planeta rojo"?', 'Marte', 'Su color rojo lo hace visible en el cielo.'),
('¿Qué tipo de animal es la ballena?', 'Mamífero', 'Vive en el mar pero respira aire como los humanos.'),
('¿Qué instrumento musical se toca con un arco?', 'Violín', 'Sus cuerdas vibran cuando el arco las acaricia.'),
('¿Cómo se llama el primer satélite artificial de la Tierra?', 'Sputnik', 'Lanzado en 1957, marcó el inicio de la carrera espacial.'),
('¿En qué país se encuentra la Gran Muralla?', 'China', 'Gran estructura hecha para proteger el antiguo imperio.'),
('¿Qué país es conocido por su café y el tango?', 'Argentina', 'Buenos Aires vibra con pasión, música y buen café.'),
('¿Qué famoso científico desarrolló la teoría de la relatividad?', 'Albert Einstein', 'Su pelo alborotado refleja su brillantez.'),
('¿En qué país se celebra el Día de los Muertos?', 'México', 'Se honra a los muertos con flores y altares coloridos.'),
('¿Qué instrumento tiene cuerdas y se toca con los dedos o un pua?', 'Guitarra', 'Compañera ideal en canciones, conciertos y fogatas.'),
('¿Qué continente está compuesto principalmente de desiertos?', 'África', 'Hay dunas y sol abrasador en gran parte del continente.'),
('¿Qué océano está entre América y Europa?', 'Océano Atlántico', 'Conecta dos continentes y fue clave en la exploración.'),
('¿Cómo se llama el proceso por el cual las plantas producen su propio alimento?', 'Fotosíntesis', 'Las hojas usan luz solar para crear energía.'),
('¿Qué tipo de animal es el pingüino?', 'Ave', 'No vuela, pero se desliza ágilmente sobre el hielo.'),
('¿Cuál es el número de huesos en el cuerpo humano?', '206', 'Son la base del cuerpo y permiten el movimiento.'),
('¿Qué continente es conocido como "la cuna de la humanidad"?', 'África', 'Aquí comenzaron los primeros pasos del ser humano.'),
('¿Cómo se llama el escritor de "Don Quijote de la Mancha"?', 'Miguel de Cervantes', 'Llevó a su personaje a luchar contra molinos.'),
('¿Cuál es el color que tiene la esmeralda?', 'Verde', 'Una piedra preciosa que refleja el color de la naturaleza.'),
('¿En qué país se encuentra el Everest, la montaña más alta del mundo?', 'Nepal', 'El techo del mundo está en este país montañoso.'),
('¿Cómo se llama la primera mujer en volar al espacio?', 'Valentina Tereshkova', 'Cosmonauta rusa que hizo historia en el espacio.'),
('¿Qué símbolo matemático se usa para representar el valor de "pi"?', 'π', 'Constante infinita que aparece en los círculos.'),
('¿En qué ciudad se encuentran las pirámides de Egipto?', 'Giza', 'Fueron construidas para faraones y siguen sorprendiendo.'),
('¿Qué artista pintó la Capilla Sixtina?', 'Miguel Ángel', 'Pintó el techo de una iglesia con gran genialidad.'),
('¿Qué animal es el mamífero más grande del mundo?', 'Ballena azul', 'Gigante marino más grande que cualquier criatura terrestre.'),
('¿Qué país es famoso por sus fjordos y auroras boreales?', 'Noruega', 'Paisajes impresionantes y cielos danzantes en invierno.'),
('¿Cómo se llama el principal dios del panteón griego?', 'Zeus', 'Rey de los dioses, con trono en el monte Olimpo.'),
('¿Cuál es el metal precioso que se encuentra en las joyas?', 'Oro', 'Brilla desde la antigüedad como símbolo de riqueza.'),
('¿Quién escribió "Cien años de soledad"?', 'Gabriel García Márquez', 'Autor colombiano que creó el mágico mundo de Macondo.'),
('¿Cuál es el nombre del continente donde se encuentra el desierto de Sahara?','África', 'Este desierto es uno de los más grandes del mundo'),
('¿Qué instrumento musical tiene cuerdas y se toca con un arco?', 'Violonchelo', 'Este instrumento de cuerdas es el más grande de su familia'),
('¿Qué sustancia da color a las plantas y es esencial para la fotosíntesis?', 'Clorofila', 'Es el secreto que da color verde a las hojas'),
('¿En qué país nació el escritor William Shakespeare?', 'Inglaterra', 'Este dramaturgo dejó su huella en el mundo con obras como "Hamlet" y "Romeo y Julieta".'),
('¿Cómo se llama el proceso por el cual las plantas convierten la luz solar en energía?', 'Fotosíntesis', 'Este proceso es vital para la vida en la Tierra, permitiendo que las plantas crezcan.'),
('¿Qué instrumento se utiliza para medir la temperatura?', 'Termómetro', 'Con este dispositivo podemos saber si hace calor o frío, ya sea en la calle o en el cuerpo.'),
('¿Cuál es el país con más población del mundo?', 'China', 'Esta nación tiene una población que supera el mil millones de personas.'),
('¿Qué animal tiene el cuello más largo del reino animal?', 'Jirafa', 'Este gigante de las sabanas africanas usa su largo cuello para alcanzar las hojas más altas de los árboles.'),
('¿En qué país se celebran los famosos carnavales de Río?', 'Brasil', 'Este festival, lleno de música, colores y samba, es uno de los más grandes y populares del mundo.'),
('¿Cómo se llama el proceso de transformación de un huevo en un pollito?', 'Incubación', 'Este proceso ocurre dentro del cascarón, donde un pequeño ser se desarrolla hasta salir al mundo.'),
('¿Qué tipo de animal es el koala?', 'Marsupial', 'Este tierno animal australiano pasa sus días durmiendo en los eucaliptos y lleva a sus crías en una bolsa.'),
('¿Qué nombre recibe el océano que separa América de Asia?', 'Océano Pacífico', 'Su nombre sugiere calma, pero esconde algunas de las mareas más poderosas del planeta.'),
('¿Cuál es el nombre del principal río de América del Sur?', 'Amazonas', 'Este río fluye a través de la selva tropical y es vital para la vida de la región.'),
('¿En qué país se encuentran las ruinas de Petra?', 'Jordania', 'Esta antigua ciudad, esculpida en la roca, es un símbolo de la arquitectura de los nabateos.'),
('¿Qué elemento químico tiene el símbolo "O"?', 'Oxígeno', 'Este gas es vital para la vida, ya que lo necesitamos para respirar.'),
('¿Qué civilización construyó las pirámides de Giza?', 'Egipcia', 'Los egipcios eran conocidos por su avanzado conocimiento de la astronomía y la arquitectura.'),
('¿Qué instrumento musical de viento tiene una lengüeta doble?', 'Oboe', 'Su sonido característico se puede escuchar en orquestas, y se toca soplando por una lengüeta.'),
('¿Cómo se llama el continente que tiene más países?', 'África', 'Este continente alberga más de 50 países, cada uno con su cultura y tradiciones únicas.'),
('¿Quién pintó "La noche estrellada"?', 'Vincent van Gogh', 'Este pintor holandés creó una obra en la que las estrellas parecen girar en un cielo nocturno lleno de emoción.'),
('¿Qué científico propuso la teoría de la evolución?', 'Charles Darwin', 'Sus estudios sobre las especies en las Islas Galápagos cambiaron nuestra visión sobre la vida.'),
('¿Cómo se llama el continente más grande del mundo?', 'Asia', 'En este continente se encuentran algunas de las civilizaciones más antiguas y grandes del mundo.'),
('¿Qué país es famoso por su tango y fútbol?', 'Argentina', 'Este país tiene una gran pasión por el baile y el deporte, siendo el hogar de muchos íconos internacionales.'),
('¿Qué órgano del cuerpo humano se encarga de filtrar la sangre?', 'Riñón', 'Este par de órganos trabaja incansablemente para mantener nuestro cuerpo en equilibrio.'),
('¿Qué escritor es conocido por su obra "1984"?', 'George Orwell', 'Su visión distópica de un futuro totalitario sigue siendo relevante hoy en día.'),
('¿Qué metal se usa para hacer monedas y joyas, y es muy codiciado?', 'Plata', 'Este metal es menos raro que el oro, pero igualmente apreciado en todo el mundo.'),
('¿Cuál es el animal terrestre más rápido?', 'Guepardo', 'Este felino puede alcanzar velocidades sorprendentes, superando incluso a los autos en carreras cortas.'),
('¿Qué enfermedad se previene con la vacuna contra el sarampión?', 'Sarampión', 'Esta enfermedad es más común en niños, pero afortunadamente se puede prevenir con una simple vacuna.'),
('¿Quién fue el líder de la revolución cubana?', 'Fidel Castro', 'Este hombre fue un símbolo de la lucha por la independencia en el siglo XX.'),
('¿Cómo se llama el continente con el desierto de Atacama?', 'América del Sur', 'Este desierto chileno es conocido como el lugar más árido del planeta.'),
('¿Qué país tiene una hoja de arce en su bandera?', 'Canadá', 'Este símbolo está asociado con los vastos bosques y la naturaleza de este país norteamericano.'),
('¿Quién inventó la teoría de la relatividad?', 'Albert Einstein', 'Su famosa ecuación E=mc² cambió nuestra comprensión del universo.'),
('¿En qué país se originó la pizza?', 'Italia', 'Este platillo se ha convertido en un favorito mundial, pero su cuna está en Nápoles.'),
('¿Qué ciudad es conocida como "La ciudad de la luz"?', 'París', 'Es famosa por su torre y por ser el epicentro de la moda y la cultura.'),
('¿Cómo se llama el proceso en el que las plantas convierten el dióxido de carbono en oxígeno?', 'Fotosíntesis', 'Este proceso es fundamental para la vida en la Tierra, ya que produce el aire que respiramos.'),
('¿Qué país es famoso por su carnaval y samba?', 'Brasil', 'Este país alberga uno de los mayores desfiles de carnaval del mundo.'),
('¿Qué insecto es conocido por su capacidad para producir miel?', 'Abeja', 'Estas pequeñas trabajadoras vuelan de flor en flor, creando un dulce manjar.'),
('¿Quién pintó el Guernica?', 'Pablo Picasso', 'Esta obra maestra es un grito de protesta contra la guerra, pintada en tonos sombríos.'),
('¿Qué océano se encuentra entre América y África?', 'Océano Atlántico', 'Este océano ha sido un puente de comercio y exploración entre dos continentes.'),
('¿Cómo se llama el continente que alberga la selva del Amazonas?', 'América del Sur', 'En este continente, la selva tropical más grande del mundo juega un papel crucial en el equilibrio del planeta.'),
('¿Cuál es el segundo planeta más cercano al sol?', 'Venus', 'Este planeta es conocido por su atmósfera densa y sus temperaturas extremadamente altas.');

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
    elemento INT NOT NULL,
	PRIMARY KEY (cord_x, cord_y, id_disposicion),
    FOREIGN KEY (id_disposicion) REFERENCES disposiciones(id)
);
