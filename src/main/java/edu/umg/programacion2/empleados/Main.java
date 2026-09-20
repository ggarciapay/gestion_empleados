package edu.umg.programacion2.empleados;

import javax.swing.SwingUtilities;
import edu.umg.programacion2.empleados.view.MenuPrincipal;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            MenuPrincipal ventana = new MenuPrincipal();
            ventana.setVisible(true);
        });
    }
}