/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.programaescuela.model;

/**
 *
 * @author Alexis
 */
public class Grado {
    private String nivel;     
    private String gradoText;       
    private String seccion;


    public Grado(String nivel, String gradoText, String seccion) {
        this.nivel = nivel;
        this.gradoText = gradoText;
        this.seccion = seccion;
       
    }

    // Getters y Setters
    public String getNivel() {
        return nivel;
    }

    public String getGradoText() {
        return gradoText;
    }
    
    public String getSeccion() {
        return seccion;
    }
    
    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
    
     public void setGradoText(String gradoText) {
        this.gradoText =gradoText;
    }
    
    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }
    
   

    @Override
    public String toString() {
        return nivel + " " + gradoText + " - " + seccion;
    }

}
