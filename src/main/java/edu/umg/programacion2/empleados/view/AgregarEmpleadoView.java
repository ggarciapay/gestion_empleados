package edu.umg.programacion2.empleados.view;

import edu.umg.programacion2.empleados.dao.EmpleadoDAO;
import edu.umg.programacion2.empleados.modelo.Empleado;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class AgregarEmpleadoView extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final MenuPrincipal menuPrincipal;

    private JTextField txtNombre;
    private JTextField txtDepartamento;
    private JTextField txtSalario;
    private JTextField txtFecha;
    private JTextField txtExperiencia;
    private JTextField txtBono;

    private JCheckBox chkActivo;

    private JButton btnGuardar;
    private JButton btnRegresar;

    private final EmpleadoDAO DAO = new EmpleadoDAO();

    public AgregarEmpleadoView(MenuPrincipal menuPrincipal) {

        this.menuPrincipal = menuPrincipal;

        setTitle("Registrar Empleado");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        crearComponentes();
    }

    private void crearComponentes() {

        JLabel titulo = new JLabel("REGISTRAR EMPLEADO");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        txtNombre = new JTextField();
        txtDepartamento = new JTextField();
        txtSalario = new JTextField();
        txtFecha = new JTextField();
        txtExperiencia = new JTextField();
        txtBono = new JTextField();

        chkActivo = new JCheckBox("Empleado activo");

        btnGuardar = new JButton("Guardar");
        btnRegresar = new JButton("Regresar");

        JPanel formulario = new JPanel(new GridLayout(7, 2, 10, 10));

        formulario.add(new JLabel("Nombre completo:"));
        formulario.add(txtNombre);

        formulario.add(new JLabel("Departamento:"));
        formulario.add(txtDepartamento);

        formulario.add(new JLabel("Salario mensual:"));
        formulario.add(txtSalario);

        formulario.add(new JLabel("Fecha contratación:"));
        formulario.add(txtFecha);

        formulario.add(new JLabel("Años de experiencia:"));
        formulario.add(txtExperiencia);

        formulario.add(new JLabel("Bono anual:"));
        formulario.add(txtBono);

        formulario.add(new JLabel("Estado:"));
        formulario.add(chkActivo);

        JPanel botones = new JPanel();

        botones.add(btnGuardar);
        botones.add(btnRegresar);

        setLayout(new BorderLayout(10, 10));

        add(titulo, BorderLayout.NORTH);
        add(formulario, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> guardarEmpleado());

        btnRegresar.addActionListener(e -> regresar());
    }

    private void guardarEmpleado() {

        String nombre = txtNombre.getText().trim();
        String departamento = txtDepartamento.getText().trim();
        String textoSalario = txtSalario.getText().trim();
        String textoFecha = txtFecha.getText().trim();
        String textoExperiencia = txtExperiencia.getText().trim();
        String textoBono = txtBono.getText().trim();

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

            salario = new BigDecimal(textoSalario);

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

        LocalDate fechaContratacion;

        try {

            fechaContratacion = LocalDate.parse(textoFecha);

            if (fechaContratacion.isAfter(LocalDate.now())) {

                JOptionPane.showMessageDialog(
                        this,
                        "La fecha de contratación no puede ser futura."
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

        int aniosExperiencia;

        try {

            aniosExperiencia = Integer.parseInt(textoExperiencia);

            if (aniosExperiencia <= 0) {

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

        BigDecimal bonoAnual;

        try {

            bonoAnual = new BigDecimal(textoBono);

            if (bonoAnual.compareTo(BigDecimal.ZERO) <= 0) {

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

        boolean activo = chkActivo.isSelected();

        Empleado empleado = new Empleado(
                nombre,
                departamento,
                salario,
                fechaContratacion,
                activo,
                aniosExperiencia,
                bonoAnual
        );

        try {

            DAO.crear(empleado);

            JOptionPane.showMessageDialog(
                    this,
                    "Empleado registrado correctamente."
            );

            limpiarCampos();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error de base de datos:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void limpiarCampos() {

        txtNombre.setText("");
        txtDepartamento.setText("");
        txtSalario.setText("");
        txtFecha.setText("");
        txtExperiencia.setText("");
        txtBono.setText("");
        chkActivo.setSelected(false);
    }

    private void regresar() {

        this.dispose();
        menuPrincipal.setVisible(true);
    }
}