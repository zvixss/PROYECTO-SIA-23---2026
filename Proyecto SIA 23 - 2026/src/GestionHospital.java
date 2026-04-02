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
}
