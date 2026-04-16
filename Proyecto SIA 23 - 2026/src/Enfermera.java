import java.util.ArrayList;
import java.util.List;

public class Enfermera {
    private String rut;
    private String nombre;
    private String especialidad;
    private List<String> turnos;

    public Enfermera(String rut, String nombre, String especialidad) {
        this.rut = rut;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.turnos = new ArrayList<>();
    }

    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public List<String> getTurnos() { return turnos; }

    public void agregarTurno(String turno) {
        this.turnos.add(turno);
    }

    @Override
    public String toString() {
        return "RUT: " + rut + " | Nombre: " + nombre + " | Especialidad: " + especialidad;
    }
}