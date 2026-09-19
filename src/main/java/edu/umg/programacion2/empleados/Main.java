package edu.umg.programacion2.empleados;

import edu.umg.programacion2.empleados.dao.EmpleadoDAO;
import edu.umg.programacion2.empleados.modelo.Empleado;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final Scanner SC = new Scanner(System.in);
    private static final EmpleadoDAO DAO = new EmpleadoDAO();

    public static void main(String[] args) {

        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero("Elige una opción: ");

            try {

                switch (opcion) {

                    case 1:
                        registrarEmpleado();
                        break;

                    case 2:
                        listarEmpleados();
                        break;

                    case 3:
                        editarEmpleado();
                        break;

                    case 4:
                        eliminarEmpleado();
                        break;

                    case 5:
                        System.out.println("Programa finalizado.");
                        break;

                    default:
                        System.out.println("Opción no válida.");
                }

            } catch (SQLException e) {
                System.err.println("Error de base de datos: " + e.getMessage());
            }

        } while (opcion != 5);
    }

    private static void mostrarMenu() {

        System.out.println("===== GESTIÓN DE EMPLEADOS =====");
        System.out.println("1. Registrar empleado");
        System.out.println("2. Listar empleados");
        System.out.println("3. Editar empleado");
        System.out.println("4. Eliminar empleado");
        System.out.println("5. Salir");
    }

    private static void registrarEmpleado() throws SQLException {
        System.out.println();
        System.out.println("===== REGISTRAR EMPLEADO =====");

        String nombre;

        do {
            nombre = leerTexto("Nombre completo: ");

            if (nombre.isEmpty()) {
                System.out.println("El nombre no puede quedar vacío.");
            }

        } while (nombre.isEmpty());

        String departamento;

        do {
            departamento = leerTexto("Departamento: ");

            if (departamento.isEmpty()) {
                System.out.println("El departamento no puede quedar vacío.");
            }

        } while (departamento.isEmpty());

        BigDecimal salario;

        while (true) {

            String textoSalario = leerTexto("Salario mensual: ");

            try {
                salario = new BigDecimal(textoSalario);

                if (salario.compareTo(BigDecimal.ZERO) > 0) {
                    break;
                }

                System.out.println("El salario debe ser mayor a cero.");

            } catch (NumberFormatException e) {
                System.out.println("Ingrese un salario válido.");
            }
        }

        LocalDate fechaContratacion;

        while (true) {

            String textoFecha = leerTexto(
                    "Fecha de contratación (YYYY-MM-DD): ");

            try {
                fechaContratacion = LocalDate.parse(textoFecha);

                if (!fechaContratacion.isAfter(LocalDate.now())) {
                    break;
                }

                System.out.println(
                        "La fecha de contratación no puede ser futura.");

            } catch (DateTimeParseException e) {
                System.out.println(
                        "Ingrese una fecha válida con formato YYYY-MM-DD.");
            }
        }

        boolean activo = leerBooleano("¿El empleado está activo? (S/N): ");

        Empleado empleado = new Empleado(
                nombre,
                departamento,
                salario,
                fechaContratacion,
                activo
        );

        DAO.crear(empleado);

        System.out.println("Empleado registrado.");
    }

    private static void listarEmpleados() throws SQLException {

        System.out.println();
        System.out.println("===== LISTADO DE EMPLEADOS =====");

        List<Empleado> empleados = DAO.listarTodos();

        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }

        for (Empleado empleado : empleados) {

            String estado = empleado.isActivo()
                    ? "Activo"
                    : "Inactivo";

            System.out.printf(
                    "[%d] %s | %s | Q%s | %s%n",
                    empleado.getId(),
                    empleado.getNombre(),
                    empleado.getDepartamento(),
                    empleado.getSalario().toPlainString(),
                    estado
            );
        }
    }

    private static void editarEmpleado() throws SQLException {

        System.out.println();
        System.out.println("===== EDITAR EMPLEADO =====");

        int id = leerEntero("ID del empleado: ");

        Optional<Empleado> resultado = DAO.buscarPorId(id);

        if (resultado.isEmpty()) {
            System.out.println("No existe un empleado con ese ID.");
            return;
        }

        Empleado empleado = resultado.get();

        String nombre;

        do {
            nombre = leerTexto(
                    "Nombre completo [" + empleado.getNombre() + "]: ");

            if (nombre.isEmpty()) {
                System.out.println("El nombre no puede quedar vacío.");
            }

        } while (nombre.isEmpty());

        String departamento;

        do {
            departamento = leerTexto(
                    "Departamento [" + empleado.getDepartamento() + "]: ");

            if (departamento.isEmpty()) {
                System.out.println(
                        "El departamento no puede quedar vacío.");
            }

        } while (departamento.isEmpty());

        BigDecimal salario;

        while (true) {

            String textoSalario = leerTexto(
                    "Salario mensual [" + empleado.getSalario() + "]: ");

            try {
                salario = new BigDecimal(textoSalario);

                if (salario.compareTo(BigDecimal.ZERO) > 0) {
                    break;
                }

                System.out.println("El salario debe ser mayor a cero.");

            } catch (NumberFormatException e) {
                System.out.println("Ingrese un salario válido.");
            }
        }

        LocalDate fechaContratacion;

        while (true) {

            String textoFecha = leerTexto(
                    "Fecha de contratación [" +
                    empleado.getFechaContratacion() + "]: ");

            try {
                fechaContratacion = LocalDate.parse(textoFecha);

                if (!fechaContratacion.isAfter(LocalDate.now())) {
                    break;
                }

                System.out.println(
                        "La fecha no puede ser futura.");

            } catch (DateTimeParseException e) {
                System.out.println(
                        "Ingrese una fecha válida con formato YYYY-MM-DD.");
            }
        }

        boolean activo = leerBooleano(
                "¿El empleado está activo? (S/N): ");

        empleado.setNombre(nombre);
        empleado.setDepartamento(departamento);
        empleado.setSalario(salario);
        empleado.setFechaContratacion(fechaContratacion);
        empleado.setActivo(activo);

        DAO.actualizar(empleado);

        System.out.println("Empleado actualizado.");
    }

    private static void eliminarEmpleado() throws SQLException {

        System.out.println();
        System.out.println("===== ELIMINAR EMPLEADO =====");

        int id = leerEntero("ID del empleado: ");

        Optional<Empleado> resultado = DAO.buscarPorId(id);

        if (resultado.isEmpty()) {
            System.out.println("No existe un empleado con ese ID.");
            return;
        }

        Empleado empleado = resultado.get();

        System.out.println(
                "Empleado: " + empleado.getNombre());

        String confirmacion = leerTexto(
                "¿Está seguro de eliminarlo? (S/N): ");

        if (confirmacion.equalsIgnoreCase("S")) {

            DAO.eliminar(id);

            System.out.println("Empleado eliminado.");

        } else {

            System.out.println("Eliminación cancelada.");
        }
    }

    private static String leerTexto(String mensaje) {

        System.out.print(mensaje);

        return SC.nextLine().trim();
    }

    private static int leerEntero(String mensaje) {

        while (true) {

            String texto = leerTexto(mensaje);

            try {
                return Integer.parseInt(texto);

            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número entero válido.");
            }
        }
    }

    private static boolean leerBooleano(String mensaje) {

        while (true) {

            String respuesta = leerTexto(mensaje);

            if (respuesta.equalsIgnoreCase("S")) {
                return true;
            }

            if (respuesta.equalsIgnoreCase("N")) {
                return false;
            }

            System.out.println("Ingrese S o N.");
        }
    }
}