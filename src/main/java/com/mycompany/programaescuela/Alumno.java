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

    private String dni;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private double nota1, nota2, nota3, nota4;
    private double promedio;
    private boolean activo;

    public Alumno(String dni, String nombre, String apellido, String correo, String telefono,
                  double n1, double n2, double n3, double n4, String retirado) {

        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;

        this.nota1 = n1;
        this.nota2 = n2;
        this.nota3 = n3;
        this.nota4 = n4;

        this.activo = (retirado == null || retirado.equalsIgnoreCase("No"));

        recalcularPromedio();
    }


    private void recalcularPromedio() {
        this.promedio = (nota1 + nota2 + nota3 + nota4) / 4.0;
    }

    public String getEstadoAcademico() {
        return (promedio >= 11.5) ? "APROBADO" : "DESAPROBADO";
    }

    public boolean estaActivo() {
        return activo;
    }

    public String getSituacionTexto() {
        return activo ? "ACTIVO" : "RETIRADO";
    }


    // ---------- Getters ----------
    public String getDni() { return dni; }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    public double getPromedio() {
        return promedio;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }
    
    public double getNota1() {
        return nota1;
    }

    public double getNota2() {
        return nota2;
    }
    public double getNota3() {
        return nota3;
    }
    public double getNota4() {
        return nota4;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setNota1(double n1) {
        this.nota1 = n1;
        recalcularPromedio();
    }

    public void setNota2(double n2) {
        this.nota2 = n2;
        recalcularPromedio();
    }

    public void setNota3(double n3) {
        this.nota3 = n3;
        recalcularPromedio();
    }

    public void setNota4(double n4) {
        this.nota4 = n4;
        recalcularPromedio();
    }


    public String generarLineaCSV() {
        return String.format("%s,%s,%s,%s,%s,%.2f,%.2f,%.2f,%.2f",
                dni,
                nombre,
                apellido,
                telefono == null ? "" : telefono,
                correo == null ? "" : correo,
                nota1, nota2, nota3, nota4
        );
    }


    // ---------- Representación de texto ----------
    @Override
    public String toString() {
        return String.format(
            "DNI: %-10s | Alumno: %-30s | Promedio: %05.2f | Estado Académico: %s | Situación: %s",
            dni,
            getNombreCompleto(),
            promedio,
            getEstadoAcademico(),
            getSituacionTexto()
        );
    }
}
