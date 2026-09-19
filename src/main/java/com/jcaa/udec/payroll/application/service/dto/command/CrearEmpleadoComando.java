package com.jcaa.udec.payroll.application.service.dto.command;

public record CrearEmpleadoComando(
        String dni,
        String nombre,
        String apellidos,
        String telefono,
        String direccion,
        String cuentaBancaria,
        String categoria) {
}
