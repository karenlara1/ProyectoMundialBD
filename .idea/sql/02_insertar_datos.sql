------------------------------------------------------------
-- PROYECTO BASE DE DATOS I - MUNDIAL 2026
-- SCRIPT 02: INSERCIÓN DE DATOS BASE
-- SGBD: ORACLE
------------------------------------------------------------

------------------------------------------------------------
-- USUARIOS DEL SISTEMA
------------------------------------------------------------
INSERT INTO usuario (username, password, rol)
VALUES ('admin', 'admin123', 'ADMINISTRADOR');

INSERT INTO usuario (username, password, rol)
VALUES ('tradicional', 'trad123', 'TRADICIONAL');

INSERT INTO usuario (username, password, rol)
VALUES ('esporadico', 'esp123', 'ESPORADICO');

------------------------------------------------------------
-- CONFEDERACIONES
------------------------------------------------------------
INSERT INTO confederacion (nombre, continente) VALUES ('CONMEBOL', 'América del Sur');
INSERT INTO confederacion (nombre, continente) VALUES ('UEFA', 'Europa');
INSERT INTO confederacion (nombre, continente) VALUES ('CONCACAF', 'Norteamérica, Centroamérica y Caribe');
INSERT INTO confederacion (nombre, continente) VALUES ('CAF', 'África');
INSERT INTO confederacion (nombre, continente) VALUES ('AFC', 'Asia');
INSERT INTO confederacion (nombre, continente) VALUES ('OFC', 'Oceanía');

------------------------------------------------------------
-- GRUPOS MUNDIAL 2026
------------------------------------------------------------
INSERT INTO grupo_mundial (nombre) VALUES ('A');
INSERT INTO grupo_mundial (nombre) VALUES ('B');
INSERT INTO grupo_mundial (nombre) VALUES ('C');
INSERT INTO grupo_mundial (nombre) VALUES ('D');
INSERT INTO grupo_mundial (nombre) VALUES ('E');
INSERT INTO grupo_mundial (nombre) VALUES ('F');
INSERT INTO grupo_mundial (nombre) VALUES ('G');
INSERT INTO grupo_mundial (nombre) VALUES ('H');
INSERT INTO grupo_mundial (nombre) VALUES ('I');
INSERT INTO grupo_mundial (nombre) VALUES ('J');
INSERT INTO grupo_mundial (nombre) VALUES ('K');
INSERT INTO grupo_mundial (nombre) VALUES ('L');

------------------------------------------------------------
-- PAÍSES
------------------------------------------------------------
INSERT INTO pais (nombre, es_anfitrion, id_confederacion)
VALUES ('México', 'S', (SELECT id_confederacion FROM confederacion WHERE nombre = 'CONCACAF'));

INSERT INTO pais (nombre, es_anfitrion, id_confederacion)
VALUES ('Canadá', 'S', (SELECT id_confederacion FROM confederacion WHERE nombre = 'CONCACAF'));

INSERT INTO pais (nombre, es_anfitrion, id_confederacion)
VALUES ('Estados Unidos', 'S', (SELECT id_confederacion FROM confederacion WHERE nombre = 'CONCACAF'));

INSERT INTO pais (nombre, es_anfitrion, id_confederacion)
VALUES ('Brasil', 'N', (SELECT id_confederacion FROM confederacion WHERE nombre = 'CONMEBOL'));

INSERT INTO pais (nombre, es_anfitrion, id_confederacion)
VALUES ('Argentina', 'N', (SELECT id_confederacion FROM confederacion WHERE nombre = 'CONMEBOL'));

INSERT INTO pais (nombre, es_anfitrion, id_confederacion)
VALUES ('España', 'N', (SELECT id_confederacion FROM confederacion WHERE nombre = 'UEFA'));

INSERT INTO pais (nombre, es_anfitrion, id_confederacion)
VALUES ('Francia', 'N', (SELECT id_confederacion FROM confederacion WHERE nombre = 'UEFA'));

INSERT INTO pais (nombre, es_anfitrion, id_confederacion)
VALUES ('Inglaterra', 'N', (SELECT id_confederacion FROM confederacion WHERE nombre = 'UEFA'));

------------------------------------------------------------
-- POSICIONES
------------------------------------------------------------
INSERT INTO posicion (nombre) VALUES ('Portero');
INSERT INTO posicion (nombre) VALUES ('Defensa');
INSERT INTO posicion (nombre) VALUES ('Mediocampista');
INSERT INTO posicion (nombre) VALUES ('Delantero');

