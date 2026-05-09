public class Validador {

    public static void validarRut(String rut) throws RutInvalidoException {
        if (rut == null || !rut.matches("\\d{7,8}-[\\dkK]")) {
            throw new RutInvalidoException("Error: El formato del RUT es incorrecto (debe ser 12345678-9).");
        }

        String[] partes = rut.split("-");
        int num = Integer.parseInt(partes[0]);
        char dv = partes[1].toUpperCase().charAt(0);

        int m = 0, s = 1;
        for (; num != 0; num /= 10) {
            s = (s + num % 10 * (9 - m++ % 6)) % 11;
        }
        char dvCalc = (char) (s != 0 ? s + 47 : 75);

        if (dv != dvCalc) {
            throw new RutInvalidoException("Error: El digito verificador del RUT es invalido.");
        }
    }

    public static boolean esTextoValido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }
}