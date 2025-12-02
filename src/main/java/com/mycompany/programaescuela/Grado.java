/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.programaescuela;

/**
 *
 * @author Alexis
 */
public class Grado {
    private String nivel;     // Primaria, Secundaria
    private int grado;        // 1, 2, 3...
    private String seccion;   // A, B, C...

    public Grado(String nivel, int grado, String seccion) {
        this.nivel = nivel;
        this.grado = grado;
        this.seccion = seccion;
    }

    // Getters y Setters
    public String getNivel() {
        return nivel;
    }
    public int getGrado() {
        return grado;
    }
    public String getSeccion() {
        return seccion;
    }
    
    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
    
    public void setGrado(int grado) {
        this.grado = grado;
    }
    
    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }


    @Override
    public String toString() {
        return nivel + " " + grado + "°" + " - " + seccion;
    }

}
