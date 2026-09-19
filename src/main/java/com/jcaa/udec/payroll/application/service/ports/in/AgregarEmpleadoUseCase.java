package com.jcaa.udec.payroll.application.service.ports.in;

import com.jcaa.udec.payroll.application.service.dto.command.CrearEmpleadoComando;

public interface AgregarEmpleadoUseCase {
    void guardar(CrearEmpleadoComando comando);
}
