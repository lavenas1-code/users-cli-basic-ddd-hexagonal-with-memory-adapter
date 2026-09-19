package com.jcaa.udec.payroll.entrypoint.cli;

import com.jcaa.udec.payroll.domain.core.exception.EmpleadoInvalidoException;
import com.jcaa.udec.payroll.domain.core.exception.EmpleadoNoExisteException;
import com.jcaa.udec.payroll.domain.core.exception.EmpleadoYaExisteException;
import com.jcaa.udec.payroll.domain.core.valueobject.Categoria;
import com.jcaa.udec.payroll.domain.core.valueobject.CuentaBancaria;
import com.jcaa.udec.payroll.domain.core.valueobject.Direccion;
import com.jcaa.udec.payroll.domain.core.valueobject.Dni;
import com.jcaa.udec.payroll.domain.core.valueobject.NombreCompleto;
import com.jcaa.udec.payroll.domain.core.valueobject.Telefono;
import com.jcaa.udec.payroll.entrypoint.controller.EmpleadoControlador;
import com.jcaa.udec.payroll.entrypoint.controller.dto.request.ActualizarEmpleadoPeticion;
import com.jcaa.udec.payroll.entrypoint.controller.dto.request.RegistrarEmpleadoPeticion;
import com.jcaa.udec.payroll.entrypoint.controller.dto.response.ObtenerEmpleadoResponse;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Adaptador de entrada (CLI) para el modulo de nomina. Sigue el mismo patron
 * que {@code GuiCli} del modulo de usuarios: captura y valida datos en el
 * borde de la aplicacion y delega toda la logica de negocio al controlador.
 */
public class EmpleadoCli {
    private static final int OPCION_AGREGAR = 1;
    private static final int OPCION_BUSCAR = 2;
    private static final int OPCION_ACTUALIZAR = 3;
    private static final int OPCION_ELIMINAR = 4;
    private static final int OPCION_LISTAR = 5;
    private static final int OPCION_VOLVER = 6;
    private static final String TEXTO_TITULO = "** GESTION DE EMPLEADOS - MODULO DE NOMINA **";
    private static final String TITULO_REGISTRO = "** INGRESE LOS DATOS DEL NUEVO EMPLEADO **";
    private static final String TITULO_ACTUALIZACION = "** INGRESE LOS NUEVOS DATOS DEL EMPLEADO **";
    private static final String SEPARADOR = "- - - - - - - - - ";
    private static final String OPCIONES = "Opciones:";
    private static final String TEXTO_OPCION_AGREGAR = "1 - Agregar (Create)";
    private static final String TEXTO_OPCION_BUSCAR = "2 - Buscar por DNI (Read)";
    private static final String TEXTO_OPCION_ACTUALIZAR = "3 - Actualizar (Update)";
    private static final String TEXTO_OPCION_ELIMINAR = "4 - Eliminar (Delete)";
    private static final String TEXTO_OPCION_LISTAR = "5 - Listar todos (List)";
    private static final String TEXTO_OPCION_VOLVER = "6 - Volver al menu principal";
    private static final String TEXTO_SOLICITUD_OPCION = "Ingrese el numero de la opcion: ";
    private static final String SOLICITUD_DNI = "DNI: ";
    private static final String SOLICITUD_NOMBRE = "NOMBRE: ";
    private static final String SOLICITUD_APELLIDOS = "APELLIDOS: ";
    private static final String SOLICITUD_TELEFONO = "TELEFONO: ";
    private static final String SOLICITUD_DIRECCION = "DIRECCION: ";
    private static final String SOLICITUD_CUENTA = "CUENTA BANCARIA: ";
    private static final String SOLICITUD_CATEGORIA =
            "CATEGORIA %s: ".formatted(Arrays.toString(Categoria.values()));
    private static final String MENSAJE_OPCION_INVALIDA = "Opcion [%s] invalida";
    private static final String MENSAJE_DNI_INVALIDO = "DNI INVALIDO: debe tener entre 6 y 12 caracteres alfanumericos";
    private static final String MENSAJE_NOMBRE_INVALIDO = "NOMBRE/APELLIDOS INVALIDO: minimo 2 caracteres";
    private static final String MENSAJE_TELEFONO_INVALIDO = "TELEFONO INVALIDO: solo numeros, entre 7 y 15 digitos";
    private static final String MENSAJE_DIRECCION_INVALIDA = "DIRECCION INVALIDA: minimo 5 caracteres";
    private static final String MENSAJE_CUENTA_INVALIDA = "CUENTA INVALIDA: entre 10 y 24 caracteres alfanumericos";
    private static final String MENSAJE_CATEGORIA_INVALIDA =
            "CATEGORIA INVALIDA: use una de las opciones indicadas";
    private static final String MENSAJE_ERROR = "ERROR: ";
    private static final String MENSAJE_REGISTRO_EXITOSO = "Empleado registrado correctamente.";
    private static final String MENSAJE_ACTUALIZACION_EXITOSA = "Empleado actualizado correctamente.";
    private static final String MENSAJE_ELIMINACION_EXITOSA = "Empleado eliminado correctamente.";
    private static final String MENSAJE_LISTA_VACIA = "No hay empleados registrados.";
    private static final String MARCA_ORDEN_BYTES = "\uFEFF";
    private static final String TEXTO_VACIO = "";
    private final EmpleadoControlador empleadoControlador;
    private final Scanner entrada;

