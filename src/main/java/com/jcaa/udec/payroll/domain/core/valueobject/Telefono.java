package com.jcaa.udec.payroll.domain.core.valueobject;

import com.jcaa.udec.payroll.domain.core.exception.EmpleadoInvalidoException;
import java.util.Objects;

public record Telefono(String valor) {
    private static final int LONGITUD_MINIMA = 7;
    private static final int LONGITUD_MAXIMA = 15;

    public Telefono {
        if (Objects.isNull(valor) || !esValido(valor)) {
            throw new EmpleadoInvalidoException();
        }
    }

    private static boolean esValido(String valor) {
        String limpio = valor.replace("+", "").replace(" ", "").replace("-", "");
        if (limpio.length() < LONGITUD_MINIMA || limpio.length() > LONGITUD_MAXIMA) {
            return false;
        }
        for (int indice = 0; indice < limpio.length(); indice++) {
            if (!Character.isDigit(limpio.charAt(indice))) {
                return false;
            }
        }
        return true;
    }
}
