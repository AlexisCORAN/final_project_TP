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
    // Atributos privados
    private String dni;
    private String nombres;
    private String apellidos;
    private String correo;   
    private String telefono; 
    private double nota1, nota2, nota3, nota4;
    private double promedio;
    private String estado;

    // Constructor
    public Alumno(String dni, String nombres, String apellidos, String correo, String telefono,
                  double n1, double n2, double n3, double n4) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefono = telefono;
        this.nota1 = n1;
        this.nota2 = n2;
        this.nota3 = n3;
        this.nota4 = n4;
        this.calcularPromedio();
    }

    private void calcularPromedio() {
        this.promedio = (nota1 + nota2 + nota3 + nota4) / 4.0;
        this.estado = (this.promedio >= 11.5) ? "APROBADO" : "DESAPROBADO";
    }

    public String getDni() { return dni; }
    public String getNombreCompleto() { return nombres + " " + apellidos; }
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

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public void setNota1(double n1) {
        this.nota1 = n1; calcularPromedio(); }
    public void setNota2(double n2) { 
       this.nota2 = n2; calcularPromedio();
    }
    public void setNota3(double n3) { 
        this.nota3 = n3; calcularPromedio();
    } 
    public void setNota4(double n4) {
        this.nota4 = n4; calcularPromedio();
    } 

    public String generarLineaCSV() {
        StringBuilder sb = new StringBuilder();

        sb.append(escapeCsv(dni)).append(",");
        sb.append(escapeCsv(nombres)).append(",");
        sb.append(escapeCsv(apellidos)).append(",");
        sb.append(escapeCsv(telefono == null ? "" : telefono)).append(",");
        sb.append(escapeCsv(correo == null ? "" : correo)).append(",");
        sb.append(nota1).append(",");
        sb.append(nota2).append(",");
        sb.append(nota3).append(",");
        sb.append(nota4);
        return sb.toString();
    }

    private String escapeCsv(String s) {
        if (s == null) return "";
        String out = s.replace("\"", "\"\"");
        if (out.contains(",") || out.contains("\"") || out.contains("\n") || out.contains("\r")) {
            return "\"" + out + "\"";
        }
        return out;
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return String.format("DNI: %-10s | Alumno: %-30s | Promedio: %05.2f | Estado: %s", 
                             dni, getNombreCompleto(), promedio, estado);
    }
}