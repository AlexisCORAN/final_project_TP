/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

import java.util.Scanner;

/**
 *
 * @author tiago
 */
public class ProgramaEscuela {
     public static void main(String[] args) {
       SistemaNotas sistema = new SistemaNotas();
       Scanner tecla = new Scanner(System.in);
        
        
        String archivoEntrada = "base_de_datos2.csv";
        String archivoSalida = "Reporte_Final.csv";
        
        int opcion = 0;

        do {
            System.out.println("\n=== SISTEMA DE CONTROL DE NOTAS ===");
            System.out.println("1. Cargar Datos (Excel/CSV)");
            System.out.println("2. Mostrar Reporte en Pantalla");
            System.out.println("3. Exportar Resultados");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opcion: ");


            opcion = Integer.parseInt(tecla.nextLine());
            

            switch (opcion) {
                case 1:
                    if (sistema.cargarDatosDesdeArchivo(archivoEntrada)) {
                        System.out.println("Carga exitosa");
                    }
                    break;
                case 2:
                    sistema.calcularPromedio();
                    break;
                case 3:
                    sistema.exportarReporte(archivoSalida);
                    break;
                case 4:
                    System.out.println("Cerrando sistema");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 4);
    }
}

}
