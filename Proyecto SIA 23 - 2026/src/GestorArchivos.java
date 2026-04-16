import java.io.*;

public class GestorArchivos {
    private static final String RUTA_ARCHIVO = "datos_hospital.csv";

    // Grabado Batch: Se llama al SALIR del sistema
    public static void guardarDatos(GestionHospital sistema) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_ARCHIVO))) {
            for (Area area : sistema.getMapaAreas().values()) {
                if (area.getListaEnfermeras().isEmpty()) {
                    // Guardamos el área aunque esté vacía
                    writer.write(area.getNombre() + ",VACIO,VACIO,VACIO");
                    writer.newLine();
                } else {
                    // Guardamos cada enfermera con su área respectiva
                    for (Enfermera e : area.getListaEnfermeras()) {
                        writer.write(area.getNombre() + "," + e.getRut() + "," + e.getNombre() + "," + e.getEspecialidad());
                        writer.newLine();
                    }
                }
            }
            System.out.println("-> Datos guardados exitosamente en CSV al salir.");
        } catch (IOException e) {
            System.err.println("Error al guardar los datos: " + e.getMessage());
        }
    }

    // Carga Batch: Se llama al INICIAR el sistema
    public static void cargarDatos(GestionHospital sistema) {
        File archivo = new File(RUTA_ARCHIVO);
        if (!archivo.exists()) {
            System.out.println("-> No hay archivo previo. Se iniciará un sistema en blanco.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 4) {
                    String nombreArea = partes[0];
                    sistema.agregarArea(nombreArea); // Agrega el área (el HashMap evita duplicados)

                    // Si no es un área vacía, agregamos la enfermera
                    if (!partes[1].equals("VACIO")) {
                        Area areaActual = sistema.buscarArea(nombreArea);
                        areaActual.agregarEnfermera(partes[1], partes[2], partes[3]);
                    }
                }
            }
            System.out.println("-> Datos cargados exitosamente desde CSV.");
        } catch (IOException e) {
            System.err.println("Error al cargar los datos: " + e.getMessage());
        }
    }
}