package com.jcaa.udec.payroll.domain.port.out;

import com.jcaa.udec.payroll.domain.core.model.Empleado;

public interface GuardarEmpleadoPort {
    void guardar(Empleado empleado);
}
