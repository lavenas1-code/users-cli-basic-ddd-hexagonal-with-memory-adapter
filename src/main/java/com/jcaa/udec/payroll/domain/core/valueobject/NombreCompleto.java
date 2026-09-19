package com.jcaa.udec.payroll.domain.core.valueobject;

import com.jcaa.udec.payroll.domain.core.exception.EmpleadoInvalidoException;
import java.util.Objects;

public record NombreCompleto(String nombre, String apellidos) {
    private static final int LONGITUD_MINIMA = 2;

    public NombreCompleto {
        if (esInvalido(nombre) || esInvalido(apellidos)) {
            throw new EmpleadoInvalidoException();
        }
    }

    private static boolean esInvalido(String valor) {
        return Objects.isNull(valor) || valor.isBlank() || valor.trim().length() < LONGITUD_MINIMA;
    }

    public String valorCompleto() {
        return "%s %s".formatted(nombre.trim(), apellidos.trim());
    }
}
