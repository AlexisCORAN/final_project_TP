/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.programaescuela;

/**
 *
 * @author Alexis
 */

public class Alumno {

    // Datos personales
    private String dni;
    private String nombres;
    private String apellidos;
    private char genero;
    private Grado grado;

    // Información de contacto
    private String telefono;
    private String correo;
    private String direccion;

    // Apoderado
    private Apoderado apoderado;

    private RegistroAcademico registroAcademico;

    // Estado del alumno
    private boolean retirado = false;

    public Alumno() { }

    public Alumno(String dni, String nombres, String apellidos, char genero,
              String telefono, String correo, String direccion,
              Apoderado apoderado, Grado grado,
              RegistroAcademico registroAcademico){

        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.genero = genero;

        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;

        this.apoderado = apoderado;

        this.registroAcademico = registroAcademico;
        this.grado = grado;
    }

    // -------------------- GETTERS Y SETTERS -------------------

    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombres() {
        return nombres;
    }
    
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public char getGenero() {
        return genero;
    }
    
    public void setGenero(char genero) {
        this.genero = genero;
    }

    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Apoderado getApoderado() {
        return apoderado;
    }
    
    public void setApoderado(Apoderado apoderado) { this.apoderado = apoderado; }
    
    public RegistroAcademico getRegistroAcademico() {
        return registroAcademico;
    }

    public void setRegistroAcademico(RegistroAcademico registroAcademico) {
        this.registroAcademico = registroAcademico;
    }
    
    public Grado getGrado() {
        return grado;
    }

    public void setGrado(Grado grado) {
        this.grado = grado;
    }

    public boolean isRetirado() { return retirado; }
    public void setRetirado(boolean retirado) { this.retirado = retirado; }

    @Override
    public String toString() {
        return dni + " - " + apellidos + ", " + nombres;
    }
}