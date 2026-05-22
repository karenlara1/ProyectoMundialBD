package co.edu.uniquindio.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidacionUtil {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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
}