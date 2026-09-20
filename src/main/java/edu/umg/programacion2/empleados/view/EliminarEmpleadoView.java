package edu.umg.programacion2.empleados.view;

import edu.umg.programacion2.empleados.dao.EmpleadoDAO;
import edu.umg.programacion2.empleados.modelo.Empleado;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Optional;

public class EliminarEmpleadoView extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final MenuPrincipal menuPrincipal;

    private JTextField txtId;
    private JLabel lblEmpleado;

    private JButton btnBuscar;
    private JButton btnEliminar;
    private JButton btnRegresar;

    private final EmpleadoDAO DAO = new EmpleadoDAO();

    private Empleado empleado;

    public EliminarEmpleadoView(MenuPrincipal menuPrincipal) {

        this.menuPrincipal = menuPrincipal;

        setTitle("Eliminar Empleado");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        crearComponentes();
    }

    private void crearComponentes() {

        JLabel titulo = new JLabel("ELIMINAR EMPLEADO");

        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        txtId = new JTextField();

        lblEmpleado = new JLabel("Empleado: ");

        btnBuscar = new JButton("Buscar");
        btnEliminar = new JButton("Eliminar");
        btnRegresar = new JButton("Regresar");

        btnEliminar.setEnabled(false);

        JPanel formulario = new JPanel(new GridLayout(3, 2, 10, 10));

        formulario.add(new JLabel("ID del empleado:"));
        formulario.add(txtId);

        formulario.add(new JLabel(""));
        formulario.add(btnBuscar);

        formulario.add(new JLabel("Empleado encontrado:"));
        formulario.add(lblEmpleado);

        JPanel botones = new JPanel();

        botones.add(btnEliminar);
        botones.add(btnRegresar);

        setLayout(new BorderLayout(10, 10));

        add(titulo, BorderLayout.NORTH);
        add(formulario, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);

        btnBuscar.addActionListener(e -> buscarEmpleado());

        btnEliminar.addActionListener(e -> eliminarEmpleado());

        btnRegresar.addActionListener(e -> regresar());
    }

    private void buscarEmpleado() {

        int id;

        try {

            id = Integer.parseInt(
                    txtId.getText().trim()
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese un ID válido."
            );

            return;
        }

        try {

            Optional<Empleado> resultado = DAO.buscarPorId(id);

            if (resultado.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "No existe un empleado con ese ID."
                );

                return;
            }

            empleado = resultado.get();

            lblEmpleado.setText(
                    empleado.getNombre()
                    + " - "
                    + empleado.getDepartamento()
            );

            btnEliminar.setEnabled(true);

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error de base de datos:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void eliminarEmpleado() {

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar al empleado?\n\n"
                        + empleado.getNombre(),
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            DAO.eliminar(empleado.getId());

            JOptionPane.showMessageDialog(
                    this,
                    "Empleado eliminado correctamente."
            );

            regresar();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error de base de datos:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void regresar() {

        this.dispose();
        menuPrincipal.setVisible(true);
    }
}