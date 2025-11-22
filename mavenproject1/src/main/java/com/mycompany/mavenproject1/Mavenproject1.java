/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mavenproject1;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

 
 
public class Mavenproject1 {

    public static void main(String[] args) {
        
        Scanner lector=new Scanner(System.in);
        String archivo = "C:\\Users\\tiago\\Documents\\NetBeansProjects\\final_project_TPP\\mavenproject1\\src\\main\\java\\resources\\basededatos.csv";
        String linea;
        String[] columnas=null;
        String ingresaralumno;
        int comprobador=0;
        double nota1=0;
        double nota2=0;
        double nota3=0;
        double nota4=0;
        double promedio=0;
        System.out.println("Ingrese el nombre del alumno");
        ingresaralumno=lector.nextLine();
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            
            while ((linea = br.readLine()) != null) {
               
                columnas = linea.split(";");
                
                
                
                
                
                if (columnas.length > 1 && columnas[1].equals(ingresaralumno)) {
                    System.out.println("Nombre encontrado: " + columnas[1]);
                    comprobador=1;
                    System.out.println("Apellido:"+columnas[2]);
                    
                    nota1=Double.parseDouble(columnas[17]);
                    System.out.println("Primera nota"+nota1);
                    
                    nota2=Double.parseDouble(columnas[18]);
                    System.out.println("Segunda nota"+nota2);
                    
                    nota3=Double.parseDouble(columnas[19]);
                    System.out.println("Tercera nota"+nota3);
                    
                    nota4=Double.parseDouble(columnas[20]);
                    System.out.println("Cuarta nota"+nota4);
                    promedio=(nota1+nota2+nota3+nota4)/4;
                    System.out.println("El promedio es: "+promedio);
                    
                    
                } 


            }
            
            if (comprobador!=1){
            System.out.println("Nombre no encontrado");
            }
            
            if (promedio>=12 && comprobador!=1) {
            System.out.println("Estado:aprobado");
            } else {
            System.out.println("Estado: Desaprobado");
            }
            
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        

    


        
    }
}

        
        
        
    

