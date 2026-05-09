import java.io.*;

public class GestorArchivos {
    private static final String RUTA_ARCHIVO = "datos_hospital.csv";

    public static void cargarDatos(GestionHospital sistema) {
        File archivo = new File(RUTA_ARCHIVO);
        if (!archivo.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length < 4) continue;

                String nombreArea = partes[0];
                String rut = partes[1];
                String nombreEnf = partes[2];
                String especialidad = partes[3];

                Area area = sistema.buscarArea(nombreArea, true);
                Enfermera nueva = new Enfermera(rut, nombreEnf, especialidad);

                if (partes.length > 4) {
                    for (int i = 4; i < partes.length; i++) {
                        nueva.agregarTurno(partes[i]);
                    }
                }
                area.agregarEnfermera(nueva);
            }
        } catch (IOException e) {
            System.err.println("Error al cargar: " + e.getMessage());
        }
    }

    public static void guardarDatos(GestionHospital sistema) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA_ARCHIVO))) {
            for (Area area : sistema.getMapaAreas().values()) {
                for (Enfermera e : area.getListaEnfermeras()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(area.getNombre()).append(",")
                            .append(e.getRut()).append(",")
                            .append(e.getNombre()).append(",")
                            .append(e.getEspecialidad());

                    for (String turno : e.getListaTurnos()) {
                        sb.append(",").append(turno);
                    }
                    pw.println(sb.toString());
                }
            }
        } catch (IOException e) {
            System.err.println("Error al guardar: " + e.getMessage());
        }
    }
}