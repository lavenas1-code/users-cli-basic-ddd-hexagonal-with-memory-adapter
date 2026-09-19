package com.jcaa.udec.payroll.domain.core.valueobject;

import com.jcaa.udec.payroll.domain.core.exception.EmpleadoInvalidoException;
import java.util.Objects;

public record Direccion(String valor) {
    private static final int LONGITUD_MINIMA = 5;

    public Direccion {
        if (Objects.isNull(valor) || valor.isBlank() || valor.trim().length() < LONGITUD_MINIMA) {
            throw new EmpleadoInvalidoException();
        }
    }
}
