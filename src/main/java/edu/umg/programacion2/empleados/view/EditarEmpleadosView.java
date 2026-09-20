package edu.umg.programacion2.empleados.view;

import edu.umg.programacion2.empleados.dao.EmpleadoDAO;
import edu.umg.programacion2.empleados.modelo.Empleado;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class EditarEmpleadosView extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final MenuPrincipal menuPrincipal;

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtDepartamento;
    private JTextField txtSalario;
    private JTextField txtFecha;
    private JTextField txtExperiencia;
    private JTextField txtBono;

    private JCheckBox chkActivo;

    private JButton btnBuscar;
    private JButton btnActualizar;
    private JButton btnRegresar;

    private final EmpleadoDAO DAO = new EmpleadoDAO();

    private Empleado empleado;

    public EditarEmpleadosView(MenuPrincipal menuPrincipal) {

        this.menuPrincipal = menuPrincipal;

        setTitle("Editar Empleado");
        setSize(550, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        crearComponentes();
    }

    private void crearComponentes() {

        JLabel titulo = new JLabel("EDITAR EMPLEADO");

        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        txtId = new JTextField();
        txtNombre = new JTextField();
        txtDepartamento = new JTextField();
        txtSalario = new JTextField();
        txtFecha = new JTextField();
        txtExperiencia = new JTextField();
        txtBono = new JTextField();

        chkActivo = new JCheckBox("Empleado activo");

        btnBuscar = new JButton("Buscar");
        btnActualizar = new JButton("Actualizar");
        btnRegresar = new JButton("Regresar");

        txtNombre.setEnabled(false);
        txtDepartamento.setEnabled(false);
        txtSalario.setEnabled(false);
        txtFecha.setEnabled(false);
        txtExperiencia.setEnabled(false);
        txtBono.setEnabled(false);
        chkActivo.setEnabled(false);
        btnActualizar.setEnabled(false);

        JPanel formulario = new JPanel(new GridLayout(8, 2, 10, 10));

        formulario.add(new JLabel("ID:"));
        formulario.add(txtId);

        formulario.add(new JLabel(""));
        formulario.add(btnBuscar);

        formulario.add(new JLabel("Nombre:"));
        formulario.add(txtNombre);

        formulario.add(new JLabel("Departamento:"));
        formulario.add(txtDepartamento);

        formulario.add(new JLabel("Salario:"));
        formulario.add(txtSalario);

        formulario.add(new JLabel("Fecha contratación:"));
        formulario.add(txtFecha);

        formulario.add(new JLabel("Años experiencia:"));
        formulario.add(txtExperiencia);

        formulario.add(new JLabel("Bono anual:"));
        formulario.add(txtBono);

        formulario.add(new JLabel("Estado:"));
        formulario.add(chkActivo);

        JPanel botones = new JPanel();

        botones.add(btnActualizar);
        botones.add(btnRegresar);

        setLayout(new BorderLayout(10, 10));

        add(titulo, BorderLayout.NORTH);
        add(formulario, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);

        btnBuscar.addActionListener(e -> buscarEmpleado());

        btnActualizar.addActionListener(e -> actualizarEmpleado());

        btnRegresar.addActionListener(e -> regresar());
    }

    private void buscarEmpleado() {

        int id;

        try {

            id = Integer.parseInt(txtId.getText().trim());

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

            txtNombre.setText(empleado.getNombre());
            txtDepartamento.setText(empleado.getDepartamento());
            txtSalario.setText(empleado.getSalario().toPlainString());
            txtFecha.setText(
                    empleado.getFechaContratacion().toString()
            );
            txtExperiencia.setText(
                    String.valueOf(empleado.getAnios_experiencia())
            );
            txtBono.setText(
                    empleado.getBono_anual().toPlainString()
            );

            chkActivo.setSelected(empleado.isActivo());

            habilitarCampos(true);

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error de base de datos:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void actualizarEmpleado() {

        String nombre = txtNombre.getText().trim();
        String departamento = txtDepartamento.getText().trim();

        if (nombre.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "El nombre no puede quedar vacío."
            );

            return;
        }

        if (departamento.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "El departamento no puede quedar vacío."
            );

            return;
        }

        BigDecimal salario;

        try {

            salario = new BigDecimal(
                    txtSalario.getText().trim()
            );

            if (salario.compareTo(BigDecimal.ZERO) <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "El salario debe ser mayor a cero."
                );

                return;
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese un salario válido."
            );

            return;
        }

        LocalDate fecha;

        try {

            fecha = LocalDate.parse(
                    txtFecha.getText().trim()
            );

            if (fecha.isAfter(LocalDate.now())) {

                JOptionPane.showMessageDialog(
                        this,
                        "La fecha no puede ser futura."
                );

                return;
            }

        } catch (DateTimeParseException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese una fecha válida con formato YYYY-MM-DD."
            );

            return;
        }

        int experiencia;

        try {

            experiencia = Integer.parseInt(
                    txtExperiencia.getText().trim()
            );

            if (experiencia <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Los años de experiencia deben ser mayores a 0."
                );

                return;
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese años de experiencia válidos."
            );

            return;
        }

        BigDecimal bono;

        try {

            bono = new BigDecimal(
                    txtBono.getText().trim()
            );

            if (bono.compareTo(BigDecimal.ZERO) <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "El bono debe ser mayor a cero."
                );

                return;
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese un bono válido."
            );

            return;
        }

        empleado.setNombre(nombre);
        empleado.setDepartamento(departamento);
        empleado.setSalario(salario);
        empleado.setFechaContratacion(fecha);
        empleado.setActivo(chkActivo.isSelected());
        empleado.setAnios_experiencia(experiencia);
        empleado.setBono_anual(bono);

        try {

            DAO.actualizar(empleado);

            JOptionPane.showMessageDialog(
                    this,
                    "Empleado actualizado correctamente."
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

    private void habilitarCampos(boolean habilitar) {

        txtNombre.setEnabled(habilitar);
        txtDepartamento.setEnabled(habilitar);
        txtSalario.setEnabled(habilitar);
        txtFecha.setEnabled(habilitar);
        txtExperiencia.setEnabled(habilitar);
        txtBono.setEnabled(habilitar);
        chkActivo.setEnabled(habilitar);

        btnActualizar.setEnabled(habilitar);
    }

    private void regresar() {

        this.dispose();
        menuPrincipal.setVisible(true);
    }
}