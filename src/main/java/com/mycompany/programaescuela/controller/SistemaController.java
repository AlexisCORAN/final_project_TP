/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.programaescuela.controller;
import com.mycompany.programaescuela.SistemaNotas;
import com.mycompany.programaescuela.model.AlumnoDTO;
import com.mycompany.programaescuela.model.RegistroAcademico;
import com.mycompany.programaescuela.model.Apoderado;
import com.mycompany.programaescuela.model.Grado;
import com.mycompany.programaescuela.model.Alumno;
import com.mycompany.programaescuela.validadorAlumno;
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
            return false;
        }
    }

    public boolean guardarDatos(String archivo) {
        try {
            sistema.guardarCSV(archivo);
            return true;
        } catch (Exception e) {
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

    public String obtenerReporteEstadisticas() {
        return sistema.generarReporteEstadisticas();
    }


    public String guardarAlumno(AlumnoDTO dto) {

        // 1. VALIDAR DATOS DEL DTO (vista)
        String error = validadorAlumno.validar(dto);
        if (error != null) return error;

        // 2. OBJETOS DEL MODELO
        Apoderado apo = new Apoderado(
                dto.apoNombre,
                dto.apoApellido,
                dto.apoGenero,
                dto.apoTelefono
        );

        RegistroAcademico reg = new RegistroAcademico(
                dto.nota1, dto.nota2, dto.nota3, dto.nota4,
                dto.asistencia,
                dto.comportamiento
        );

        Grado grado = new Grado(
                dto.nivel,
                dto.gradoText,
                dto.seccion
        );

        // 3. VER SI EXISTE
        Alumno existente = buscarAlumnoPorDni(dto.dni);

        if (existente != null) {

            // 4. ACTUALIZAR DATOS
            existente.setNombres(dto.nombres);
            existente.setApellidos(dto.apellidos);
            existente.setGenero(dto.genero);

            existente.setTelefono(dto.telefono);
            existente.setCorreo(dto.correo);
            existente.setDireccion(dto.direccion);

            existente.setApoderado(apo);
            existente.setGrado(grado);

            RegistroAcademico r = existente.getRegistroAcademico();
            r.setNota1(dto.nota1);
            r.setNota2(dto.nota2);
            r.setNota3(dto.nota3);
            r.setNota4(dto.nota4);
            r.setAsistencia(dto.asistencia);
            r.setComportamiento(dto.comportamiento);

            existente.setRetirado(dto.retirado);

            return "ACTUALIZADO";
        }

        // 5. CREAR NUEVO
        Alumno nuevo = new Alumno(
                dto.dni, dto.nombres, dto.apellidos, dto.genero,
                dto.telefono, dto.correo, dto.direccion,
                apo, grado, reg
        );

        nuevo.setRetirado(dto.retirado);

        registrarAlumno(nuevo);

        return "REGISTRADO";
    }
}