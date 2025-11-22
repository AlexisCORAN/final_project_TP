/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

/**
 *
 * @author tiago
 */
public class Alumno {
    
    private String dni;
    private String nombres;
    private String apellidos;
    private double nota1, nota2, nota3, nota4;
    private double promedio;
    private String estado;

    public Alumno(String dni, String nombres, String apellidos, 
                      double n1, double n2, double n3, double n4) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.nota1 = n1;
        this.nota2 = n2;
        this.nota3 = n3;
        this.nota4 = n4;
        this.calcularPromedio();
    }

    private void calcularPromedio() {
        this.promedio = (nota1 + nota2 + nota3 + nota4) / 4.0;
        
        if (this.promedio >= 11.5) {
            this.estado = "APROBADO";
        } else {
            this.estado = "DESAPROBADO";
        }
    }

    public String getDni() {
        return dni;
    }
    
    public String getNombreCompleto() {
        return String.format("%s %s", nombres, apellidos);
    }
    
    public double getPromedio() {
        return promedio; 
    }
    
    public String generarLineaCSV() {
        StringBuilder sb = new StringBuilder();
        sb.append(dni).append(",");
        sb.append(nombres).append(",");
        sb.append(apellidos).append(",");
        sb.append(String.format("%.2f", promedio)).append(",");
        sb.append(estado);
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return String.format("DNI: %-10s | Alumno: %-40s | Promedio: %05.2f | Estado: %s", 
                             dni, getNombreCompleto(), promedio, estado);
    }
}"
}
