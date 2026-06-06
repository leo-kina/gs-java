package util;


public class Validador {


    public static boolean validar(String valor) {
        return valor != null && !valor.trim().isEmpty();
    }


    public static boolean validar(double valor, double min, double max) {
        return valor >= min && valor <= max;
    }


    public static boolean validar(int valor, int min, int max) {
        return valor >= min && valor <= max;
    }


    public static boolean validar(String valor, int minLen, int maxLen) {
        if (!validar(valor)) return false;
        int len = valor.trim().length();
        return len >= minLen && len <= maxLen;
    }


    public static String formatarId(String sigla, int numero) {
        return sigla.toUpperCase() + String.format("%03d", numero);
    }
}