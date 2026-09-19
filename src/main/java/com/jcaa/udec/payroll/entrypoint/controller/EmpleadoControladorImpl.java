package com.jcaa.udec.payroll.entrypoint.controller;

import com.jcaa.udec.payroll.application.service.dto.command.ActualizarEmpleadoComando;
import com.jcaa.udec.payroll.application.service.dto.command.CrearEmpleadoComando;
import com.jcaa.udec.payroll.application.service.dto.command.EliminarEmpleadoComando;
import com.jcaa.udec.payroll.application.service.dto.query.ObtenerEmpleadoConsulta;
import com.jcaa.udec.payroll.application.service.ports.in.ActualizarEmpleadoUseCase;
import com.jcaa.udec.payroll.application.service.ports.in.AgregarEmpleadoUseCase;
import com.jcaa.udec.payroll.application.service.ports.in.EliminarEmpleadoUseCase;
import com.jcaa.udec.payroll.application.service.ports.in.ObtenerEmpleadoUseCase;
import com.jcaa.udec.payroll.entrypoint.controller.dto.request.ActualizarEmpleadoPeticion;
import com.jcaa.udec.payroll.entrypoint.controller.dto.request.RegistrarEmpleadoPeticion;
import com.jcaa.udec.payroll.entrypoint.controller.dto.response.ObtenerEmpleadoResponse;
import com.jcaa.udec.payroll.entrypoint.controller.mapper.EmpleadoResponseMapper;

public class EmpleadoControladorImpl implements EmpleadoControlador {
    private final AgregarEmpleadoUseCase agregarEmpleadoUseCase;
    private final ObtenerEmpleadoUseCase obtenerEmpleadoUseCase;
    private final ActualizarEmpleadoUseCase actualizarEmpleadoUseCase;
    private final EliminarEmpleadoUseCase eliminarEmpleadoUseCase;

    public EmpleadoControladorImpl(
            AgregarEmpleadoUseCase agregarEmpleadoUseCase,
            ObtenerEmpleadoUseCase obtenerEmpleadoUseCase,
            ActualizarEmpleadoUseCase actualizarEmpleadoUseCase,
            EliminarEmpleadoUseCase eliminarEmpleadoUseCase) {
        this.agregarEmpleadoUseCase = agregarEmpleadoUseCase;
        this.obtenerEmpleadoUseCase = obtenerEmpleadoUseCase;
        this.actualizarEmpleadoUseCase = actualizarEmpleadoUseCase;
        this.eliminarEmpleadoUseCase = eliminarEmpleadoUseCase;
    }

    @Override
    public void registrar(RegistrarEmpleadoPeticion peticion) {
        CrearEmpleadoComando comando = new CrearEmpleadoComando(
                peticion.dni(),
                peticion.nombre(),
                peticion.apellidos(),
                peticion.telefono(),
                peticion.direccion(),
                peticion.cuentaBancaria(),
                peticion.categoria());
        agregarEmpleadoUseCase.guardar(comando);
    }

    @Override
    public ObtenerEmpleadoResponse obtenerPorId(String dni) {
        ObtenerEmpleadoConsulta consulta = new ObtenerEmpleadoConsulta(dni);
        return EmpleadoResponseMapper.mapearAResponse(obtenerEmpleadoUseCase.obtenerPorId(consulta));
    }

    @Override
    public ObtenerEmpleadoResponse obtenerTodos() {
        return EmpleadoResponseMapper.mapearAResponse(obtenerEmpleadoUseCase.obtenerTodos());
    }

    @Override
    public void actualizar(ActualizarEmpleadoPeticion peticion) {
        ActualizarEmpleadoComando comando = new ActualizarEmpleadoComando(
                peticion.dni(),
                peticion.nombre(),
                peticion.apellidos(),
                peticion.telefono(),
                peticion.direccion(),
                peticion.cuentaBancaria(),
                peticion.categoria());
        actualizarEmpleadoUseCase.actualizar(comando);
    }

    @Override
    public void eliminar(String dni) {
        eliminarEmpleadoUseCase.eliminar(new EliminarEmpleadoComando(dni));
    }
}
