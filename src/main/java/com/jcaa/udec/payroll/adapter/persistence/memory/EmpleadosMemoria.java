package com.jcaa.udec.payroll.adapter.persistence.memory;

import com.jcaa.udec.payroll.domain.core.model.Empleado;
import java.util.ArrayList;
import java.util.List;

final class EmpleadosMemoria {
    private static final List<Empleado> EMPLEADOS = new ArrayList<>();

    private EmpleadosMemoria() {
    }

    static List<Empleado> obtenerEmpleados() {
        return EMPLEADOS;
    }
}
