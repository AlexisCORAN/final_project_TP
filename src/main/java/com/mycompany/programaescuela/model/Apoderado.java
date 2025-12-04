/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.programaescuela.model;

/**
 *
 * @author Alexis
 */
public class Apoderado {
    private String nombre;
    private String apellido;
    private char genero;
    private String telefono;

    public Apoderado() {}

    public Apoderado(String nombre, String apellido, char genero, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.genero = genero;
        this.telefono = telefono;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
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
    
}
