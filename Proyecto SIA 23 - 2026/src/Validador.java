public class Validador {
    public static boolean validarRut(String rut) {
        if (rut == null || rut.isEmpty()) return false;
        rut = rut.trim().replace(".", "").replace("-", "").toUpperCase();
        if (rut.length() < 8) return false;
        try {
            char dv = rut.charAt(rut.length() - 1);
            int cuerpo = Integer.parseInt(rut.substring(0, rut.length() - 1));
            int m = 0, s = 1;
            for (; cuerpo != 0; cuerpo /= 10) {
                s = (s + cuerpo % 10 * (9 - m++ % 6)) % 11;
            }
            char dvEsperado = (char) (s != 0 ? s + 47 : 75);
            return dv == dvEsperado;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean esTextoValido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }
}