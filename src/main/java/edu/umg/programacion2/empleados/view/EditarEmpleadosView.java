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

        titulo.setFont(new Font("Arial", Font.BOLD, 30));
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

        JPanel formulario = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fuenteEtiqueta = new Font("Arial", Font.BOLD, 18);
        Font fuenteCampo = new Font("Arial", Font.PLAIN, 18);

        txtId.setFont(fuenteCampo);
        txtNombre.setFont(fuenteCampo);
        txtDepartamento.setFont(fuenteCampo);
        txtSalario.setFont(fuenteCampo);
        txtFecha.setFont(fuenteCampo);
        txtExperiencia.setFont(fuenteCampo);
        txtBono.setFont(fuenteCampo);

        chkActivo.setFont(fuenteEtiqueta);

        txtId.setPreferredSize(new Dimension(180, 45));

        Dimension tamañoCampo = new Dimension(355, 65);

        txtNombre.setPreferredSize(tamañoCampo);
        txtDepartamento.setPreferredSize(tamañoCampo);
        txtSalario.setPreferredSize(tamañoCampo);
        txtFecha.setPreferredSize(tamañoCampo);
        txtExperiencia.setPreferredSize(tamañoCampo);
        txtBono.setPreferredSize(tamañoCampo);
        
        JLabel lblId = new JLabel("ID empleado:");
        lblId.setFont(fuenteEtiqueta);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.45;

        formulario.add(lblId, gbc);

        JPanel panelId = new JPanel(new BorderLayout(10, 0));

        panelId.add(txtId, BorderLayout.CENTER);
        panelId.add(btnBuscar, BorderLayout.EAST);

        gbc.gridx = 1;
        gbc.weightx = 0.55;

        formulario.add(panelId, gbc);
        
        JLabel lblNombre = new JLabel("Nombre completo:");
        lblNombre.setFont(fuenteEtiqueta);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formulario.add(lblNombre, gbc);

        gbc.gridx = 1;
        formulario.add(txtNombre, gbc);


        JLabel lblDepartamento = new JLabel("Departamento:");
        lblDepartamento.setFont(fuenteEtiqueta);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formulario.add(lblDepartamento, gbc);

        gbc.gridx = 1;
        formulario.add(txtDepartamento, gbc);


        JLabel lblSalario = new JLabel("Salario mensual:");
        lblSalario.setFont(fuenteEtiqueta);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formulario.add(lblSalario, gbc);

        gbc.gridx = 1;
        formulario.add(txtSalario, gbc);


        JLabel lblFecha = new JLabel("Fecha contratación:");
        lblFecha.setFont(fuenteEtiqueta);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formulario.add(lblFecha, gbc);

        gbc.gridx = 1;
        formulario.add(txtFecha, gbc);


        JLabel lblExperiencia = new JLabel("Años de experiencia:");
        lblExperiencia.setFont(fuenteEtiqueta);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formulario.add(lblExperiencia, gbc);

        gbc.gridx = 1;
        formulario.add(txtExperiencia, gbc);


        JLabel lblBono = new JLabel("Bono anual:");
        lblBono.setFont(fuenteEtiqueta);

        gbc.gridx = 0;
        gbc.gridy = 6;
        formulario.add(lblBono, gbc);


        gbc.gridx = 1;
        formulario.add(txtBono, gbc);


        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setFont(fuenteEtiqueta);

        gbc.gridx = 0;
        gbc.gridy = 7;
        formulario.add(lblEstado, gbc);

        gbc.gridx = 1;
        formulario.add(chkActivo, gbc);
        
        
        JPanel botones = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 10, 10)
        );

        btnActualizar.setPreferredSize(new Dimension(130, 45));
        btnRegresar.setPreferredSize(new Dimension(130, 45));

        btnActualizar.setFont(new Font("Arial", Font.BOLD, 16));
        btnRegresar.setFont(new Font("Arial", Font.BOLD, 16));

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