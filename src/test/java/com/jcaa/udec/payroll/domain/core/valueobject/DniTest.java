package com.jcaa.udec.payroll.domain.core.valueobject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jcaa.udec.payroll.domain.core.exception.EmpleadoInvalidoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class DniTest {

    @Test
    void deberiaCrearDniValido() {
        // Arrange
        // Act
        Dni dni = new Dni("1002003000");

        // Assert
        assertThat(dni.valor()).isEqualTo("1002003000");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"abc", "12345", "1234567890123", "12345-6789"})
    void deberiaRechazarDniInvalido(String valor) {
        // Arrange
        // Act
        // Assert
        assertThatThrownBy(() -> new Dni(valor)).isInstanceOf(EmpleadoInvalidoException.class);
    }
}
