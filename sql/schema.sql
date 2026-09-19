CREATE DATABASE IF NOT EXISTS gestion_empleados;

USE gestion_empleados;

DROP TABLE IF EXISTS empleados; 

-- Id: Identificador automatico y unico del empleado
-- nombre, departamento: nombre para permitir nombres completos y departamento para texto libre hasta 100 caracteres
-- salario: para almacenar los salarios con dos decimales
-- fecha_contratacion: DATE porque solo es necesaria la feecha
-- activo: BOOLEAN para indicar si el empleado esta activo o inactivos.

CREATE TABLE empleados (
id INT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(50) NOT NULL, 
departamento VARCHAR(100) NOT NULL,
salario DECIMAL(10,2) NOT NULL,
fecha_contratacion DATE NOT NULL,
activo BOOLEAN NOT NULL DEFAULT TRUE
);



