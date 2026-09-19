package com.jcaa.udec.payroll.domain.core.valueobject;

import com.jcaa.udec.payroll.domain.core.exception.EmpleadoInvalidoException;
import java.util.Objects;

public record Dni(String valor) {
    private static final int LONGITUD_MINIMA = 6;
    private static final int LONGITUD_MAXIMA = 12;

    public Dni {
        if (Objects.isNull(valor) || valor.isBlank() || !esValido(valor)) {
            throw new EmpleadoInvalidoException();
        }
    }

    private static boolean esValido(String valor) {
        String limpio = valor.trim();
        if (limpio.length() < LONGITUD_MINIMA || limpio.length() > LONGITUD_MAXIMA) {
            return false;
        }
        for (int indice = 0; indice < limpio.length(); indice++) {
            if (!Character.isLetterOrDigit(limpio.charAt(indice))) {
                return false;
            }
        }
        return true;
    }
}
