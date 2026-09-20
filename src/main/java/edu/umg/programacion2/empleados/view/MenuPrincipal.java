package edu.umg.programacion2.empleados.view;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnRegistrar;
    private JButton btnListar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnSalir;

    public MenuPrincipal() {

        setTitle("Gestión de Empleados");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        crearComponentes();
    }

    private void crearComponentes() {

        JLabel titulo = new JLabel("GESTIÓN DE EMPLEADOS");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        btnRegistrar = new JButton("Registrar empleado");
        btnListar = new JButton("Listar empleados");
        btnEditar = new JButton("Editar empleado");
        btnEliminar = new JButton("Eliminar empleado");
        btnSalir = new JButton("Salir");

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(5, 1, 10, 10));

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnListar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnSalir);

        setLayout(new BorderLayout(10, 10));

        add(titulo, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);

        btnRegistrar.addActionListener(e -> {

            AgregarEmpleadoView ventana = new AgregarEmpleadoView(this);
            ventana.setVisible(true);

            this.setVisible(false);
        });

        btnListar.addActionListener(e -> {

            ListarEmpleadosView ventana = new ListarEmpleadosView(this);
            ventana.setVisible(true);

            this.setVisible(false);
        });

        btnEditar.addActionListener(e -> {

            EditarEmpleadosView ventana = new EditarEmpleadosView(this);
            ventana.setVisible(true);

            this.setVisible(false);
        });

        btnEliminar.addActionListener(e -> {

            EliminarEmpleadoView ventana = new EliminarEmpleadoView(this);
            ventana.setVisible(true);

            this.setVisible(false);
        });

        btnSalir.addActionListener(e -> {

            int respuesta = JOptionPane.showConfirmDialog(
                    this,
                    "¿Desea salir del programa?",
                    "Salir",
                    JOptionPane.YES_NO_OPTION
            );

            if (respuesta == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }
}