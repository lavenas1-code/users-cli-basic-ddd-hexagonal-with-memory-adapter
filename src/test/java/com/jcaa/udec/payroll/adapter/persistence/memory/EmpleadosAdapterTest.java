package com.jcaa.udec.payroll.adapter.persistence.memory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jcaa.udec.payroll.domain.core.exception.EmpleadoNoExisteException;
import com.jcaa.udec.payroll.domain.core.exception.EmpleadoYaExisteException;
import com.jcaa.udec.payroll.domain.core.model.Empleado;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmpleadosAdapterTest {
    private static final AtomicInteger SECUENCIA_DNI = new AtomicInteger(2_000_000);
    private final GuardarEmpleadoAdapter guardarEmpleadoAdapter = new GuardarEmpleadoAdapter();
    private final ObtenerEmpleadosAdapter obtenerEmpleadosAdapter = new ObtenerEmpleadosAdapter();
    private final ActualizarEmpleadoAdapter actualizarEmpleadoAdapter = new ActualizarEmpleadoAdapter();
    private final EliminarEmpleadoAdapter eliminarEmpleadoAdapter = new EliminarEmpleadoAdapter();

    @BeforeEach
    void limpiarEmpleados() {
        EmpleadosMemoria.obtenerEmpleados().clear();
    }

    @Test
    void deberiaGuardarYObtenerEmpleado() {
        // Arrange
        Empleado empleado = crearEmpleado();

        // Act
        guardarEmpleadoAdapter.guardar(empleado);
        Empleado encontrado = obtenerEmpleadosAdapter.buscarPorId(empleado.getDni());

        // Assert
        assertThat(encontrado).isSameAs(empleado);
    }

    @Test
    void deberiaRechazarEmpleadoDuplicado() {
        // Arrange
        Empleado empleado = crearEmpleado();
        guardarEmpleadoAdapter.guardar(empleado);

        // Act
        // Assert
        assertThatThrownBy(() -> guardarEmpleadoAdapter.guardar(empleado))
                .isInstanceOf(EmpleadoYaExisteException.class)
                .hasMessage("El empleado ya existe.");
    }

    @Test
    void deberiaReportarEmpleadoInexistenteAlBuscar() {
        // Arrange
        String dniInexistente = String.valueOf(SECUENCIA_DNI.incrementAndGet());

        // Act
        // Assert
        assertThatThrownBy(() -> obtenerEmpleadosAdapter.buscarPorId(dniInexistente))
                .isInstanceOf(EmpleadoNoExisteException.class)
                .hasMessage("El empleado no existe.");
    }

    @Test
    void deberiaListarTodosLosEmpleadosGuardados() {
        // Arrange
        Empleado primero = crearEmpleado();
        Empleado segundo = crearEmpleado();
        guardarEmpleadoAdapter.guardar(primero);
        guardarEmpleadoAdapter.guardar(segundo);

        // Act
        List<Empleado> empleados = obtenerEmpleadosAdapter.obtenerTodos();

        // Assert
        assertThat(empleados).containsExactly(primero, segundo);
    }

    @Test
    void deberiaActualizarUnEmpleadoExistente() {
        // Arrange
        Empleado original = crearEmpleado();
        guardarEmpleadoAdapter.guardar(original);
        Empleado actualizado = Empleado.builder()
                .dni(original.getDni())
                .nombre("NombreActualizado")
                .apellidos("ApellidoActualizado")
                .telefono(original.getTelefono())
                .direccion("Nueva Direccion 123")
                .cuentaBancaria(original.getCuentaBancaria())
                .categoria("GERENCIAL")
                .build();

        // Act
        actualizarEmpleadoAdapter.actualizar(actualizado);
        Empleado resultado = obtenerEmpleadosAdapter.buscarPorId(original.getDni());

        // Assert
        assertThat(resultado.getNombreCompleto()).isEqualTo("NombreActualizado ApellidoActualizado");
        assertThat(resultado.getCategoria()).isEqualTo("GERENCIAL");
    }

    @Test
    void deberiaRechazarActualizacionDeEmpleadoInexistente() {
        // Arrange
        Empleado empleado = crearEmpleado();

        // Act
        // Assert
        assertThatThrownBy(() -> actualizarEmpleadoAdapter.actualizar(empleado))
                .isInstanceOf(EmpleadoNoExisteException.class)
                .hasMessage("El empleado no existe.");
    }

    @Test
    void deberiaEliminarUnEmpleadoExistente() {
        // Arrange
        Empleado empleado = crearEmpleado();
        guardarEmpleadoAdapter.guardar(empleado);

        // Act
        eliminarEmpleadoAdapter.eliminar(empleado.getDni());

        // Assert
        assertThatThrownBy(() -> obtenerEmpleadosAdapter.buscarPorId(empleado.getDni()))
                .isInstanceOf(EmpleadoNoExisteException.class);
    }

    @Test
    void deberiaRechazarEliminacionDeEmpleadoInexistente() {
        // Arrange
        String dniInexistente = String.valueOf(SECUENCIA_DNI.incrementAndGet());

        // Act
        // Assert
        assertThatThrownBy(() -> eliminarEmpleadoAdapter.eliminar(dniInexistente))
                .isInstanceOf(EmpleadoNoExisteException.class)
                .hasMessage("El empleado no existe.");
    }

    private static Empleado crearEmpleado() {
        String dni = String.valueOf(SECUENCIA_DNI.incrementAndGet());
        return Empleado.builder()
                .dni(dni)
                .nombre("Laura")
                .apellidos("Gomez Ruiz")
                .telefono("3001234567")
                .direccion("Calle 10 # 5-20")
                .cuentaBancaria("1234567890123")
                .categoria("PROFESIONAL")
                .build();
    }
}