------------------------------------------------------------
-- EQUIPOS / SELECCIONES
------------------------------------------------------------
INSERT INTO equipo (nombre, fecha_fundacion, id_pais, id_grupo)
VALUES ('México', TO_DATE('1927-01-01', 'YYYY-MM-DD'),
        (SELECT id_pais FROM pais WHERE nombre = 'México'),
        (SELECT id_grupo FROM grupo_mundial WHERE nombre = 'A'));

INSERT INTO equipo (nombre, fecha_fundacion, id_pais, id_grupo)
VALUES ('Canadá', TO_DATE('1912-01-01', 'YYYY-MM-DD'),
        (SELECT id_pais FROM pais WHERE nombre = 'Canadá'),
        (SELECT id_grupo FROM grupo_mundial WHERE nombre = 'B'));

INSERT INTO equipo (nombre, fecha_fundacion, id_pais, id_grupo)
VALUES ('Brasil', TO_DATE('1914-06-08', 'YYYY-MM-DD'),
        (SELECT id_pais FROM pais WHERE nombre = 'Brasil'),
        (SELECT id_grupo FROM grupo_mundial WHERE nombre = 'C'));

INSERT INTO equipo (nombre, fecha_fundacion, id_pais, id_grupo)
VALUES ('Estados Unidos', TO_DATE('1913-04-05', 'YYYY-MM-DD'),
        (SELECT id_pais FROM pais WHERE nombre = 'Estados Unidos'),
        (SELECT id_grupo FROM grupo_mundial WHERE nombre = 'D'));

INSERT INTO equipo (nombre, fecha_fundacion, id_pais, id_grupo)
VALUES ('España', TO_DATE('1913-09-29', 'YYYY-MM-DD'),
        (SELECT id_pais FROM pais WHERE nombre = 'España'),
        (SELECT id_grupo FROM grupo_mundial WHERE nombre = 'H'));

INSERT INTO equipo (nombre, fecha_fundacion, id_pais, id_grupo)
VALUES ('Francia', TO_DATE('1919-04-07', 'YYYY-MM-DD'),
        (SELECT id_pais FROM pais WHERE nombre = 'Francia'),
        (SELECT id_grupo FROM grupo_mundial WHERE nombre = 'I'));

INSERT INTO equipo (nombre, fecha_fundacion, id_pais, id_grupo)
VALUES ('Argentina', TO_DATE('1893-02-21', 'YYYY-MM-DD'),
        (SELECT id_pais FROM pais WHERE nombre = 'Argentina'),
        (SELECT id_grupo FROM grupo_mundial WHERE nombre = 'J'));

INSERT INTO equipo (nombre, fecha_fundacion, id_pais, id_grupo)
VALUES ('Inglaterra', TO_DATE('1863-10-26', 'YYYY-MM-DD'),
        (SELECT id_pais FROM pais WHERE nombre = 'Inglaterra'),
        (SELECT id_grupo FROM grupo_mundial WHERE nombre = 'L'));

------------------------------------------------------------
-- DIRECTORES TÉCNICOS
------------------------------------------------------------
INSERT INTO director_tecnico (nombre, fecha_nacimiento, nacionalidad, id_equipo)
VALUES ('Javier Aguirre', TO_DATE('1958-12-01', 'YYYY-MM-DD'), 'Mexicana',
        (SELECT id_equipo FROM equipo WHERE nombre = 'México'));

INSERT INTO director_tecnico (nombre, fecha_nacimiento, nacionalidad, id_equipo)
VALUES ('Jesse Marsch', TO_DATE('1973-11-08', 'YYYY-MM-DD'), 'Estadounidense',
        (SELECT id_equipo FROM equipo WHERE nombre = 'Canadá'));

INSERT INTO director_tecnico (nombre, fecha_nacimiento, nacionalidad, id_equipo)
VALUES ('Dorival Júnior', TO_DATE('1962-04-25', 'YYYY-MM-DD'), 'Brasileña',
        (SELECT id_equipo FROM equipo WHERE nombre = 'Brasil'));

INSERT INTO director_tecnico (nombre, fecha_nacimiento, nacionalidad, id_equipo)
VALUES ('Mauricio Pochettino', TO_DATE('1972-03-02', 'YYYY-MM-DD'), 'Argentina',
        (SELECT id_equipo FROM equipo WHERE nombre = 'Estados Unidos'));

