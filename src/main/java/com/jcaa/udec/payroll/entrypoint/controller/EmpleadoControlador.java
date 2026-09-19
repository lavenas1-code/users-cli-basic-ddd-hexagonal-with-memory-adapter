package com.jcaa.udec.payroll.entrypoint.controller;

import com.jcaa.udec.payroll.entrypoint.controller.dto.request.ActualizarEmpleadoPeticion;
import com.jcaa.udec.payroll.entrypoint.controller.dto.request.RegistrarEmpleadoPeticion;
import com.jcaa.udec.payroll.entrypoint.controller.dto.response.ObtenerEmpleadoResponse;

public interface EmpleadoControlador {
    void registrar(RegistrarEmpleadoPeticion peticion);

    ObtenerEmpleadoResponse obtenerPorId(String dni);

    ObtenerEmpleadoResponse obtenerTodos();

    void actualizar(ActualizarEmpleadoPeticion peticion);

    void eliminar(String dni);
}
