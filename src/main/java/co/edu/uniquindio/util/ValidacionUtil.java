package co.edu.uniquindio.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidacionUtil {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");

    private ValidacionUtil() {
    }

    public static boolean estaVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    public static boolean esDecimalValido(String texto) {
        if (estaVacio(texto)) {
            return false;
        }

        try {
            Double.parseDouble(texto.trim().replace(",", "."));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean esEnteroValido(String texto) {
        if (estaVacio(texto)) {
            return false;
        }

        try {
            Integer.parseInt(texto.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static double convertirDecimal(String texto) {
        return Double.parseDouble(texto.trim().replace(",", "."));
    }

    public static int convertirEntero(String texto) {
        return Integer.parseInt(texto.trim());
    }

    public static boolean esFechaValida(String texto) {
        if (estaVacio(texto)) {
            return false;
        }

        try {
            LocalDate.parse(texto.trim(), FORMATO_FECHA);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static LocalDate convertirFecha(String texto) {
        return LocalDate.parse(texto.trim(), FORMATO_FECHA);
    }

    public static boolean fechaNoFutura(LocalDate fecha) {
        return fecha != null && !fecha.isAfter(LocalDate.now());
    }

    public static boolean esValorPositivo(double valor) {
        return valor > 0;
    }

    public static boolean esValorMayorOIgualCero(double valor) {
        return valor >= 0;
    }

    public static boolean esGrupoValido(String grupo) {
        if (estaVacio(grupo)) {
            return false;
        }

        String valor = grupo.trim().toUpperCase();

        return valor.matches("[A-L]");
    }

    // ------------------------------------------------------------
    // VALIDACIONES NUEVAS PARA LA PARTE DE PAÍSES, CIUDADES,
    // ESTADIOS Y PARTIDOS
    // ------------------------------------------------------------

    public static boolean esHoraValida(String texto) {
        if (estaVacio(texto)) {
            return false;
        }

        try {
            LocalTime.parse(texto.trim(), FORMATO_HORA);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static LocalTime convertirHora(String texto) {
        return LocalTime.parse(texto.trim(), FORMATO_HORA);
    }

    public static boolean fechaNoPasada(LocalDate fecha) {
        return fecha != null && !fecha.isBefore(LocalDate.now());
    }

    public static boolean esCapacidadValida(int capacidad) {
        return capacidad > 0;
    }

    public static boolean esCapacidadEstadioRealista(int capacidad) {
        return capacidad >= 10000 && capacidad <= 120000;
    }

    public static boolean esGolesValido(int goles) {
        return goles >= 0;
    }

    public static boolean equiposDiferentes(int idEquipoLocal, int idEquipoVisitante) {
        return idEquipoLocal != idEquipoVisitante;
    }

    public static boolean esEstadoPartidoValido(String estado) {
        if (estaVacio(estado)) {
            return false;
        }

        String valor = estado.trim().toUpperCase();

        return valor.equals("PROGRAMADO")
                || valor.equals("EN JUEGO")
                || valor.equals("FINALIZADO")
                || valor.equals("CANCELADO");
    }

    public static boolean esAnfitrionValido(String valor) {
        if (estaVacio(valor)) {
            return false;
        }

        String texto = valor.trim().toUpperCase();

        return texto.equals("S") || texto.equals("N");
    }

    public static boolean longitudMaxima(String texto, int maximo) {
        if (texto == null) {
            return false;
        }

        return texto.trim().length() <= maximo;
    }
}