package com.jcaa.udec.payroll.entrypoint.controller.dto.request;

public record RegistrarEmpleadoPeticion(
        String dni,
        String nombre,
        String apellidos,
        String telefono,
        String direccion,
        String cuentaBancaria,
        String categoria) {
}
