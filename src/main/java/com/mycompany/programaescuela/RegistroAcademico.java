/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.programaescuela;

/**
 *
 * @author Alexis
 */
public class RegistroAcademico {
    private double nota1;
    private double nota2;
    private double nota3;
    private double nota4;

    private double asistencia;   // porcentaje 0-100
    private String comportamiento;

    public RegistroAcademico() {}

    public RegistroAcademico(double nota1, double nota2, double nota3, double nota4,
                             double asistencia, String comportamiento) {
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.nota3 = nota3;
        this.nota4 = nota4;
        this.asistencia = asistencia;
        this.comportamiento = comportamiento;
    }

    public double getPromedio() {
        return (nota1 + nota2 + nota3 + nota4) / 4;
    }
    
    public double getNota1() { return nota1; }
    public void setNota1(double nota1) { this.nota1 = nota1; }

    public double getNota2() { return nota2; }
    public void setNota2(double nota2) { this.nota2 = nota2; }

    public double getNota3() { return nota3; }
    public void setNota3(double nota3) { this.nota3 = nota3; }

    public double getNota4() { return nota4; }
    public void setNota4(double nota4) { this.nota4 = nota4; }

    public double getAsistencia() { return asistencia; }
    public void setAsistencia(double asistencia) { this.asistencia = asistencia; }

    public String getComportamiento() { return comportamiento; }
    public void setComportamiento(String comportamiento) { this.comportamiento = comportamiento; }

}
