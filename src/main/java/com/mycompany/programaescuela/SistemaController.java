/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.programaescuela;
import java.util.List;

/**
 *
 * @author Alexis
 */
public class SistemaController {

    private final SistemaNotas sistema;

    public SistemaController() {
        this.sistema = new SistemaNotas();
    }

    // ----------------- CARGA Y GUARDADO -----------------
    public boolean cargarDatos(String archivo) {
        try {
            sistema.cargarDesdeCSV(archivo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean guardarDatos(String archivo) {
        try {
            sistema.guardarCSV(archivo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ----------------- ALUMNOS -----------------
    public List<Alumno> obtenerTodosAlumnos() {
        return sistema.getAlumnos();
    }

    public Alumno buscarAlumnoPorDni(String dni) {
        return sistema.buscarPorDni(dni);
    }

    public List<Alumno> buscarAlumnoPorNombre(String nombre) {
        return sistema.buscarPorNombre(nombre);
    }

    public void registrarAlumno(Alumno alumno) {
        sistema.agregarAlumno(alumno);
    }

    public void retirarAlumno(String dni) {
        sistema.retirarAlumno(dni);
    }

    // ----------------- VALIDACIONES -----------------
    public boolean validarDNI(String dni) {
        return SistemaNotas.validarDNI(dni);
    }

    public boolean validarTelefono(String telefono) {
        return SistemaNotas.validarTelefono(telefono);
    }

    public boolean validarCorreo(String correo) {
        return SistemaNotas.validarCorreo(correo);
    }

    public boolean validarNota(String nota) {
        return SistemaNotas.validarNota(nota);
    }

    public boolean validarAsistencia(String asistencia) {
        return SistemaNotas.validarAsistencia(asistencia);
    }

    public boolean validarNyA(String nombre) {
        return SistemaNotas.validarNyA(nombre);
    }

    public boolean validarDireccion(String direccion) {
        return SistemaNotas.validarDireccion(direccion);
    }
    
    public int convertirGrado(String grado) {
        try {
            return sistema.convertirGradoInterno(grado);
        } catch (IllegalArgumentException e) {
            System.out.println("Grado inválido: " + grado);
            return -1; // o lanzar excepción según tu lógica
        }
    }
    
    public String obtenerReporteEstadisticas() {
       
        return sistema.generarReporteEstadisticas(); 
       
    }

}
