package com.jcaa.udec.payroll.entrypoint.controller.dto.request;

public record ActualizarEmpleadoPeticion(
        String dni,
        String nombre,
        String apellidos,
        String telefono,
        String direccion,
        String cuentaBancaria,
        String categoria) {
}
