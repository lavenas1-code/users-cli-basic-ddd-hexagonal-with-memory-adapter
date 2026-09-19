package com.jcaa.udec.payroll.domain.core.exception;

public class EmpleadoInvalidoException extends RuntimeException {
    private static final String MENSAJE_ERROR = "Los datos del empleado son invalidos.";

    public EmpleadoInvalidoException() {
        super(MENSAJE_ERROR);
    }
}
