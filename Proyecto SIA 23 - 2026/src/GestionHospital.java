import java.util.HashMap;
import java.util.Map;

public class GestionHospital {
    private Map<String, Area> mapaAreas;

    public GestionHospital() {
        this.mapaAreas = new HashMap<>();
    }

    public void agregarArea(String nombreArea) {
        if (!mapaAreas.containsKey(nombreArea)) {
            mapaAreas.put(nombreArea, new Area(nombreArea));
        }
    }

    public Area buscarArea(String nombreArea) {
        return mapaAreas.get(nombreArea);
    }

    public Map<String, Area> getMapaAreas() {
        return mapaAreas;
    }

    public Enfermera buscarEnfermeraGlobal(String rut) {
        for (Area area : mapaAreas.values()) {
            for (Enfermera enfermera : area.getListaEnfermeras()) {
                if (enfermera.getRut().equalsIgnoreCase(rut)) {
                    return enfermera;
                }
            }
        }
        return null;
    }

    public boolean eliminarArea(String nombreArea) {
        return mapaAreas.remove(nombreArea) != null;
    }
}