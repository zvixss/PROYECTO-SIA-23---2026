import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        GestionHospital sistema = new GestionHospital();

        // SIA-11: Carga Batch al iniciar
        GestorArchivos.cargarDatos(sistema);

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
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un numero entero.");
                continue;
            }

            switch (opcion) {
                case 1:
                    menuAreas(sistema);
                    break;
                case 2:
                    menuEnfermeras(sistema);
                    break;
                case 3:
                    mostrarReporteGeneral(sistema);
                    break;
                case 0:
                    System.out.println("Guardando informacion...");
                    // SIA-11: Grabado Batch al salir
                    GestorArchivos.guardarDatos(sistema);
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        } while (opcion != 0);
    }

    private static void mostrarReporteGeneral(GestionHospital sistema) {
        System.out.println("\n=== REPORTE GENERAL DE PERSONAL POR AREA ===");
        if (sistema.getMapaAreas().isEmpty()) {
            System.out.println("No hay areas registradas en el sistema.");
            return;
        }

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

    private static void menuAreas(GestionHospital sistema) {
        int opcionArea = -1; // Renombrada para evitar confusión visual
        do {
            System.out.println("\n--- MENU GESTION DE AREAS ---");
            System.out.println("1. Agregar Area");
            System.out.println("2. Mostrar Listado de Areas");
            System.out.println("3. Buscar Area");
            System.out.println("4. Editar Nombre de Area");
            System.out.println("5. Eliminar Area");
            System.out.println("0. Volver al Menu Principal");
            System.out.print("Seleccione: ");

            try {
                opcionArea = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un numero.");
                continue;
            }

            switch (opcionArea) {
                case 1:
                    System.out.print("Ingrese el nombre de la nueva area: ");
                    sistema.agregarArea(sc.nextLine());
                    break;
                case 2:
                    System.out.println("Listado de Areas:");
                    for (String nombre : sistema.getMapaAreas().keySet()) {
                        System.out.println("- " + nombre);
                    }
                    break;
                case 3:
                    System.out.print("Nombre del area a buscar: ");
                    Area areaBuscada = sistema.buscarArea(sc.nextLine());
                    if (areaBuscada != null) {
                        System.out.println("Area encontrada: " + areaBuscada.getNombre());
                    } else {
                        System.out.println("Area no existe.");
                    }
                    break;
                case 4:
                    System.out.print("Nombre actual del area: ");
                    String nombreActual = sc.nextLine();
                    Area areaEditar = sistema.buscarArea(nombreActual);
                    if (areaEditar != null) {
                        System.out.print("Nuevo nombre: ");
                        String nuevoNombre = sc.nextLine();
                        sistema.getMapaAreas().remove(nombreActual);
                        areaEditar.setNombre(nuevoNombre);
                        sistema.getMapaAreas().put(nuevoNombre, areaEditar);
                        System.out.println("Area actualizada.");
                    } else {
                        System.out.println("Area no encontrada.");
                    }
                    break;
                case 5:
                    System.out.print("Nombre del area a eliminar: ");
                    if (sistema.eliminarArea(sc.nextLine())) {
                        System.out.println("Area eliminada con exito.");
                    } else {
                        System.out.println("No se pudo eliminar el area.");
                    }
                    break;
            }
        } while (opcionArea != 0);
    }

    private static void menuEnfermeras(GestionHospital sistema) {
        int opcionEnf = -1; // Renombrada para evitar confusión visual
        do {
            System.out.println("\n--- MENU GESTION DE ENFERMERAS ---");
            System.out.println("1. Registrar Enfermera");
            System.out.println("2. Mostrar Listado por Area");
            System.out.println("3. Buscar Enfermera por RUT");
            System.out.println("4. Editar Datos de Enfermera");
            System.out.println("5. Eliminar Enfermera por RUT");
            System.out.println("0. Volver al Menu Principal");
            System.out.print("Seleccione: ");

            try {
                opcionEnf = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un numero.");
                continue;
            }

            switch (opcionEnf) {
                case 1:
                    registrarEnfermera(sistema);
                    break;
                case 2:
                    listarEnfermeras(sistema);
                    break;
                case 3:
                    buscarEnfermera(sistema);
                    break;
                case 4:
                    editarEnfermera(sistema);
                    break;
                case 5:
                    eliminarEnfermera(sistema);
                    break;
            }
        } while (opcionEnf != 0);
    }

    private static void registrarEnfermera(GestionHospital sistema) {
        System.out.print("Ingrese nombre del area para la enfermera: ");
        Area area = sistema.buscarArea(sc.nextLine());
        if (area == null) {
            System.out.println("Error: El area no existe.");
            return;
        }

        System.out.print("RUT: ");
        String rut = sc.nextLine();

        // SIA-12: Manejo de excepciones personalizadas
        try {
            if (!Validador.validarRut(rut)) {
                throw new RutInvalidoException("El RUT ingresado no es valido.");
            }

            // Verificamos duplicados usando la excepcion
            try {
                sistema.buscarEnfermera(rut);
                System.out.println("Error: El RUT ya esta registrado.");
                return;
            } catch (EnfermeraNoEncontradaException e) {
                // Si no se encuentra, procedemos con el registro
            }

            System.out.print("Nombre: ");
            String nombre = sc.nextLine();
            System.out.print("Especialidad: ");
            String especialidad = sc.nextLine();

            area.agregarEnfermera(rut, nombre, especialidad);
            System.out.println("Enfermera registrada.");

        } catch (RutInvalidoException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private static void listarEnfermeras(GestionHospital sistema) {
        System.out.print("Ingrese nombre del area: ");
        Area area = sistema.buscarArea(sc.nextLine());
        if (area != null) {
            System.out.println("Enfermeras en " + area.getNombre() + ":");
            for (Enfermera e : area.getListaEnfermeras()) {
                System.out.println(e);
            }
        } else {
            System.out.println("Area no encontrada.");
        }
    }

    private static void buscarEnfermera(GestionHospital sistema) {
        System.out.print("Ingrese RUT a buscar: ");
        String rut = sc.nextLine();

        // SIA-12: Captura de excepcion
        try {
            Enfermera e = sistema.buscarEnfermera(rut);
            System.out.println("Enfermera encontrada: " + e);
        } catch (EnfermeraNoEncontradaException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void editarEnfermera(GestionHospital sistema) {
        System.out.print("Ingrese RUT de la enfermera a editar: ");
        String rut = sc.nextLine();

        // SIA-12: Captura de excepcion para edicion
        try {
            Enfermera e = sistema.buscarEnfermera(rut);
            System.out.print("Nuevo nombre (actual: " + e.getNombre() + "): ");
            e.setNombre(sc.nextLine());
            System.out.print("Nueva especialidad (actual: " + e.getEspecialidad() + "): ");
            e.setEspecialidad(sc.nextLine());
            System.out.println("Datos actualizados correctamente.");
        } catch (EnfermeraNoEncontradaException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void eliminarEnfermera(GestionHospital sistema) {
        System.out.print("Ingrese RUT a eliminar: ");
        String rut = sc.nextLine();
        boolean eliminado = false;
        for (Area area : sistema.getMapaAreas().values()) {
            eliminado = area.getListaEnfermeras().removeIf(e -> e.getRut().equalsIgnoreCase(rut));
            if (eliminado) break;
        }
        if (eliminado) System.out.println("Personal eliminado.");
        else System.out.println("No se encontro el RUT.");
    }
}