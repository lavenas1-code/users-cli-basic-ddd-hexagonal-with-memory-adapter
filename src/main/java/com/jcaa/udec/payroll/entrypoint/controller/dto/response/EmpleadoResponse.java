package com.jcaa.udec.payroll.entrypoint.controller.dto.response;

import lombok.Builder;

@Builder
public record EmpleadoResponse(
        String dni,
        String nombreCompleto,
        String telefono,
        String direccion,
        String cuentaBancaria,
        String categoria,
        String sueldoBase) {
    private static final String FORMATO_DATOS = """
            DNI: %s
            NOMBRE: %s
            TELEFONO: %s
            DIRECCION: %s
            CUENTA BANCARIA: %s
            CATEGORIA: %s
            SUELDO BASE: %s
            """;

    @Override
    public String toString() {
        return FORMATO_DATOS.formatted(
                dni, nombreCompleto, telefono, direccion, cuentaBancaria, categoria, sueldoBase);
    }
}
