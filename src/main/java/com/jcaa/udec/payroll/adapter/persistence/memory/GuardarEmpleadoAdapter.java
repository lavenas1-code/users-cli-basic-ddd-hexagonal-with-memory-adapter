package com.jcaa.udec.payroll.adapter.persistence.memory;

import com.jcaa.udec.payroll.domain.core.exception.EmpleadoYaExisteException;
import com.jcaa.udec.payroll.domain.core.model.Empleado;
import com.jcaa.udec.payroll.domain.port.out.GuardarEmpleadoPort;
import java.util.List;
import java.util.Objects;

public class GuardarEmpleadoAdapter implements GuardarEmpleadoPort {
    private final List<Empleado> empleados = EmpleadosMemoria.obtenerEmpleados();

    @Override
    public void guardar(Empleado empleado) {
        for (Empleado empleadoRegistrado : empleados) {
            if (Objects.equals(empleadoRegistrado.getDni(), empleado.getDni())) {
                throw new EmpleadoYaExisteException();
            }
        }
        empleados.add(empleado);
    }
}