    public EmpleadoCli(EmpleadoControlador empleadoControlador) {
        this(empleadoControlador, new Scanner(System.in));
    }

    EmpleadoCli(EmpleadoControlador empleadoControlador, Scanner entrada) {
        this.empleadoControlador = empleadoControlador;
        this.entrada = entrada;
    }

    public void ejecutarAccion() {
        boolean continuar = true;
        while (continuar) {
            int opcion = obtenerOpcionMenu();
            try {
                switch (opcion) {
                    case OPCION_AGREGAR -> registrarEmpleado();
                    case OPCION_BUSCAR -> mostrarEmpleadoPorId();
                    case OPCION_ACTUALIZAR -> actualizarEmpleado();
                    case OPCION_ELIMINAR -> eliminarEmpleado();
                    case OPCION_LISTAR -> mostrarTodosLosEmpleados();
                    case OPCION_VOLVER -> continuar = false;
                }
            } catch (EmpleadoInvalidoException
                    | EmpleadoNoExisteException
                    | EmpleadoYaExisteException excepcion) {
                System.out.println(MENSAJE_ERROR + excepcion.getMessage());
            }
        }
    }

    private int obtenerOpcionMenu() {
        do {
            mostrarMenu();
            String valorIngresado = limpiarEntrada(entrada.nextLine());
            try {
                int opcion = Integer.parseInt(valorIngresado);
                if (opcion >= OPCION_AGREGAR && opcion <= OPCION_VOLVER) {
                    return opcion;
                }
            } catch (NumberFormatException excepcion) {
                // El flujo informa el valor invalido y vuelve a mostrar el menu.
            }
            System.out.printf(MENSAJE_OPCION_INVALIDA + "%n", valorIngresado);
        } while (true);
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println(TEXTO_TITULO);
        System.out.println(SEPARADOR);
        System.out.println(OPCIONES);
        System.out.println(SEPARADOR);
        System.out.println(TEXTO_OPCION_AGREGAR);
        System.out.println(TEXTO_OPCION_BUSCAR);
        System.out.println(TEXTO_OPCION_ACTUALIZAR);
        System.out.println(TEXTO_OPCION_ELIMINAR);
        System.out.println(TEXTO_OPCION_LISTAR);
        System.out.println(TEXTO_OPCION_VOLVER);
        System.out.print(TEXTO_SOLICITUD_OPCION);
    }

    private void registrarEmpleado() {
        System.out.println();
        System.out.println(TITULO_REGISTRO);
        empleadoControlador.registrar(new RegistrarEmpleadoPeticion(
                capturarDni(),
                capturarNombre(),
                capturarApellidos(),
                capturarTelefono(),
                capturarDireccion(),
                capturarCuenta(),
                capturarCategoria()));
        System.out.println(MENSAJE_REGISTRO_EXITOSO);
    }

