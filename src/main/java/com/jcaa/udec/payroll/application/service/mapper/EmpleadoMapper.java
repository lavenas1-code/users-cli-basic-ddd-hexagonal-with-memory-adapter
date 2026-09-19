package com.jcaa.udec.payroll.application.service.mapper;

import com.jcaa.udec.payroll.application.service.dto.command.ActualizarEmpleadoComando;
import com.jcaa.udec.payroll.application.service.dto.command.CrearEmpleadoComando;
import com.jcaa.udec.payroll.domain.core.model.Empleado;

public final class EmpleadoMapper {
    private EmpleadoMapper() {
    }

    public static Empleado mapearAEmpleado(CrearEmpleadoComando comando) {
        return Empleado.builder()
                .dni(comando.dni())
                .nombre(comando.nombre())
                .apellidos(comando.apellidos())
                .telefono(comando.telefono())
                .direccion(comando.direccion())
                .cuentaBancaria(comando.cuentaBancaria())
                .categoria(comando.categoria())
                .build();
    }

    public static Empleado mapearAEmpleado(ActualizarEmpleadoComando comando) {
        return Empleado.builder()
                .dni(comando.dni())
                .nombre(comando.nombre())
                .apellidos(comando.apellidos())
                .telefono(comando.telefono())
                .direccion(comando.direccion())
                .cuentaBancaria(comando.cuentaBancaria())
                .categoria(comando.categoria())
                .build();
    }
}
