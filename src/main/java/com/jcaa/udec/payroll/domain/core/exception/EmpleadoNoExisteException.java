package com.jcaa.udec.payroll.domain.core.exception;

public class EmpleadoNoExisteException extends RuntimeException {
    private static final String MENSAJE_ERROR = "El empleado no existe.";

    public EmpleadoNoExisteException() {
        super(MENSAJE_ERROR);
    }
}
