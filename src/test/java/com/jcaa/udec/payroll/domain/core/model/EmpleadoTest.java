package com.jcaa.udec.payroll.domain.core.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jcaa.udec.payroll.domain.core.exception.EmpleadoInvalidoException;
import java.math.BigDecimal;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class EmpleadoTest {
    private static final String DNI_VALIDO = "1002003000";
    private static final String NOMBRE_VALIDO = "Laura";
    private static final String APELLIDOS_VALIDOS = "Gomez Ruiz";
    private static final String TELEFONO_VALIDO = "3001234567";
    private static final String DIRECCION_VALIDA = "Calle 10 # 5-20";
    private static final String CUENTA_VALIDA = "1234567890123";
    private static final String CATEGORIA_VALIDA = "PROFESIONAL";
    private static final String MENSAJE_DATOS_INVALIDOS = "Los datos del empleado son invalidos.";

    @Test
    void deberiaCrearEmpleadoConBuilderYCalcularSueldoBaseSegunCategoria() {
        // Arrange
        // Act
        Empleado empleado = Empleado.builder()
                .dni(DNI_VALIDO)
                .nombre(NOMBRE_VALIDO)
                .apellidos(APELLIDOS_VALIDOS)
                .telefono(TELEFONO_VALIDO)
                .direccion(DIRECCION_VALIDA)
                .cuentaBancaria(CUENTA_VALIDA)
                .categoria(CATEGORIA_VALIDA)
                .build();

        // Assert
        assertThat(empleado)
                .extracting(Empleado::getDni, Empleado::getNombreCompleto, Empleado::getCategoria)
                .containsExactly(DNI_VALIDO, NOMBRE_VALIDO + " " + APELLIDOS_VALIDOS, CATEGORIA_VALIDA);
        assertThat(empleado.getSueldoBase()).isEqualByComparingTo(new BigDecimal("2800000"));
    }

    @ParameterizedTest
    @MethodSource("datosInvalidos")
    void deberiaRechazarEmpleadoConDatosInvalidos(
            String dni, String nombre, String apellidos, String telefono,
            String direccion, String cuenta, String categoria) {
        // Arrange
        // Act
        // Assert
        assertThatThrownBy(() -> new Empleado(dni, nombre, apellidos, telefono, direccion, cuenta, categoria))
                .isInstanceOf(EmpleadoInvalidoException.class)
                .hasMessage(MENSAJE_DATOS_INVALIDOS);
    }

    private static Stream<Arguments> datosInvalidos() {
        return Stream.of(
                Arguments.of("ab", NOMBRE_VALIDO, APELLIDOS_VALIDOS, TELEFONO_VALIDO,
                        DIRECCION_VALIDA, CUENTA_VALIDA, CATEGORIA_VALIDA),
                Arguments.of(DNI_VALIDO, "L", APELLIDOS_VALIDOS, TELEFONO_VALIDO,
                        DIRECCION_VALIDA, CUENTA_VALIDA, CATEGORIA_VALIDA),
                Arguments.of(DNI_VALIDO, NOMBRE_VALIDO, APELLIDOS_VALIDOS, "12",
                        DIRECCION_VALIDA, CUENTA_VALIDA, CATEGORIA_VALIDA),
                Arguments.of(DNI_VALIDO, NOMBRE_VALIDO, APELLIDOS_VALIDOS, TELEFONO_VALIDO,
                        "Ca", CUENTA_VALIDA, CATEGORIA_VALIDA),
                Arguments.of(DNI_VALIDO, NOMBRE_VALIDO, APELLIDOS_VALIDOS, TELEFONO_VALIDO,
                        DIRECCION_VALIDA, "123", CATEGORIA_VALIDA),
                Arguments.of(DNI_VALIDO, NOMBRE_VALIDO, APELLIDOS_VALIDOS, TELEFONO_VALIDO,
                        DIRECCION_VALIDA, CUENTA_VALIDA, "NO_EXISTE"));
    }
}
