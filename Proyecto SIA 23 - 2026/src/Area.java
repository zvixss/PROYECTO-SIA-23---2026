import java.util.ArrayList;
import java.util.List;

public class Area {

    private String nombre;
    private List<Enfermera> listaEnfermeras;

    public Area(String nombre) {
        this.nombre = nombre;
        this.listaEnfermeras = new ArrayList<>();
    }

    public String getNombre() { return nombre; }
    public void setNombre(String n) { nombre = n; }
    public List<Enfermera> getListaEnfermeras() { return listaEnfermeras; }

    public void agregarEnfermera(Enfermera e) {
        listaEnfermeras.add(e);
    }

    public void agregarEnfermera(String rut, String nombre, String especialidad) {
        Enfermera nuevaEnfermera = new Enfermera(rut, nombre, especialidad);
        listaEnfermeras.add(nuevaEnfermera);
    }

    @Override
    public String toString() {
        return "Area: " + getNombre() + " (Enfermeras registradas: " + listaEnfermeras.size() + ")";
    }
}