INSERT INTO director_tecnico (nombre, fecha_nacimiento, nacionalidad, id_equipo)
VALUES ('Luis de la Fuente', TO_DATE('1961-06-21', 'YYYY-MM-DD'), 'Española',
        (SELECT id_equipo FROM equipo WHERE nombre = 'España'));

INSERT INTO director_tecnico (nombre, fecha_nacimiento, nacionalidad, id_equipo)
VALUES ('Didier Deschamps', TO_DATE('1968-10-15', 'YYYY-MM-DD'), 'Francesa',
        (SELECT id_equipo FROM equipo WHERE nombre = 'Francia'));

INSERT INTO director_tecnico (nombre, fecha_nacimiento, nacionalidad, id_equipo)
VALUES ('Lionel Scaloni', TO_DATE('1978-05-16', 'YYYY-MM-DD'), 'Argentina',
        (SELECT id_equipo FROM equipo WHERE nombre = 'Argentina'));

INSERT INTO director_tecnico (nombre, fecha_nacimiento, nacionalidad, id_equipo)
VALUES ('Thomas Tuchel', TO_DATE('1973-08-29', 'YYYY-MM-DD'), 'Alemana',
        (SELECT id_equipo FROM equipo WHERE nombre = 'Inglaterra'));

------------------------------------------------------------
-- JUGADORES
------------------------------------------------------------
INSERT INTO jugador (nombre, fecha_nacimiento, estatura, peso, valor, id_equipo, id_posicion)
VALUES ('Guillermo Ochoa', TO_DATE('1985-07-13', 'YYYY-MM-DD'), 1.85, 78, 1000000,
        (SELECT id_equipo FROM equipo WHERE nombre = 'México'),
        (SELECT id_posicion FROM posicion WHERE nombre = 'Portero'));

INSERT INTO jugador (nombre, fecha_nacimiento, estatura, peso, valor, id_equipo, id_posicion)
VALUES ('Edson Álvarez', TO_DATE('1997-10-24', 'YYYY-MM-DD'), 1.87, 73, 35000000,
        (SELECT id_equipo FROM equipo WHERE nombre = 'México'),
        (SELECT id_posicion FROM posicion WHERE nombre = 'Mediocampista'));

INSERT INTO jugador (nombre, fecha_nacimiento, estatura, peso, valor, id_equipo, id_posicion)
VALUES ('Alphonso Davies', TO_DATE('2000-11-02', 'YYYY-MM-DD'), 1.83, 75, 50000000,
        (SELECT id_equipo FROM equipo WHERE nombre = 'Canadá'),
        (SELECT id_posicion FROM posicion WHERE nombre = 'Defensa'));

INSERT INTO jugador (nombre, fecha_nacimiento, estatura, peso, valor, id_equipo, id_posicion)
VALUES ('Jonathan David', TO_DATE('2000-01-14', 'YYYY-MM-DD'), 1.80, 77, 45000000,
        (SELECT id_equipo FROM equipo WHERE nombre = 'Canadá'),
        (SELECT id_posicion FROM posicion WHERE nombre = 'Delantero'));

INSERT INTO jugador (nombre, fecha_nacimiento, estatura, peso, valor, id_equipo, id_posicion)
VALUES ('Christian Pulisic', TO_DATE('1998-09-18', 'YYYY-MM-DD'), 1.77, 69, 40000000,
        (SELECT id_equipo FROM equipo WHERE nombre = 'Estados Unidos'),
        (SELECT id_posicion FROM posicion WHERE nombre = 'Delantero'));

INSERT INTO jugador (nombre, fecha_nacimiento, estatura, peso, valor, id_equipo, id_posicion)
VALUES ('Vinícius Júnior', TO_DATE('2000-07-12', 'YYYY-MM-DD'), 1.76, 73, 150000000,
        (SELECT id_equipo FROM equipo WHERE nombre = 'Brasil'),
        (SELECT id_posicion FROM posicion WHERE nombre = 'Delantero'));

INSERT INTO jugador (nombre, fecha_nacimiento, estatura, peso, valor, id_equipo, id_posicion)
VALUES ('Lamine Yamal', TO_DATE('2007-07-13', 'YYYY-MM-DD'), 1.80, 72, 150000000,
        (SELECT id_equipo FROM equipo WHERE nombre = 'España'),
        (SELECT id_posicion FROM posicion WHERE nombre = 'Delantero'));

