import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainVentana extends JFrame {
    private GestionHospital sistema;

    public MainVentana(GestionHospital sistema) {
        this.sistema = sistema;
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("SIA Hospital - GUI");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                GestorArchivos.guardarDatos(sistema);
                System.exit(0);
            }
        });
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton btnArea = new JButton("Gestionar Areas");
        JButton btnEnf = new JButton("Gestionar Enfermeras");
        JButton btnTurnos = new JButton("Gestionar Turnos");
        JButton btnReporte = new JButton("Reporte General");
        JButton btnSalir = new JButton("Salir");

        btnArea.addActionListener(e -> menuAreasGui());
        btnEnf.addActionListener(e -> menuEnfermerasGui());
        btnTurnos.addActionListener(e -> menuTurnosGui());

        btnReporte.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("=== REPORTE GENERAL ===\n");
            for (Area a : sistema.getMapaAreas().values()) {
                sb.append("\nArea: ").append(a.getNombre()).append("\n");
                for (Enfermera en : a.getListaEnfermeras()) sb.append("- ").append(en).append("\n");
            }
            JOptionPane.showMessageDialog(this, new JScrollPane(new JTextArea(sb.toString(), 15, 45)));
        });

        btnSalir.addActionListener(e -> {
            GestorArchivos.guardarDatos(sistema);
            System.exit(0);
        });

        panel.add(btnArea); panel.add(btnEnf); panel.add(btnTurnos);
        panel.add(btnReporte); panel.add(btnSalir);
        add(panel);
    }

    private void menuAreasGui() {
        String[] opciones = {"Agregar", "Buscar", "Eliminar", "Volver"};
        int sel = JOptionPane.showOptionDialog(this, "Gestion de Areas", "Opciones", 0, 1, null, opciones, opciones[0]);

        if (sel == 0) {
            String n = JOptionPane.showInputDialog(this, "Nombre del Area a agregar:");
            if (n != null && !n.trim().isEmpty()) {
                sistema.agregarArea(n);
                JOptionPane.showMessageDialog(this, "Area agregada.");
            }
        } else if (sel == 1) {
            String n = JOptionPane.showInputDialog(this, "Nombre del Area a buscar:");
            Area area = sistema.buscarArea(n, false);
            if (area != null) {
                JOptionPane.showMessageDialog(this, "Area encontrada: " + area.getNombre());
            } else {
                JOptionPane.showMessageDialog(this, "El area no existe.");
            }
        } else if (sel == 2) {
            String n = JOptionPane.showInputDialog(this, "Nombre del Area a eliminar:");
            if (sistema.eliminarArea(n)) {
                JOptionPane.showMessageDialog(this, "Area eliminada.");
            } else {
                JOptionPane.showMessageDialog(this, "No encontrada.");
            }
        }
    }

    private void menuEnfermerasGui() {
        String[] opciones = {"Registrar", "Buscar", "Eliminar", "Cancelar"};
        int sel = JOptionPane.showOptionDialog(this, "Enfermeras", "Opciones", 0, 1, null, opciones, opciones[0]);
        try {
            if (sel == 0) {
                String arStr = JOptionPane.showInputDialog(this, "Area destino:");
                Area area = sistema.buscarArea(arStr, false);
                if (area == null) { JOptionPane.showMessageDialog(this, "Area no existe."); return; }
                String r = JOptionPane.showInputDialog(this, "RUT:");
                Validador.validarRut(r);
                area.agregarEnfermera(r, JOptionPane.showInputDialog(this, "Nombre:"), JOptionPane.showInputDialog(this, "Especialidad:"));
                JOptionPane.showMessageDialog(this, "Registrada exitosamente.");
            } else if (sel == 1) {
                String r = JOptionPane.showInputDialog(this, "RUT a buscar:");
                JOptionPane.showMessageDialog(this, sistema.buscarEnfermera(r).toString());
            } else if (sel == 2) {
                String r = JOptionPane.showInputDialog(this, "RUT a eliminar:");
                if (sistema.eliminarEnfermeraGlobal(r)) JOptionPane.showMessageDialog(this, "Eliminada.");
                else JOptionPane.showMessageDialog(this, "No encontrada.");
            }
        } catch (RutInvalidoException | EnfermeraNoEncontradaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error inesperado.");
        }
    }

    private void menuTurnosGui() {
        String[] ops = {"Asignar Turno", "Ver Turnos", "Volver"};
        int sel = JOptionPane.showOptionDialog(this, "Gestion de Turnos", "Opciones", 0, 1, null, ops, ops[0]);
        try {
            String r = JOptionPane.showInputDialog(this, "RUT de enfermera:");
            if (r == null) return;
            Enfermera e = sistema.buscarEnfermera(r);
            if (sel == 0) {
                String t = JOptionPane.showInputDialog(this, "Nuevo Turno:");
                if (t != null) e.agregarTurno(t);
            } else if (sel == 1) {
                JOptionPane.showMessageDialog(this, "Turnos de " + e.getNombre() + ":\n" + e.getListaTurnos());
            }
        } catch (EnfermeraNoEncontradaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error.");
        }
    }
}