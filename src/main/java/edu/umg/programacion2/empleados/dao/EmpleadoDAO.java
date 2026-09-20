package edu.umg.programacion2.empleados.dao;

import edu.umg.programacion2.empleados.modelo.Empleado;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmpleadoDAO {

    private static final String URL =
            "jdbc:mysql://127.0.0.1:3306/gestion_empleados?useSSL=false&serverTimezone=UTC";

    private static final String USER = "root";

    private static final String PASSWORD = "root";

    private Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void crear(Empleado empleado) throws SQLException {

        String sql = "INSERT INTO empleados " +
                "(nombre, departamento, salario, fecha_contratacion, activo, anios_experiencia, bono_anual) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, empleado.getNombre());
            ps.setString(2, empleado.getDepartamento());
            ps.setBigDecimal(3, empleado.getSalario());
            ps.setDate(4, Date.valueOf(empleado.getFechaContratacion()));
            ps.setBoolean(5, empleado.isActivo());
            ps.setInt(6, empleado.getAnios_experiencia());
            ps.setBigDecimal(7, empleado.getBono_anual());

            ps.executeUpdate();
        }
    }

    public List<Empleado> listarTodos() throws SQLException {

        List<Empleado> empleados = new ArrayList<>();

        String sql = "SELECT id, nombre, departamento, salario, " +
                "fecha_contratacion, activo, anios_experiencia, bono_anual " +
                "FROM empleados";

        try (Connection conexion = obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Empleado empleado = new Empleado(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("departamento"),
                        rs.getBigDecimal("salario"),
                        rs.getDate("fecha_contratacion").toLocalDate(),
                        rs.getBoolean("activo"),
                        rs.getInt("anios_experiencia"),
                        rs.getBigDecimal("bono_anual")
                );

                empleados.add(empleado);
            }
        }

        return empleados;
    }

    public Optional<Empleado> buscarPorId(int id) throws SQLException {

        String sql = "SELECT id, nombre, departamento, salario, " +
                "fecha_contratacion, activo, anios_experiencia, bono_anual " +
                "FROM empleados WHERE id = ?";

        try (Connection conexion = obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Empleado empleado = new Empleado(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("departamento"),
                            rs.getBigDecimal("salario"),
                            rs.getDate("fecha_contratacion").toLocalDate(),
                            rs.getBoolean("activo"),
                            rs.getInt("anios_experiencia"),
                            rs.getBigDecimal("bono_anual")
                    );

                    return Optional.of(empleado);
                }
            }
        }

        return Optional.empty();
    }

    public void actualizar(Empleado empleado) throws SQLException {

        String sql = "UPDATE empleados SET " +
                "nombre = ?, " +
                "departamento = ?, " +
                "salario = ?, " +
                "fecha_contratacion = ?, " +
                "activo = ?, " +
                "anios_experiencia = ?, " +
                "bono_anual = ?, " +
                "WHERE id = ?";

        try (Connection conexion = obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, empleado.getNombre());
            ps.setString(2, empleado.getDepartamento());
            ps.setBigDecimal(3, empleado.getSalario());
            ps.setDate(4, Date.valueOf(empleado.getFechaContratacion()));
            ps.setBoolean(5, empleado.isActivo());
            ps.setInt(6, empleado.getId());
            ps.setInt(7, empleado.getAnios_experiencia());
            ps.setBigDecimal(8, empleado.getBono_anual());
            
            ps.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {

        String sql = "DELETE FROM empleados WHERE id = ?";

        try (Connection conexion = obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();
        }
    }
}