package com.jcaa.udec.payroll.domain.core.exception;

public class EmpleadoYaExisteException extends RuntimeException {
    private static final String MENSAJE_ERROR = "El empleado ya existe.";

    public EmpleadoYaExisteException() {
        super(MENSAJE_ERROR);
    }
}
