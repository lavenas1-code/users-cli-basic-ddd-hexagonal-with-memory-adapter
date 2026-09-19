package com.jcaa.udec.payroll.domain.core.valueobject;

import com.jcaa.udec.payroll.domain.core.exception.EmpleadoInvalidoException;
import java.math.BigDecimal;

/**
 * Representa la categoria contractual del empleado. Cada categoria tiene
 * asociado un sueldo base predefinido, tal como lo exige el caso de uso de
 * nomina (el sueldo base de un empleado depende de su categoria).
 */
public enum Categoria {
    AUXILIAR(new BigDecimal("1300000")),
    TECNICO(new BigDecimal("1900000")),
    PROFESIONAL(new BigDecimal("2800000")),
    COORDINADOR(new BigDecimal("3900000")),
    GERENCIAL(new BigDecimal("5500000"));

    private final BigDecimal sueldoBase;

    Categoria(BigDecimal sueldoBase) {
        this.sueldoBase = sueldoBase;
    }

    public BigDecimal sueldoBase() {
        return sueldoBase;
    }

    public static Categoria desdeTexto(String valor) {
        try {
            return Categoria.valueOf(valor.trim().toUpperCase());
        } catch (Exception excepcion) {
            throw new EmpleadoInvalidoException();
        }
    }
}
