------------------------------------------------------------
-- PROYECTO BASE DE DATOS I - MUNDIAL 2026
-- SCRIPT 04: VERIFICACIÓN DE DATOS
-- SGBD: ORACLE
------------------------------------------------------------

------------------------------------------------------------
-- 1. VERIFICAR TABLAS CREADAS
------------------------------------------------------------
SELECT table_name
FROM user_tables
WHERE table_name IN (
                     'USUARIO',
                     'BITACORA',
                     'CONFEDERACION',
                     'GRUPO_MUNDIAL',
                     'PAIS',
                     'EQUIPO',
                     'DIRECTOR_TECNICO',
                     'POSICION',
                     'JUGADOR',
                     'CIUDAD',
                     'ESTADIO',
                     'PARTIDO'
    )
ORDER BY table_name;

------------------------------------------------------------
-- 2. CONTAR REGISTROS POR TABLA
------------------------------------------------------------
SELECT COUNT(*) AS total_usuarios FROM usuario;

SELECT COUNT(*) AS total_bitacora FROM bitacora;

SELECT COUNT(*) AS total_confederaciones FROM confederacion;

SELECT COUNT(*) AS total_grupos FROM grupo_mundial;

SELECT COUNT(*) AS total_paises FROM pais;

SELECT COUNT(*) AS total_equipos FROM equipo;

SELECT COUNT(*) AS total_directores_tecnicos FROM director_tecnico;

SELECT COUNT(*) AS total_posiciones FROM posicion;

SELECT COUNT(*) AS total_jugadores FROM jugador;

SELECT COUNT(*) AS total_ciudades FROM ciudad;

SELECT COUNT(*) AS total_estadios FROM estadio;

SELECT COUNT(*) AS total_partidos FROM partido;

------------------------------------------------------------
-- 3. VERIFICAR USUARIOS
------------------------------------------------------------
SELECT id_usuario,
       username,
       rol
FROM usuario
ORDER BY id_usuario;

------------------------------------------------------------
-- 4. VERIFICAR PAÍSES CON CONFEDERACIÓN
------------------------------------------------------------
SELECT p.id_pais,
       p.nombre AS pais,
       p.es_anfitrion,
       c.nombre AS confederacion
FROM pais p
         INNER JOIN confederacion c ON p.id_confederacion = c.id_confederacion
ORDER BY p.nombre;

------------------------------------------------------------
-- 5. VERIFICAR EQUIPOS CON PAÍS, GRUPO Y CONFEDERACIÓN
------------------------------------------------------------
SELECT e.id_equipo,
       e.nombre AS equipo,
       p.nombre AS pais,
       g.nombre AS grupo,
       c.nombre AS confederacion
FROM equipo e
         INNER JOIN pais p ON e.id_pais = p.id_pais
         INNER JOIN grupo_mundial g ON e.id_grupo = g.id_grupo
         INNER JOIN confederacion c ON p.id_confederacion = c.id_confederacion
ORDER BY e.nombre;

------------------------------------------------------------
-- 6. VERIFICAR JUGADORES CON EQUIPO Y POSICIÓN
------------------------------------------------------------
SELECT j.id_jugador,
       j.nombre AS jugador,
       e.nombre AS equipo,
       pos.nombre AS posicion,
       j.fecha_nacimiento,
       j.estatura,
       j.peso,
       j.valor
FROM jugador j
         INNER JOIN equipo e ON j.id_equipo = e.id_equipo
         INNER JOIN posicion pos ON j.id_posicion = pos.id_posicion
ORDER BY e.nombre, j.nombre;

------------------------------------------------------------
-- 7. VERIFICAR DIRECTORES TÉCNICOS CON EQUIPO
------------------------------------------------------------
SELECT dt.id_director_tecnico,
       dt.nombre AS director_tecnico,
       dt.fecha_nacimiento,
       dt.nacionalidad,
       e.nombre AS equipo
FROM director_tecnico dt
         INNER JOIN equipo e ON dt.id_equipo = e.id_equipo
ORDER BY e.nombre;

------------------------------------------------------------
-- 8. VERIFICAR CIUDADES CON PAÍS
------------------------------------------------------------
SELECT ci.id_ciudad,
       ci.nombre AS ciudad,
       p.nombre AS pais
FROM ciudad ci
         INNER JOIN pais p ON ci.id_pais = p.id_pais
ORDER BY p.nombre, ci.nombre;

------------------------------------------------------------
-- 9. VERIFICAR ESTADIOS CON CIUDAD Y PAÍS
------------------------------------------------------------
SELECT es.id_estadio,
       es.nombre AS estadio,
       es.capacidad,
       es.direccion,
       ci.nombre AS ciudad,
       p.nombre AS pais
FROM estadio es
         INNER JOIN ciudad ci ON es.id_ciudad = ci.id_ciudad
         INNER JOIN pais p ON ci.id_pais = p.id_pais
ORDER BY p.nombre, ci.nombre, es.nombre;

------------------------------------------------------------
-- 10. VERIFICAR PARTIDOS CON ESTADIO, GRUPO Y EQUIPOS
------------------------------------------------------------
SELECT pa.id_partido,
       pa.fecha_partido,
       pa.hora_partido,
       es.nombre AS estadio,
       g.nombre AS grupo,
       el.nombre AS equipo_local,
       ev.nombre AS equipo_visitante,
       pa.goles_local,
       pa.goles_visitante,
       pa.estado
FROM partido pa
         INNER JOIN estadio es ON pa.id_estadio = es.id_estadio
         INNER JOIN grupo_mundial g ON pa.id_grupo = g.id_grupo
         INNER JOIN equipo el ON pa.id_equipo_local = el.id_equipo
         INNER JOIN equipo ev ON pa.id_equipo_visitante = ev.id_equipo
ORDER BY pa.fecha_partido, pa.hora_partido;

------------------------------------------------------------
-- 11. VERIFICAR BITÁCORA
------------------------------------------------------------
SELECT b.id_bitacora,
       u.username,
       b.fecha_hora_ingreso,
       b.fecha_hora_salida
FROM bitacora b
         INNER JOIN usuario u ON b.id_usuario = u.id_usuario
ORDER BY b.fecha_hora_ingreso DESC;

------------------------------------------------------------
-- 12. VERIFICAR PAÍSES ANFITRIONES
------------------------------------------------------------
SELECT id_pais,
       nombre,
       es_anfitrion
FROM pais
WHERE es_anfitrion = 'S'
ORDER BY nombre;

------------------------------------------------------------
-- 13. VERIFICAR RESTRICCIÓN: PARTIDOS CON EQUIPOS DIFERENTES
------------------------------------------------------------
SELECT id_partido,
       id_equipo_local,
       id_equipo_visitante
FROM partido
WHERE id_equipo_local = id_equipo_visitante;

------------------------------------------------------------
-- 14. VERIFICAR PARTIDOS POR PAÍS ANFITRIÓN
------------------------------------------------------------
SELECT pais_sede.nombre AS pais_anfitrion,
       COUNT(p.id_partido) AS cantidad_partidos
FROM partido p
         INNER JOIN estadio es ON p.id_estadio = es.id_estadio
         INNER JOIN ciudad ci ON es.id_ciudad = ci.id_ciudad
         INNER JOIN pais pais_sede ON ci.id_pais = pais_sede.id_pais
GROUP BY pais_sede.nombre
ORDER BY pais_sede.nombre;