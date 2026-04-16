import java.util.HashMap;
import java.util.Map;

public class GestionHospital {

    private Map<String, Area> mapaAreas;

    public GestionHospital() {
        this.mapaAreas = new HashMap<>();
    }

    public void crearArea(String nombre) {
        if (!mapaAreas.containsKey(nombre)) {
            mapaAreas.put(nombre, new Area(nombre));
        }
    }

    public Area getArea(String nombre) {
        return mapaAreas.get(nombre);
    }

    public Map<String, Area> getMapaAreas () {
        return mapaAreas;
    }

    public boolean existeEnfermera(String rut) {

        for (Area area : mapaAreas.values()) {

            for (Enfermera e : area.getListaEnfermeras()) {

                if (e.getRut().equalsIgnoreCase(rut)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void agregarArea(String nombreArea) {
        // Validación de texto vacío (SIA-3 de buenas prácticas)
        if (nombreArea == null || nombreArea.trim().isEmpty()) {
            System.out.println("Error: El nombre del área no puede estar vacío.");
            return;
        }

        if (!mapaAreas.containsKey(nombreArea)) {
            mapaAreas.put(nombreArea, new Area(nombreArea));
        } else {
            System.out.println("El área '" + nombreArea + "' ya existe.");
        }
    }

    public Area buscarArea(String nombreArea) {
        // Retorna el objeto o null si no existe
        return mapaAreas.get(nombreArea);
    }
    public Enfermera buscarEnfermera(String rut) throws EnfermeraNoEncontradaException {
        for (Area area : mapaAreas.values()) {
            for (Enfermera enfermera : area.getListaEnfermeras()) {
                if (enfermera.getRut().equalsIgnoreCase(rut)) {
                    return enfermera;
                }
            }
        }
        throw new EnfermeraNoEncontradaException("Error: No se encontró personal con el RUT " + rut);
    }

    public boolean eliminarArea(String nombreArea) {
        return mapaAreas.remove(nombreArea) != null;
    }
}
