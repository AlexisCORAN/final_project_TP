/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.programaescuela.model;

/**
 *
 * @author Alexis
 */
public class AlumnoDTO {
    public String dni;
    public String nombres;
    public String apellidos;
    public char genero;

    public String telefono;
    public String correo;
    public String direccion;

    // APODERADO
    public String apoNombre;
    public String apoApellido;
    public char apoGenero;
    public String apoTelefono;

    // GRADO
    public String nivel;
    public String gradoText;
    public String seccion;

    // REGISTRO ACADÉMICO
    public double nota1;
    public double nota2;
    public double nota3;
    public double nota4;
    public double asistencia;
    public String comportamiento;

    public boolean retirado;
}
