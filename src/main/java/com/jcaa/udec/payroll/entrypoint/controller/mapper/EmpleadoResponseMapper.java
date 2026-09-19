package com.jcaa.udec.payroll.entrypoint.controller.mapper;

import com.jcaa.udec.payroll.domain.core.model.Empleado;
import com.jcaa.udec.payroll.entrypoint.controller.dto.response.EmpleadoResponse;
import com.jcaa.udec.payroll.entrypoint.controller.dto.response.ObtenerEmpleadoResponse;
import java.util.List;

public final class EmpleadoResponseMapper {
    private EmpleadoResponseMapper() {
    }

    public static ObtenerEmpleadoResponse mapearAResponse(Empleado empleado) {
        return new ObtenerEmpleadoResponse(List.of(mapearAResponseEmpleado(empleado)));
    }

    public static ObtenerEmpleadoResponse mapearAResponse(List<Empleado> empleados) {
        return new ObtenerEmpleadoResponse(empleados.stream()
                .map(EmpleadoResponseMapper::mapearAResponseEmpleado)
                .toList());
    }

    private static EmpleadoResponse mapearAResponseEmpleado(Empleado empleado) {
        return EmpleadoResponse.builder()
                .dni(empleado.getDni())
                .nombreCompleto(empleado.getNombreCompleto())
                .telefono(empleado.getTelefono())
                .direccion(empleado.getDireccion())
                .cuentaBancaria(empleado.getCuentaBancaria())
                .categoria(empleado.getCategoria())
                .sueldoBase(empleado.getSueldoBase().toPlainString())
                .build();
    }
}
