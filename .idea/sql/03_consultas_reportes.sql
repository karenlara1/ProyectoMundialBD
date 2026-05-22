------------------------------------------------------------
-- PROYECTO BASE DE DATOS I - MUNDIAL 2026
-- SCRIPT 03: CONSULTAS Y REPORTES
-- SGBD: ORACLE
------------------------------------------------------------

------------------------------------------------------------
-- 1. LISTAR USUARIOS QUE INGRESARON Y SALIERON
--    EN UN RANGO DE FECHA Y HORA
------------------------------------------------------------
SELECT b.id_bitacora,
       b.id_usuario,
       u.username,
       b.fecha_hora_ingreso,
       b.fecha_hora_salida
FROM bitacora b
         INNER JOIN usuario u ON b.id_usuario = u.id_usuario
WHERE b.fecha_hora_ingreso >= TO_TIMESTAMP('2026-05-22 00:00', 'YYYY-MM-DD HH24:MI')
  AND b.fecha_hora_salida <= TO_TIMESTAMP('2026-05-22 23:59', 'YYYY-MM-DD HH24:MI')
  AND b.fecha_hora_salida IS NOT NULL
ORDER BY b.fecha_hora_ingreso DESC;

------------------------------------------------------------
-- 2. JUGADOR MÁS COSTOSO POR CONFEDERACIÓN
------------------------------------------------------------
SELECT c.nombre AS confederacion,
       j.nombre AS jugador,
       e.nombre AS equipo,
       j.valor AS valor
FROM jugador j
         INNER JOIN equipo e ON j.id_equipo = e.id_equipo
         INNER JOIN pais p ON e.id_pais = p.id_pais
         INNER JOIN confederacion c ON p.id_confederacion = c.id_confederacion
WHERE j.valor = (
    SELECT MAX(j2.valor)
    FROM jugador j2
             INNER JOIN equipo e2 ON j2.id_equipo = e2.id_equipo
             INNER JOIN pais p2 ON e2.id_pais = p2.id_pais
    WHERE p2.id_confederacion = c.id_confederacion
)
ORDER BY c.nombre;

------------------------------------------------------------
-- 3. PARTIDOS QUE SE JUGARÁN EN UN ESTADIO ELEGIDO
--    EJEMPLO: ESTADIO AZTECA
------------------------------------------------------------
SELECT es.nombre AS estadio,
       p.fecha_partido AS fecha,
       p.hora_partido AS hora,
       el.nombre AS equipo_local,
       ev.nombre AS equipo_visitante,
       g.nombre AS grupo,
       p.estado AS estado
FROM partido p
         INNER JOIN estadio es ON p.id_estadio = es.id_estadio
         INNER JOIN equipo el ON p.id_equipo_local = el.id_equipo
         INNER JOIN equipo ev ON p.id_equipo_visitante = ev.id_equipo
         INNER JOIN grupo_mundial g ON p.id_grupo = g.id_grupo
WHERE es.nombre = 'Estadio Azteca'
ORDER BY p.fecha_partido, p.hora_partido;

------------------------------------------------------------
-- 4. EQUIPO MÁS COSTOSO QUE JUEGA EN CADA PAÍS ANFITRIÓN
------------------------------------------------------------
SELECT pais_anfitrion,
       equipo,
       valor_total
FROM (
         SELECT pais_anfitrion,
                equipo,
                valor_total,
                ROW_NUMBER() OVER (
               PARTITION BY pais_anfitrion
               ORDER BY valor_total DESC
           ) AS posicion
         FROM (
                  SELECT pa.nombre AS pais_anfitrion,
                         eq.nombre AS equipo,
                         SUM(j.valor) AS valor_total
                  FROM (
                           SELECT p.id_partido,
                                  p.id_equipo_local AS id_equipo,
                                  p.id_estadio
                           FROM partido p

                           UNION

                           SELECT p.id_partido,
                                  p.id_equipo_visitante AS id_equipo,
                                  p.id_estadio
                           FROM partido p
                       ) equipos_partido
                           INNER JOIN equipo eq ON equipos_partido.id_equipo = eq.id_equipo
                           INNER JOIN jugador j ON eq.id_equipo = j.id_equipo
                           INNER JOIN estadio es ON equipos_partido.id_estadio = es.id_estadio
                           INNER JOIN ciudad ci ON es.id_ciudad = ci.id_ciudad
                           INNER JOIN pais pa ON ci.id_pais = pa.id_pais
                  WHERE pa.es_anfitrion = 'S'
                  GROUP BY pa.nombre, eq.nombre
              )
     )
WHERE posicion = 1
ORDER BY pais_anfitrion;

