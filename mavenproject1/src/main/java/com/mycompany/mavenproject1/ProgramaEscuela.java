/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.programaescuela;
import java.io.IOException;
import java.util.Scanner;
/**
 *
 * @author Alexis
 */
public class ProgramaEscuela {

    public static void main(String[] args) {
       SistemaNotas sistema = new SistemaNotas();
       Scanner tecla = new Scanner(System.in);
        
        
        String archivoEntrada = "base_de_datos2.csv";
        
        int opcion = 0;

        do {
            System.out.println("\n=== SISTEMA DE CONTROL DE NOTAS ===");
            System.out.println("1. Cargar Datos (Excel/CSV)");
            System.out.println("2. Mostrar Reporte de promedios");
            System.out.println("3. Exportar Resultados");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opcion: ");


            opcion = Integer.parseInt(tecla.nextLine());
            

            switch (opcion) {
                case 1:
                {
                    try {
                        if (sistema.cargaDatos(archivoEntrada)) {
                            System.out.println("Carga exitosa");
                        }
                    } catch (IOException ex) {
                        System.getLogger(ProgramaEscuela.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }
                            
                }
                    break;

                case 2:
                    sistema.listarPromedios();
                    break;
                case 3:
                    break;
                case 4:
                    System.out.println("Cerrando sistema");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 4);
    }
}
