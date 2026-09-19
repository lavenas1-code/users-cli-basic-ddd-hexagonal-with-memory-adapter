package com.jcaa.udec.payroll.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.jcaa.udec.payroll.application.service.dto.command.CrearEmpleadoComando;
import com.jcaa.udec.payroll.domain.core.model.Empleado;
import com.jcaa.udec.payroll.domain.port.out.GuardarEmpleadoPort;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class AgregarEmpleadoServiceTest {

    @Test
    void deberiaMapearElComandoYGuardarElEmpleado() {
        // Arrange
        List<Empleado> empleadosGuardados = new ArrayList<>();
        GuardarEmpleadoPort guardarEmpleadoPortFalso = empleadosGuardados::add;
        AgregarEmpleadoService servicio = new AgregarEmpleadoService(guardarEmpleadoPortFalso);
        CrearEmpleadoComando comando = new CrearEmpleadoComando(
                "1002003000",
                "Laura",
                "Gomez Ruiz",
                "3001234567",
                "Calle 10 # 5-20",
                "1234567890123",
                "PROFESIONAL");

        // Act
        servicio.guardar(comando);

        // Assert
        assertThat(empleadosGuardados).hasSize(1);
        assertThat(empleadosGuardados.get(0).getDni()).isEqualTo("1002003000");
    }
}
