package com.jcaa.udec.payroll.application.service;

import com.jcaa.udec.payroll.application.service.dto.command.CrearEmpleadoComando;
import com.jcaa.udec.payroll.application.service.mapper.EmpleadoMapper;
import com.jcaa.udec.payroll.application.service.ports.in.AgregarEmpleadoUseCase;
import com.jcaa.udec.payroll.domain.core.model.Empleado;
import com.jcaa.udec.payroll.domain.port.out.GuardarEmpleadoPort;

public class AgregarEmpleadoService implements AgregarEmpleadoUseCase {
    private final GuardarEmpleadoPort guardarEmpleadoPort;

    public AgregarEmpleadoService(GuardarEmpleadoPort guardarEmpleadoPort) {
        this.guardarEmpleadoPort = guardarEmpleadoPort;
    }

    @Override
    public void guardar(CrearEmpleadoComando comando) {
        Empleado empleado = EmpleadoMapper.mapearAEmpleado(comando);
        guardarEmpleadoPort.guardar(empleado);
    }
}
