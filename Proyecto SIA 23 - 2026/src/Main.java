import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        GestionHospital sistema = new GestionHospital();

        int opcion = -1;

        inicializarDatos(sistema);

        do {

            imprimirMenu();

            try {

                System.out.print("Seleccione una opcion: ");
                opcion = Integer.parseInt(sc.nextLine());

            } catch (NumberFormatException e) {

                System.out.println("Error: Debe ingresar un numero entero.");
                continue;

            }

            switch (opcion) {

                case 1:
                    registrarEnfermera(sistema);
                    break;
                case 2:
                    mostrarEnfermerasPorArea(sistema);
                    break;
                case 3:
                    eliminarEnfermera(sistema);
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion no valida. Intente nuevamente.");

            }
        } while (opcion != 0);

        sc.close();
    }

    private static void imprimirMenu() {

        System.out.println("\n-------------------------------------------");
        System.out.println("      SISTEMA DE GESTION HOSPITALARIA      ");
        System.out.println("-------------------------------------------");
        System.out.println("1. Registrar nueva enfermera");
        System.out.println("2. Listar enfermeras por area");
        System.out.println("3. Eliminar enfermera por RUT");
        System.out.println("0. Salir");
        System.out.println("-------------------------------------------");

    }

    private static void registrarEnfermera(GestionHospital sistema) {

        System.out.print("Ingrese el nombre del area: ");
        String nombreArea = sc.nextLine();

        if (!Validador.esTextoValido(nombreArea)) {

            System.out.println("Error: El nombre del area no puede estar vacio.");
            return;
        }

        sistema.crearArea(nombreArea);
        Area areaActual = sistema.getArea(nombreArea);

        System.out.print("Ingrese RUT de la enfermera (ej: 12345678-9 o 12.345.678-9): ");
        String rut = sc.nextLine();

        if (!Validador.validarRut(rut)) {

            System.out.println("Error: El RUT ingresado no es valido.");
            return;
        }

        if (sistema.existeEnfermera(rut)) {

            System.out.println("Error: Ya existe personal registrado con ese RUT.");
            return;
        }

        System.out.print("Ingrese nombre completo: ");
        String nombreEnf = sc.nextLine();

        System.out.print("Ingrese especialidad medica: ");
        String especialidad = sc.nextLine();

        if (Validador.esTextoValido(nombreEnf) && Validador.esTextoValido(especialidad)) {

            Enfermera nueva = new Enfermera(rut, nombreEnf, especialidad);
            areaActual.agregarEnfermera(nueva);
            System.out.println("Registro completado con exito.");

        } else {

            System.out.println("Error: Los campos de texto no pueden estar vacios.");
        }
    }

    private static void mostrarEnfermerasPorArea(GestionHospital sistema) {

        System.out.print("Ingrese el nombre del area a consultar: ");
        String nombreArea = sc.nextLine();
        Area area = sistema.getArea(nombreArea);

        if (area != null) {

            System.out.println("\n--- Listado de Personal en " + nombreArea + " ---");

            if (area.getListaEnfermeras().isEmpty()) {

                System.out.println("No hay enfermeras registradas en esta unidad.");

            } else {

                for (Enfermera e : area.getListaEnfermeras()) {

                    System.out.println("RUT: " + e.getRut() + " | Nombre: " + e.getNombre() +
                            " | Especialidad: " + e.getEspecialidad());

                }
            }
        } else {

            System.out.println("Error: El area indicada no existe en el sistema.");

        }
    }

    private static void eliminarEnfermera(GestionHospital sistema) {

        System.out.print("Ingrese el RUT de la enfermera a eliminar: ");
        String rut = sc.nextLine();
        boolean eliminado = false;

        for (Area area : sistema.getMapaAreas().values()) {

            for (int i = 0; i < area.getListaEnfermeras().size(); i++) {

                if (area.getListaEnfermeras().get(i).getRut().equals(rut)) {

                    area.getListaEnfermeras().remove(i);
                    eliminado = true;
                    break;

                }
            }

            if (eliminado) break;

        }

        if (eliminado) {

            System.out.println("Personal eliminado correctamente.");

        } else {

            System.out.println("Error: No se encontro a nadie con ese RUT.");

        }
    }

    private static void inicializarDatos(GestionHospital sistema) {

        sistema.crearArea("Urgencias");
        sistema.crearArea("Pediatria");
        Area urg = sistema.getArea("Urgencias");
        urg.agregarEnfermera(new Enfermera("12.345.678-9", "Ejemplo Nombre", "Generalista"));
    }
}