INSERT INTO jugador (nombre, fecha_nacimiento, estatura, peso, valor, id_equipo, id_posicion)
VALUES ('Kylian Mbappé', TO_DATE('1998-12-20', 'YYYY-MM-DD'), 1.78, 75, 180000000,
        (SELECT id_equipo FROM equipo WHERE nombre = 'Francia'),
        (SELECT id_posicion FROM posicion WHERE nombre = 'Delantero'));

INSERT INTO jugador (nombre, fecha_nacimiento, estatura, peso, valor, id_equipo, id_posicion)
VALUES ('Lionel Messi', TO_DATE('1987-06-24', 'YYYY-MM-DD'), 1.70, 72, 30000000,
        (SELECT id_equipo FROM equipo WHERE nombre = 'Argentina'),
        (SELECT id_posicion FROM posicion WHERE nombre = 'Delantero'));

INSERT INTO jugador (nombre, fecha_nacimiento, estatura, peso, valor, id_equipo, id_posicion)
VALUES ('Jude Bellingham', TO_DATE('2003-06-29', 'YYYY-MM-DD'), 1.86, 75, 180000000,
        (SELECT id_equipo FROM equipo WHERE nombre = 'Inglaterra'),
        (SELECT id_posicion FROM posicion WHERE nombre = 'Mediocampista'));

------------------------------------------------------------
-- CIUDADES SEDE
------------------------------------------------------------
INSERT INTO ciudad (nombre, id_pais)
VALUES ('Ciudad de México', (SELECT id_pais FROM pais WHERE nombre = 'México'));

INSERT INTO ciudad (nombre, id_pais)
VALUES ('Guadalajara', (SELECT id_pais FROM pais WHERE nombre = 'México'));

INSERT INTO ciudad (nombre, id_pais)
VALUES ('Monterrey', (SELECT id_pais FROM pais WHERE nombre = 'México'));

INSERT INTO ciudad (nombre, id_pais)
VALUES ('Toronto', (SELECT id_pais FROM pais WHERE nombre = 'Canadá'));

INSERT INTO ciudad (nombre, id_pais)
VALUES ('Vancouver', (SELECT id_pais FROM pais WHERE nombre = 'Canadá'));

INSERT INTO ciudad (nombre, id_pais)
VALUES ('Atlanta', (SELECT id_pais FROM pais WHERE nombre = 'Estados Unidos'));

INSERT INTO ciudad (nombre, id_pais)
VALUES ('Boston', (SELECT id_pais FROM pais WHERE nombre = 'Estados Unidos'));

INSERT INTO ciudad (nombre, id_pais)
VALUES ('Dallas', (SELECT id_pais FROM pais WHERE nombre = 'Estados Unidos'));

INSERT INTO ciudad (nombre, id_pais)
VALUES ('Houston', (SELECT id_pais FROM pais WHERE nombre = 'Estados Unidos'));

INSERT INTO ciudad (nombre, id_pais)
VALUES ('Kansas City', (SELECT id_pais FROM pais WHERE nombre = 'Estados Unidos'));

INSERT INTO ciudad (nombre, id_pais)
VALUES ('Los Ángeles', (SELECT id_pais FROM pais WHERE nombre = 'Estados Unidos'));

INSERT INTO ciudad (nombre, id_pais)
VALUES ('Miami', (SELECT id_pais FROM pais WHERE nombre = 'Estados Unidos'));

INSERT INTO ciudad (nombre, id_pais)
VALUES ('Nueva York/Nueva Jersey', (SELECT id_pais FROM pais WHERE nombre = 'Estados Unidos'));

INSERT INTO ciudad (nombre, id_pais)
VALUES ('Filadelfia', (SELECT id_pais FROM pais WHERE nombre = 'Estados Unidos'));

INSERT INTO ciudad (nombre, id_pais)
VALUES ('San Francisco Bay Area', (SELECT id_pais FROM pais WHERE nombre = 'Estados Unidos'));

INSERT INTO ciudad (nombre, id_pais)
VALUES ('Seattle', (SELECT id_pais FROM pais WHERE nombre = 'Estados Unidos'));

------------------------------------------------------------
-- ESTADIOS SEDE
------------------------------------------------------------
INSERT INTO estadio (nombre, capacidad, direccion, id_ciudad)
VALUES ('Estadio Azteca', 87523, 'Calzada de Tlalpan 3465, Ciudad de México',
        (SELECT id_ciudad FROM ciudad WHERE nombre = 'Ciudad de México'));