------------------------------------------------------------
-- 5. PAÍSES QUE JUGARÁN EN CADA PAÍS ANFITRIÓN
------------------------------------------------------------
SELECT DISTINCT pais_sede.nombre AS pais_anfitrion,
                pais_equipo.nombre AS pais_que_juega
FROM partido p
         INNER JOIN estadio es ON p.id_estadio = es.id_estadio
         INNER JOIN ciudad ci ON es.id_ciudad = ci.id_ciudad
         INNER JOIN pais pais_sede ON ci.id_pais = pais_sede.id_pais
         INNER JOIN (
    SELECT p1.id_partido,
           eq1.id_pais
    FROM partido p1
             INNER JOIN equipo eq1 ON p1.id_equipo_local = eq1.id_equipo

    UNION

    SELECT p2.id_partido,
           eq2.id_pais
    FROM partido p2
             INNER JOIN equipo eq2 ON p2.id_equipo_visitante = eq2.id_equipo
) paises_partido ON p.id_partido = paises_partido.id_partido
         INNER JOIN pais pais_equipo ON paises_partido.id_pais = pais_equipo.id_pais
WHERE pais_sede.es_anfitrion = 'S'
ORDER BY pais_sede.nombre, pais_equipo.nombre;

------------------------------------------------------------
-- 6. CANTIDAD DE JUGADORES MENORES DE 21 AÑOS POR EQUIPO
------------------------------------------------------------
SELECT e.nombre AS equipo,
       COUNT(j.id_jugador) AS cantidad_menores_21
FROM equipo e
         LEFT JOIN jugador j ON e.id_equipo = j.id_equipo
    AND MONTHS_BETWEEN(SYSDATE, j.fecha_nacimiento) / 12 < 21
GROUP BY e.nombre
ORDER BY e.nombre;

------------------------------------------------------------
-- 7. JUGADORES FILTRADOS POR PESO, ESTATURA Y EQUIPO
--    EJEMPLO: BRASIL
------------------------------------------------------------
SELECT j.nombre AS jugador,
       e.nombre AS equipo,
       j.peso,
       j.estatura,
       j.valor
FROM jugador j
         INNER JOIN equipo e ON j.id_equipo = e.id_equipo
WHERE j.peso BETWEEN 60 AND 80
  AND j.estatura BETWEEN 1.70 AND 1.85
  AND e.nombre = 'Brasil'
ORDER BY j.nombre;

------------------------------------------------------------
-- 8. VALOR TOTAL DE JUGADORES POR EQUIPO Y CONFEDERACIÓN
------------------------------------------------------------
SELECT c.nombre AS confederacion,
       e.nombre AS equipo,
       SUM(j.valor) AS valor_total_jugadores
FROM jugador j
         INNER JOIN equipo e ON j.id_equipo = e.id_equipo
         INNER JOIN pais p ON e.id_pais = p.id_pais
         INNER JOIN confederacion c ON p.id_confederacion = c.id_confederacion
GROUP BY c.nombre, e.nombre
ORDER BY c.nombre, valor_total_jugadores DESC;

------------------------------------------------------------
-- 9. PARTIDOS POR PAÍS ANFITRIÓN
------------------------------------------------------------
SELECT pa.nombre AS pais_anfitrion,
       ci.nombre AS ciudad,
       es.nombre AS estadio,
       p.fecha_partido AS fecha,
       p.hora_partido AS hora,
       el.nombre AS equipo_local,
       ev.nombre AS equipo_visitante,
       g.nombre AS grupo,
       p.estado AS estado
FROM partido p
         INNER JOIN estadio es ON p.id_estadio = es.id_estadio
         INNER JOIN ciudad ci ON es.id_ciudad = ci.id_ciudad
         INNER JOIN pais pa ON ci.id_pais = pa.id_pais
         INNER JOIN equipo el ON p.id_equipo_local = el.id_equipo
         INNER JOIN equipo ev ON p.id_equipo_visitante = ev.id_equipo
         INNER JOIN grupo_mundial g ON p.id_grupo = g.id_grupo
WHERE pa.es_anfitrion = 'S'
ORDER BY pa.nombre, ci.nombre, es.nombre, p.fecha_partido, p.hora_partido;

------------------------------------------------------------
-- 10. CANTIDAD DE PARTIDOS POR PAÍS ANFITRIÓN
------------------------------------------------------------
SELECT pa.nombre AS pais_anfitrion,
       COUNT(p.id_partido) AS cantidad_partidos
FROM pais pa
         INNER JOIN ciudad ci ON pa.id_pais = ci.id_pais
         INNER JOIN estadio es ON ci.id_ciudad = es.id_ciudad
         LEFT JOIN partido p ON es.id_estadio = p.id_estadio
WHERE pa.es_anfitrion = 'S'
GROUP BY pa.nombre
ORDER BY pa.nombre;