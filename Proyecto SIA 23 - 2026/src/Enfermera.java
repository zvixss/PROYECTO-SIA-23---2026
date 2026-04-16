import java.util.ArrayList;
import java.util.List;

public class Enfermera {
    private String rut;
    private String nombre;
    private String especialidad;
    private List<String> listaTurnos;

    public Enfermera(String rut, String nombre, String especialidad) {
        this.rut = rut;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.listaTurnos = new ArrayList<>();
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public List<String> getListaTurnos() {
        return listaTurnos;
    }

    public void eliminarTurno(String turno) {
        this.listaTurnos.remove(turno);
    }

    public boolean estaDisponible(String turno) {
        // Regla de disponibilidad: no puede tener dos turnos el mismo día/bloque
        return !this.listaTurnos.contains(turno);
    }

    public void agregarTurno(String turno) {
        this.listaTurnos.add(turno);
    }

    @Override
    public String toString() {
        return "RUT: " + rut + " | Nombre: " + nombre + " | Especialidad: " + especialidad + " | Turnos: " + listaTurnos;
    }
}