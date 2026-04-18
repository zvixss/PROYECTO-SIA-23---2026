import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GestionHospital sistema = new GestionHospital();
        GestorArchivos.cargarDatos(sistema);

        Scanner sc = new Scanner(System.in);
        System.out.println("=== SISTEMA DE GESTION HOSPITALARIA ===");
        System.out.println("1. Modo Consola");
        System.out.println("2. Modo Ventanas (GUI)");
        System.out.print("Seleccione una opcion: ");

        String seleccion = sc.nextLine();

        if (seleccion.equals("1")) {
            MainMenu menuConsola = new MainMenu(sistema);
            menuConsola.ejecutar();
        } else if (seleccion.equals("2")) {
            MainVentana ventana = new MainVentana(sistema);
            ventana.setVisible(true);
        } else {
            GestorArchivos.guardarDatos(sistema);
        }
    }
}