INSERT INTO estadio (nombre, capacidad, direccion, id_ciudad)
VALUES ('Estadio Akron', 49850, 'Circuito JVC 2800, Zapopan, Jalisco',
        (SELECT id_ciudad FROM ciudad WHERE nombre = 'Guadalajara'));

INSERT INTO estadio (nombre, capacidad, direccion, id_ciudad)
VALUES ('Estadio BBVA', 53500, 'Av. Pablo Livas 2011, Guadalupe, Nuevo León',
        (SELECT id_ciudad FROM ciudad WHERE nombre = 'Monterrey'));

INSERT INTO estadio (nombre, capacidad, direccion, id_ciudad)
VALUES ('BMO Field', 30000, '170 Princes Blvd, Toronto',
        (SELECT id_ciudad FROM ciudad WHERE nombre = 'Toronto'));

INSERT INTO estadio (nombre, capacidad, direccion, id_ciudad)
VALUES ('BC Place', 54500, '777 Pacific Blvd, Vancouver',
        (SELECT id_ciudad FROM ciudad WHERE nombre = 'Vancouver'));

INSERT INTO estadio (nombre, capacidad, direccion, id_ciudad)
VALUES ('Mercedes-Benz Stadium', 71000, '1 AMB Dr NW, Atlanta',
        (SELECT id_ciudad FROM ciudad WHERE nombre = 'Atlanta'));

INSERT INTO estadio (nombre, capacidad, direccion, id_ciudad)
VALUES ('Gillette Stadium', 65878, '1 Patriot Pl, Foxborough',
        (SELECT id_ciudad FROM ciudad WHERE nombre = 'Boston'));

INSERT INTO estadio (nombre, capacidad, direccion, id_ciudad)
VALUES ('AT&T Stadium', 80000, '1 AT&T Way, Arlington',
        (SELECT id_ciudad FROM ciudad WHERE nombre = 'Dallas'));

INSERT INTO estadio (nombre, capacidad, direccion, id_ciudad)
VALUES ('NRG Stadium', 72220, 'NRG Pkwy, Houston',
        (SELECT id_ciudad FROM ciudad WHERE nombre = 'Houston'));

INSERT INTO estadio (nombre, capacidad, direccion, id_ciudad)
VALUES ('Arrowhead Stadium', 76416, '1 Arrowhead Dr, Kansas City',
        (SELECT id_ciudad FROM ciudad WHERE nombre = 'Kansas City'));

INSERT INTO estadio (nombre, capacidad, direccion, id_ciudad)
VALUES ('SoFi Stadium', 70240, '1001 Stadium Dr, Inglewood',
        (SELECT id_ciudad FROM ciudad WHERE nombre = 'Los Ángeles'));

INSERT INTO estadio (nombre, capacidad, direccion, id_ciudad)
VALUES ('Hard Rock Stadium', 65326, '347 Don Shula Dr, Miami Gardens',
        (SELECT id_ciudad FROM ciudad WHERE nombre = 'Miami'));

INSERT INTO estadio (nombre, capacidad, direccion, id_ciudad)
VALUES ('MetLife Stadium', 82500, '1 MetLife Stadium Dr, East Rutherford',
        (SELECT id_ciudad FROM ciudad WHERE nombre = 'Nueva York/Nueva Jersey'));

INSERT INTO estadio (nombre, capacidad, direccion, id_ciudad)
VALUES ('Lincoln Financial Field', 67594, 'One Lincoln Financial Field Way, Philadelphia',
        (SELECT id_ciudad FROM ciudad WHERE nombre = 'Filadelfia'));

INSERT INTO estadio (nombre, capacidad, direccion, id_ciudad)
VALUES ('Levi''s Stadium', 68500, '4900 Marie P DeBartolo Way, Santa Clara',
        (SELECT id_ciudad FROM ciudad WHERE nombre = 'San Francisco Bay Area'));

INSERT INTO estadio (nombre, capacidad, direccion, id_ciudad)
VALUES ('Lumen Field', 68740, '800 Occidental Ave S, Seattle',
        (SELECT id_ciudad FROM ciudad WHERE nombre = 'Seattle'));