    private void mostrarEmpleadoPorId() {
        System.out.print(SOLICITUD_DNI);
        String dni = limpiarEntrada(entrada.nextLine());
        System.out.println(empleadoControlador.obtenerPorId(dni));
    }

    private void actualizarEmpleado() {
        System.out.println();
        System.out.println(TITULO_ACTUALIZACION);
        System.out.print(SOLICITUD_DNI);
        String dni = limpiarEntrada(entrada.nextLine());
        empleadoControlador.actualizar(new ActualizarEmpleadoPeticion(
                dni,
                capturarNombre(),
                capturarApellidos(),
                capturarTelefono(),
                capturarDireccion(),
                capturarCuenta(),
                capturarCategoria()));
        System.out.println(MENSAJE_ACTUALIZACION_EXITOSA);
    }

    private void eliminarEmpleado() {
        System.out.print(SOLICITUD_DNI);
        String dni = limpiarEntrada(entrada.nextLine());
        empleadoControlador.eliminar(dni);
        System.out.println(MENSAJE_ELIMINACION_EXITOSA);
    }

    private void mostrarTodosLosEmpleados() {
        ObtenerEmpleadoResponse response = empleadoControlador.obtenerTodos();
        if (response.estaVacia()) {
            System.out.println(MENSAJE_LISTA_VACIA);
            return;
        }
        System.out.println(response);
    }

    private String capturarDni() {
        do {
            System.out.print(SOLICITUD_DNI);
            String dni = limpiarEntrada(entrada.nextLine());
            if (esValido(() -> new Dni(dni))) {
                return dni;
            }
            System.out.println(MENSAJE_DNI_INVALIDO);
        } while (true);
    }

    private String capturarNombre() {
        do {
            System.out.print(SOLICITUD_NOMBRE);
            String nombre = limpiarEntrada(entrada.nextLine());
            if (esValido(() -> new NombreCompleto(nombre, "temporal"))) {
                return nombre;
            }
            System.out.println(MENSAJE_NOMBRE_INVALIDO);
        } while (true);
    }

    private String capturarApellidos() {
        do {
            System.out.print(SOLICITUD_APELLIDOS);
            String apellidos = limpiarEntrada(entrada.nextLine());
            if (esValido(() -> new NombreCompleto("temporal", apellidos))) {
                return apellidos;
            }
            System.out.println(MENSAJE_NOMBRE_INVALIDO);
        } while (true);
    }

    private String capturarTelefono() {
        do {
            System.out.print(SOLICITUD_TELEFONO);
            String telefono = limpiarEntrada(entrada.nextLine());
            if (esValido(() -> new Telefono(telefono))) {
                return telefono;
            }
            System.out.println(MENSAJE_TELEFONO_INVALIDO);
        } while (true);
    }

    private String capturarDireccion() {
        do {
            System.out.print(SOLICITUD_DIRECCION);
            String direccion = limpiarEntrada(entrada.nextLine());
            if (esValido(() -> new Direccion(direccion))) {
                return direccion;
            }
            System.out.println(MENSAJE_DIRECCION_INVALIDA);
        } while (true);
    }

    private String capturarCuenta() {
        do {
            System.out.print(SOLICITUD_CUENTA);
            String cuenta = limpiarEntrada(entrada.nextLine());
            if (esValido(() -> new CuentaBancaria(cuenta))) {
                return cuenta;
            }
            System.out.println(MENSAJE_CUENTA_INVALIDA);
        } while (true);
    }

    private String capturarCategoria() {
        do {
            System.out.print(SOLICITUD_CATEGORIA);
            String categoria = limpiarEntrada(entrada.nextLine());
            if (esValido(() -> Categoria.desdeTexto(categoria))) {
                return categoria;
            }
            System.out.println(MENSAJE_CATEGORIA_INVALIDA);
        } while (true);
    }

    private static String limpiarEntrada(String valor) {
        return valor.replace(MARCA_ORDEN_BYTES, TEXTO_VACIO).trim();
    }

    private static boolean esValido(Runnable validacion) {
        try {
            validacion.run();
            return true;
        } catch (EmpleadoInvalidoException excepcion) {
            return false;
        }
    }
}
