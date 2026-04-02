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
    public List<Enfermera> getListaEnfermeras() { return listaEnfermeras; }

    public void agregarEnfermera(Enfermera e) {
        listaEnfermeras.add(e);
    }
}
