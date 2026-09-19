package com.jcaa.udec.payroll.entrypoint.controller.dto.response;

import java.util.List;

public record ObtenerEmpleadoResponse(List<EmpleadoResponse> empleados) {
    public ObtenerEmpleadoResponse {
        empleados = List.copyOf(empleados);
    }

    public boolean estaVacia() {
        return empleados.isEmpty();
    }

    @Override
    public String toString() {
        return String.join(System.lineSeparator(), empleados.stream()
                .map(EmpleadoResponse::toString)
                .toList());
    }
}
