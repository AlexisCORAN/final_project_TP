/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.programaescuela;
import java.io.*;
/**
 *
 * @author Alexis
 */
public class SistemaNotas {

    private Alumno[] listaEstudiantes;
    int index = 0;
    
    public SistemaNotas() {
        this.listaEstudiantes = new Alumno[35];
        this.index = 0;
    }
    
    public void agregaralumno(Alumno nuevo) {
        listaEstudiantes[index] = nuevo;
        index++;
    }
    
    public boolean cargaDatos(String ruta) throws FileNotFoundException, IOException {
        File archivo = new File(ruta);
        
        if (!archivo.exists()) {
            return false;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean header = true;
            
            while ((linea = br.readLine()) != null) {
                if (header) {
                    header = false;
                    continue;
                } 
                if (!linea.trim().isEmpty()) {
                    procesarDatos(linea);
                }
            }
        }
        return true;
        
    }
    
    public void procesarDatos(String linea) {
        String[] datos = linea.split(",");
        String dni = datos[0];
        String nombres  = datos[1];
        String apellidos = datos[2];
        double nota1 = Double.parseDouble(datos[17]);
        double nota2 = Double.parseDouble(datos[18]);
        double nota3 = Double.parseDouble(datos[19]);
        double nota4 = Double.parseDouble(datos[20]);
        
        Alumno nuevo = new Alumno(dni, nombres, apellidos, nota1, nota2, nota3, nota4);
        
        agregaralumno(nuevo);

    }
    
    public void listarPromedios() {
        for (int i = 0; i <= index; i++) {
               if (listaEstudiantes[i] != null) {
                System.out.println(listaEstudiantes[i].toString());
            }
        }
 

      
    }
}
