/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.programaescuela;
import java.util.Scanner;
/**
 *
 * @author Alexis
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */


public class ProgramaEscuela {

    public static void main(String[] args) {
        

        // Usar la salida por defecto del sistema para que NetBeans muestre correctamente la consola
        
        SistemaNotas sistema = new SistemaNotas();
        System.out.println("[ProgramaEscuela] Charset por defecto de la JVM: " + java.nio.charset.Charset.defaultCharset().name());
        try {
            // Forzar la codificación de salida a UTF-8 para alinear con la detección/guardado en UTF-8
            System.setOut(new java.io.PrintStream(new java.io.FileOutputStream(java.io.FileDescriptor.out), true, java.nio.charset.StandardCharsets.UTF_8.name()));
            System.setErr(new java.io.PrintStream(new java.io.FileOutputStream(java.io.FileDescriptor.err), true, java.nio.charset.StandardCharsets.UTF_8.name()));
            System.out.println("[ProgramaEscuela] System.out forzado a UTF-8 para compatibilidad con la consola.");
        } catch (Exception e) {
            System.out.println("[ProgramaEscuela] No se pudo forzar System.out: " + e.getMessage());
        }
        Scanner tecla = new Scanner(System.in);
        String archivoEntrada = "base_de_datos2.csv";
        String archivoSalida = "Reporte_Actualizado.csv";
        String menu = """
               === SISTEMA DE CONTROL DE NOTAS ===
                   1. Cargar Datos
                   2. Mostrar Reporte de promedios
                   3. Buscar Alumno por DNI
                   4. Agregar Alumno Nuevo
                   5. Editar Datos de Alumno
                   6. Exportar Resultados
                   7. Salir
               """;
        
        int opcion = 0;

        do {
            System.out.printf("\n %s", menu);
            System.out.print("Seleccione una opcion: ");

            try {
               
                String entrada = tecla.nextLine();
                opcion = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    if (sistema.cargaDatos(archivoEntrada)) {
                        System.out.println("¡Carga exitosa del archivo!");
                    } else {
                        System.out.println("No se pudo cargar el archivo. Verifique que exista.");
                    }
                    break;

                case 2:
                    sistema.listarPromedios();
                    break;
                case 3:
                
                    System.out.println("\n--- BÚSQUEDA DE ALUMNO ---");
                    System.out.print("Ingrese el DNI a buscar: ");
                    String dniBusqueda = tecla.nextLine();

                    Alumno encontrado = sistema.buscarPorDni(dniBusqueda);
                    
                    if (encontrado != null) {
                        System.out.println("\n>>> ALUMNO ENCONTRADO <<<");
                        System.out.println(encontrado.toString());
                        System.out.println("Correo: " + encontrado.getCorreo());
                        System.out.println("Teléfono: " + encontrado.getTelefono());
                    } else {
                        System.out.println("\n No se encontró ningún alumno con el DNI: " + dniBusqueda);
                    }
                    
                    System.out.println("\n ¿Qué desea hacer ahora?");
                    System.out.println("1. Volver al Menú Principal");
                    System.out.println("2. Terminar Programa");
                    System.out.print("Elija: ");
                    
                    try {
                        int decision = Integer.parseInt(tecla.nextLine());
                        if (decision == 2) {
                            opcion = 7;
                            System.out.println("Terminando programa...");
                        }
                    } catch (Exception e) {
                        System.out.println("Opción no válida, volviendo al menú por defecto.");
                    }
                    break;

                case 4:
                    try {
                        System.out.println("\n--- REGISTRO DE NUEVO ALUMNO ---");
                        System.out.print("Ingrese DNI: ");
                        String dni = tecla.nextLine();
                        
                        
                        if (sistema.buscarPorDni(dni) != null) {
                            System.out.println("Error: Ya existe un alumno con ese DNI.");
                        } else {
                            System.out.print("Nombres: ");
                            String nom = tecla.nextLine();
                            System.out.print("Apellidos: ");
                            String ape = tecla.nextLine();
                            System.out.print("Correo: ");
                            String correo = tecla.nextLine();
                            System.out.print("Teléfono: ");
                            String telf = tecla.nextLine();
                            
                            System.out.println("Ingrese las notas (use punto para decimales, ej: 15.5):");
                            System.out.print("Nota 1: ");
                            double n1 = Double.parseDouble(tecla.nextLine());
                            System.out.print("Nota 2: ");
                            double n2 = Double.parseDouble(tecla.nextLine());
                            System.out.print("Nota 3: ");
                            double n3 = Double.parseDouble(tecla.nextLine());
                            System.out.print("Nota 4: ");
                            double n4 = Double.parseDouble(tecla.nextLine());

                            Alumno nuevo = new Alumno(dni, nom, ape, correo, telf, n1, n2, n3, n4);
                            
                            sistema.agregarAlumno(nuevo);
                            System.out.println("¡Alumno registrado correctamente!");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Ingresaste un texto en lugar de un número válido para la nota.");
                    }
                    break;

                case 5:
                    sistema.actualizarDatosAlumno(tecla);
                    break;

                case 6:
                    sistema.guardarArchivo(archivoSalida);
                    break;
                case 7:
                    System.out.println("Cerrando sistema.");
                    break;

                default:
                    System.out.println("Opción no válida, intente de nuevo.");
            }
        } while (opcion != 7);
        
        tecla.close();
    }
}