------------------------------------------------------------
-- PARTIDOS DE PRUEBA
------------------------------------------------------------
INSERT INTO partido (
    fecha_partido, hora_partido, id_estadio, id_grupo,
    id_equipo_local, id_equipo_visitante, goles_local, goles_visitante, estado
)
VALUES (
           TO_DATE('2026-06-11', 'YYYY-MM-DD'), '20:00',
           (SELECT id_estadio FROM estadio WHERE nombre = 'Estadio Azteca'),
           (SELECT id_grupo FROM grupo_mundial WHERE nombre = 'A'),
           (SELECT id_equipo FROM equipo WHERE nombre = 'México'),
           (SELECT id_equipo FROM equipo WHERE nombre = 'Canadá'),
           0, 0, 'PROGRAMADO'
       );

INSERT INTO partido (
    fecha_partido, hora_partido, id_estadio, id_grupo,
    id_equipo_local, id_equipo_visitante, goles_local, goles_visitante, estado
)
VALUES (
           TO_DATE('2026-06-12', 'YYYY-MM-DD'), '18:00',
           (SELECT id_estadio FROM estadio WHERE nombre = 'BMO Field'),
           (SELECT id_grupo FROM grupo_mundial WHERE nombre = 'B'),
           (SELECT id_equipo FROM equipo WHERE nombre = 'Canadá'),
           (SELECT id_equipo FROM equipo WHERE nombre = 'Estados Unidos'),
           0, 0, 'PROGRAMADO'
       );

INSERT INTO partido (
    fecha_partido, hora_partido, id_estadio, id_grupo,
    id_equipo_local, id_equipo_visitante, goles_local, goles_visitante, estado
)
VALUES (
           TO_DATE('2026-06-13', 'YYYY-MM-DD'), '19:00',
           (SELECT id_estadio FROM estadio WHERE nombre = 'MetLife Stadium'),
           (SELECT id_grupo FROM grupo_mundial WHERE nombre = 'D'),
           (SELECT id_equipo FROM equipo WHERE nombre = 'Estados Unidos'),
           (SELECT id_equipo FROM equipo WHERE nombre = 'Inglaterra'),
           0, 0, 'PROGRAMADO'
       );

INSERT INTO partido (
    fecha_partido, hora_partido, id_estadio, id_grupo,
    id_equipo_local, id_equipo_visitante, goles_local, goles_visitante, estado
)
VALUES (
           TO_DATE('2026-06-14', 'YYYY-MM-DD'), '17:00',
           (SELECT id_estadio FROM estadio WHERE nombre = 'Hard Rock Stadium'),
           (SELECT id_grupo FROM grupo_mundial WHERE nombre = 'C'),
           (SELECT id_equipo FROM equipo WHERE nombre = 'Brasil'),
           (SELECT id_equipo FROM equipo WHERE nombre = 'Argentina'),
           0, 0, 'PROGRAMADO'
       );

INSERT INTO partido (
    fecha_partido, hora_partido, id_estadio, id_grupo,
    id_equipo_local, id_equipo_visitante, goles_local, goles_visitante, estado
)
VALUES (
           TO_DATE('2026-06-15', 'YYYY-MM-DD'), '16:00',
           (SELECT id_estadio FROM estadio WHERE nombre = 'BC Place'),
           (SELECT id_grupo FROM grupo_mundial WHERE nombre = 'H'),
           (SELECT id_equipo FROM equipo WHERE nombre = 'España'),
           (SELECT id_equipo FROM equipo WHERE nombre = 'Francia'),
           0, 0, 'PROGRAMADO'
       );

------------------------------------------------------------
-- BITÁCORA DE PRUEBA
------------------------------------------------------------
INSERT INTO bitacora (id_usuario, fecha_hora_ingreso, fecha_hora_salida)
VALUES (
           (SELECT id_usuario FROM usuario WHERE username = 'admin'),
           TO_TIMESTAMP('2026-05-22 08:00', 'YYYY-MM-DD HH24:MI'),
           TO_TIMESTAMP('2026-05-22 09:30', 'YYYY-MM-DD HH24:MI')
       );

INSERT INTO bitacora (id_usuario, fecha_hora_ingreso, fecha_hora_salida)
VALUES (
           (SELECT id_usuario FROM usuario WHERE username = 'tradicional'),
           TO_TIMESTAMP('2026-05-22 10:00', 'YYYY-MM-DD HH24:MI'),
           TO_TIMESTAMP('2026-05-22 11:15', 'YYYY-MM-DD HH24:MI')
       );

COMMIT;