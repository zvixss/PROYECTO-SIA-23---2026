import java.util.HashMap;
import java.util.Map;

public class GestionHospital {
    private Map<String, Area> mapaAreas;

    public GestionHospital() {
        this.mapaAreas = new HashMap<>();
    }

    public void agregarArea(String nombreArea) {
        if (nombreArea == null || nombreArea.trim().isEmpty()) {
            return;
        }
        if (!mapaAreas.containsKey(nombreArea)) {
            mapaAreas.put(nombreArea, new Area(nombreArea));
        }
    }

    public Area buscarArea(String nombreArea) {
        return mapaAreas.get(nombreArea);
    }

    public Area buscarArea(String nombreArea, boolean crearSiNoExiste) {
        if (!mapaAreas.containsKey(nombreArea) && crearSiNoExiste) {
            agregarArea(nombreArea);
        }
        return mapaAreas.get(nombreArea);
    }

    public Map<String, Area> getMapaAreas() {
        return mapaAreas;
    }

    public Enfermera buscarEnfermera(String rut) throws EnfermeraNoEncontradaException {
        for (Area area : mapaAreas.values()) {
            for (Enfermera enfermera : area.getListaEnfermeras()) {
                if (enfermera.getRut().equalsIgnoreCase(rut)) {
                    return enfermera;
                }
            }
        }
        throw new EnfermeraNoEncontradaException("Error: No se encontro personal con el RUT " + rut);
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

    public boolean eliminarArea(String nombreArea) {
        return mapaAreas.remove(nombreArea) != null;
    }
}