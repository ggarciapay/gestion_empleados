package edu.umg.programacion2.empleados.view;

import edu.umg.programacion2.empleados.dao.EmpleadoDAO;
import edu.umg.programacion2.empleados.modelo.Empleado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ListarEmpleadosView extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final MenuPrincipal menuPrincipal;

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private JButton btnRegresar;

    private final EmpleadoDAO DAO = new EmpleadoDAO();

    public ListarEmpleadosView(MenuPrincipal menuPrincipal) {

        this.menuPrincipal = menuPrincipal;

        setTitle("Listado de Empleados");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        crearComponentes();
        cargarEmpleados();
    }

    private void crearComponentes() {

        JLabel titulo = new JLabel("LISTADO DE EMPLEADOS");

        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        String[] columnas = {
                "ID",
                "Nombre",
                "Departamento",
                "Salario",
                "Estado",
                "Experiencia",
                "Bono anual"
        };

        modeloTabla = new DefaultTableModel(columnas, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);

        JScrollPane scroll = new JScrollPane(tabla);

        btnRegresar = new JButton("Regresar");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnRegresar);

        setLayout(new BorderLayout(10, 10));

        add(titulo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        btnRegresar.addActionListener(e -> regresar());
    }

    private void cargarEmpleados() {

        try {

            List<Empleado> empleados = DAO.listarTodos();

            modeloTabla.setRowCount(0);

            if (empleados.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "No hay empleados registrados."
                );

                return;
            }

            int registroMasBajo = empleados.get(0).getId();
            int registroMasAlto = empleados.get(0).getId();

            for (Empleado empleado : empleados) {

                String estado = empleado.isActivo()
                        ? "Activo"
                        : "Inactivo";

                modeloTabla.addRow(new Object[]{
                        empleado.getId(),
                        empleado.getNombre(),
                        empleado.getDepartamento(),
                        empleado.getSalario().toPlainString(),
                        estado,
                        empleado.getAnios_experiencia(),
                        empleado.getBono_anual()
                });

                if (empleado.getId() > registroMasAlto) {
                    registroMasAlto = empleado.getId();
                }

                if (empleado.getId() < registroMasBajo) {
                    registroMasBajo = empleado.getId();
                }
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Registro con valor numérico más alto: "
                            + registroMasAlto
                            + "\nRegistro con valor numérico más bajo: "
                            + registroMasBajo
            );

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