package com.jcaa.udec.payroll.domain.core.model;

import com.jcaa.udec.payroll.domain.core.valueobject.Categoria;
import com.jcaa.udec.payroll.domain.core.valueobject.CuentaBancaria;
import com.jcaa.udec.payroll.domain.core.valueobject.Direccion;
import com.jcaa.udec.payroll.domain.core.valueobject.Dni;
import com.jcaa.udec.payroll.domain.core.valueobject.NombreCompleto;
import com.jcaa.udec.payroll.domain.core.valueobject.Telefono;
import java.math.BigDecimal;
import lombok.Builder;

/**
 * Entidad raiz del agregado de nomina para la CEA de "Gestion de Nominas".
 * Modela los datos personales y de contrato necesarios para liquidar el
 * sueldo base de un empleado, manteniendo la logica de negocio desacoplada
 * de cualquier framework o adaptador (Arquitectura Hexagonal / DDD).
 */
public class Empleado {
    private final Dni dni;
    private final NombreCompleto nombreCompleto;
    private final Telefono telefono;
    private final Direccion direccion;
    private final CuentaBancaria cuentaBancaria;
    private final Categoria categoria;

    @Builder
    public Empleado(
            String dni,
            String nombre,
            String apellidos,
            String telefono,
            String direccion,
            String cuentaBancaria,
            String categoria) {
        this.dni = new Dni(dni);
        this.nombreCompleto = new NombreCompleto(nombre, apellidos);
        this.telefono = new Telefono(telefono);
        this.direccion = new Direccion(direccion);
        this.cuentaBancaria = new CuentaBancaria(cuentaBancaria);
        this.categoria = Categoria.desdeTexto(categoria);
    }

    public String getDni() {
        return dni.valor();
    }

    public String getNombre() {
        return nombreCompleto.nombre();
    }

    public String getApellidos() {
        return nombreCompleto.apellidos();
    }

    public String getNombreCompleto() {
        return nombreCompleto.valorCompleto();
    }

    public String getTelefono() {
        return telefono.valor();
    }

    public String getDireccion() {
        return direccion.valor();
    }

    public String getCuentaBancaria() {
        return cuentaBancaria.valor();
    }

    public String getCategoria() {
        return categoria.name();
    }

    public BigDecimal getSueldoBase() {
        return categoria.sueldoBase();
    }
}
