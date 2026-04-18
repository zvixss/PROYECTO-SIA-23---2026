import java.io.*;
import java.util.Arrays;

public class GestorArchivos {
    private static final String RUTA_ARCHIVO = "datos_hospital.csv";

    public static void guardarDatos(GestionHospital sistema) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_ARCHIVO))) {
            for (Area area : sistema.getMapaAreas().values()) {
                if (area.getListaEnfermeras().isEmpty()) {
                    writer.write(area.getNombre() + ",VACIO,VACIO,VACIO,SIN_TURNOS");
                    writer.newLine();
                } else {
                    for (Enfermera e : area.getListaEnfermeras()) {
                        String turnosStr = String.join(";", e.getListaTurnos());
                        if (turnosStr.isEmpty()) turnosStr = "SIN_TURNOS";

                        writer.write(area.getNombre() + "," +
                                e.getRut() + "," +
                                e.getNombre() + "," +
                                e.getEspecialidad() + "," +
                                turnosStr);
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al guardar: " + e.getMessage());
        }
    }

    public static void cargarDatos(GestionHospital sistema) {
        File archivo = new File(RUTA_ARCHIVO);
        if (!archivo.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 4) {
                    String nombreArea = partes[0];
                    sistema.agregarArea(nombreArea);

                    if (!partes[1].equals("VACIO")) {
                        Area areaActual = sistema.buscarArea(nombreArea);
                        Enfermera nueva = new Enfermera(partes[1], partes[2], partes[3]);

                        if (partes.length == 5 && !partes[4].equals("SIN_TURNOS")) {
                            String[] turnos = partes[4].split(";");
                            for (String t : turnos) {
                                nueva.agregarTurno(t);
                            }
                        }
                        areaActual.getListaEnfermeras().add(nueva);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar: " + e.getMessage());
        }
    }
}