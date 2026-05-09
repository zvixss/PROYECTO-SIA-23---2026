import java.util.Scanner;

public class MainMenu {
    private GestionHospital sistema;
    private static Scanner sc = new Scanner(System.in);

    public MainMenu(GestionHospital sistema) {
        this.sistema = sistema;
    }

    public void ejecutar() {
        int opcion = -1;
        do {
            System.out.println("\n=== SISTEMA DE GESTION HOSPITALARIA ===");
            System.out.println("1. Gestionar Areas");
            System.out.println("2. Gestionar Enfermeras");
            System.out.println("3. Ver Reporte General de Personal");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());
                switch (opcion) {
                    case 1: menuAreas(); break;
                    case 2: menuEnfermeras(); break;
                    case 3: mostrarReporteGeneral(); break;
                    case 0: GestorArchivos.guardarDatos(sistema); break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un numero entero.");
            }
        } while (opcion != 0);
    }

    private void mostrarReporteGeneral() {
        System.out.println("\n=== REPORTE GENERAL DE PERSONAL POR AREA ===");
        for (Area area : sistema.getMapaAreas().values()) {
            System.out.println("\nArea: " + area.getNombre());
            if (area.getListaEnfermeras().isEmpty()) {
                System.out.println("  [Sin enfermeras registradas]");
            } else {
                for (Enfermera enfermera : area.getListaEnfermeras()) {
                    System.out.println("  - " + enfermera);
                }
            }
        }
    }

    private void menuAreas() {
        int opcionArea = -1;
        do {
            System.out.println("\n--- MENU GESTION DE AREAS ---");
            System.out.println("1. Agregar Area");
            System.out.println("2. Mostrar Listado");
            System.out.println("3. Buscar Area");
            System.out.println("4. Editar Nombre");
            System.out.println("5. Eliminar Area");
            System.out.println("0. Volver");
            try {
                opcionArea = Integer.parseInt(sc.nextLine());
                switch (opcionArea) {
                    case 1:
                        System.out.print("Nombre de la nueva area: ");
                        sistema.agregarArea(sc.nextLine());
                        break;
                    case 2:
                        for (String nombre : sistema.getMapaAreas().keySet()) {
                            System.out.println("- " + nombre);
                        }
                        break;
                    case 3:
                        System.out.print("Nombre del area a buscar: ");
                        Area a = sistema.buscarArea(sc.nextLine(), false);
                        if (a != null) System.out.println("Area encontrada: " + a.getNombre());
                        else System.out.println("Area no existe.");
                        break;
                    case 4:
                        System.out.print("Nombre actual: ");
                        String act = sc.nextLine();
                        if (sistema.buscarArea(act, false) != null) {
                            System.out.print("Nuevo nombre: ");
                            sistema.actualizarNombreArea(act, sc.nextLine());
                            System.out.println("Area actualizada.");
                        } else System.out.println("Area no encontrada.");
                        break;
                    case 5:
                        System.out.print("Nombre del area a eliminar: ");
                        if (sistema.eliminarArea(sc.nextLine())) System.out.println("Area eliminada.");
                        else System.out.println("No se pudo eliminar.");
                        break;
                }
            } catch (Exception e) { System.out.println("Error en la operacion."); }
        } while (opcionArea != 0);
    }

    private void menuEnfermeras() {
        int opcionEnf = -1;
        do {
            System.out.println("\n--- MENU GESTION DE ENFERMERAS ---");
            System.out.println("1. Registrar Enfermera");
            System.out.println("2. Listar por Area");
            System.out.println("3. Buscar por RUT");
            System.out.println("4. Editar Datos");
            System.out.println("5. Eliminar por RUT");
            System.out.println("6. Gestionar Turnos");
            System.out.println("0. Volver");
            try {
                opcionEnf = Integer.parseInt(sc.nextLine());
                switch (opcionEnf) {
                    case 1: registrarEnfermera(); break;
                    case 2: listarEnfermeras(); break;
                    case 3: buscarEnfermera(); break;
                    case 4: editarEnfermera(); break;
                    case 5: eliminarEnfermera(); break;
                    case 6: menuGestionTurnos(); break;
                }
            } catch (Exception e) { System.out.println("Error en la operacion."); }
        } while (opcionEnf != 0);
    }

    private void registrarEnfermera() {
        System.out.print("Nombre del area: ");
        Area area = sistema.buscarArea(sc.nextLine(), false);
        if (area == null) {
            System.out.println("Error: El area no existe.");
            return;
        }
        System.out.print("RUT (formato 12345678-9): ");
        String rut = sc.nextLine();
        try {
            Validador.validarRut(rut);
            System.out.print("Nombre completo: ");
            String nom = sc.nextLine();
            System.out.print("Especialidad: ");
            area.agregarEnfermera(rut, nom, sc.nextLine());
            System.out.println("Enfermera registrada exitosamente.");
        } catch (RutInvalidoException e) {
            System.out.println(e.getMessage());
        }
    }

    private void listarEnfermeras() {
        System.out.print("Nombre del area: ");
        Area area = sistema.buscarArea(sc.nextLine(), false);
        if (area != null) {
            for (Enfermera e : area.getListaEnfermeras()) System.out.println(e);
        } else System.out.println("Area no encontrada.");
    }

    private void buscarEnfermera() {
        System.out.print("RUT a buscar: ");
        try {
            System.out.println(sistema.buscarEnfermera(sc.nextLine()));
        } catch (EnfermeraNoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }

    private void editarEnfermera() {
        System.out.print("RUT (formato 12345678-9): ");
        try {
            Enfermera e = sistema.buscarEnfermera(sc.nextLine());
            System.out.print("Nuevo nombre: "); e.setNombre(sc.nextLine());
            System.out.print("Nueva especialidad: "); e.setEspecialidad(sc.nextLine());
            System.out.println("Datos actualizados.");
        } catch (EnfermeraNoEncontradaException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void eliminarEnfermera() {
        System.out.print("RUT a eliminar: ");
        if (sistema.eliminarEnfermeraGlobal(sc.nextLine())) System.out.println("Personal eliminado.");
        else System.out.println("RUT no encontrado.");
    }

    private void menuGestionTurnos() {
        System.out.println("\n--- GESTION DE TURNOS ---");
        System.out.println("1. Asignar Turno\n2. Quitar Turno\n3. Ver Listado");
        try {
            int op = Integer.parseInt(sc.nextLine());
            System.out.print("RUT de enfermera: ");
            Enfermera e = sistema.buscarEnfermera(sc.nextLine());
            if (op == 1) {
                System.out.print("Nuevo Turno: ");
                e.agregarTurno(sc.nextLine());
                System.out.println("Turno asignado.");
            } else if (op == 2) {
                System.out.print("Nombre del turno a quitar: ");
                e.eliminarTurno(sc.nextLine());
                System.out.println("Turno eliminado.");
            } else if (op == 3) {
                System.out.println("Turnos: " + e.getListaTurnos());
            }
        } catch (Exception e) { System.out.println("Error en la gestion de turnos."); }
    }
}