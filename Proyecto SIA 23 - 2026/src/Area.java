import java.util.ArrayList;
import java.util.List;

public class Area {
    private String nombreArea;
    private List<Enfermera> listaEnfermeras;

    public Area(String nombreArea) {
        this.nombreArea = nombreArea;
        this.listaEnfermeras = new ArrayList<>();
    }

    public String getNombreArea() {
        return nombreArea;
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }

    public List<Enfermera> getListaEnfermeras() {
        return listaEnfermeras;
    }

    public void agregarEnfermera(Enfermera enfermera) {
        listaEnfermeras.add(enfermera);
    }

    public void agregarEnfermera(String rut, String nombre, String especialidad) {
        Enfermera nuevaEnfermera = new Enfermera(rut, nombre, especialidad);
        listaEnfermeras.add(nuevaEnfermera);
    }
}