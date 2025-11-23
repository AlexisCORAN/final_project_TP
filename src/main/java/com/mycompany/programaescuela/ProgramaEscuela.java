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

        SistemaNotas sistema = new SistemaNotas();

        try {
            System.setOut(new java.io.PrintStream(
                    new java.io.FileOutputStream(java.io.FileDescriptor.out),
                    true,
                    java.nio.charset.StandardCharsets.UTF_8.name()
            ));

            System.setErr(new java.io.PrintStream(
                    new java.io.FileOutputStream(java.io.FileDescriptor.err),
                    true,
                    java.nio.charset.StandardCharsets.UTF_8.name()
            ));

        } catch (Exception e) {
            System.out.println("[ProgramaEscuela] No se pudo forzar UTF-8: " + e.getMessage());
        }

        Scanner tecla = new Scanner(System.in);

        String archivoEntrada = "base_de_datos2.csv";
        String archivoSalida = "Reporte_Actualizado.csv";
        String menu = """
               === SISTEMA DE CONTROL DE NOTAS ===
                   1. Cargar Datos
                   2. Mostrar Reporte de Promedios
                   3. Buscar Alumno por DNI
                   4. Registrar Nuevo Alumno
                   5. Retirar Alumno
                   6. Editar Datos de Alumno
                   7. Exportar Resultados
                   8. Salir
               """;

        int opcion = 0;

        do {
            System.out.printf("\n%s", menu);
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(tecla.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {

                case 1:
                    if (sistema.cargaDatosArchivo(archivoEntrada)) {
                        System.out.println("¡Datos cargados correctamente!");
                    } else {
                        System.out.println("No se pudo cargar el archivo.");
                    }
                    break;

                case 2:
                    System.out.println("\n--- TIPO DE REPORTE ---");
                    System.out.println("1. Ver TODOS");
                    System.out.println("2. Solo ACTIVOS");
                    System.out.println("3. Solo RETIRADOS");
                    System.out.print("Elija una opción: ");

                    try {
                        int filtro = Integer.parseInt(tecla.nextLine());
                        sistema.listarPromediosFiltro(filtro);
                    } catch (Exception e) {
                        System.out.println("Entrada inválida → Mostrando todos.");
                        sistema.listarPromediosFiltro(1);
                    }
                    break;

                case 3:
                    System.out.println("\n--- BUSCAR ALUMNO POR DNI ---");
                    System.out.print("Ingrese DNI: ");
                    String dniBusqueda = tecla.nextLine();

                    Alumno encontrado = sistema.buscarAlumnoDni(dniBusqueda);

                    if (encontrado != null) {
                        System.out.println("\n>>> ALUMNO ENCONTRADO <<<");
                        System.out.println(encontrado);
                        System.out.println("Correo: " + encontrado.getCorreo());
                        System.out.println("Teléfono: " + encontrado.getTelefono());
                    } else {
                        System.out.println("\nNo se encontró alumno con DNI: " + dniBusqueda);
                    }

                    System.out.println("\n1. Volver");
                    System.out.println("2. Salir");
                    System.out.print("Elija: ");

                    try {
                        int decision = Integer.parseInt(tecla.nextLine());
                        if (decision == 2) {
                            opcion = 7;
                            System.out.println("Cerrando programa...");
                        }
                    } catch (Exception e) {
                        System.out.println("Entrada inválida → Regresando al menú.");
                    }
                    break;

                case 4:
                    try {
                        System.out.println("\n--- REGISTRO DE NUEVO ALUMNO ---");

                        System.out.print("DNI: ");
                        String dni = tecla.nextLine();

                        if (sistema.buscarAlumnoDni(dni) != null) {
                            System.out.println("Error: Ya existe un alumno con ese DNI.");
                            break;
                        }

                        System.out.print("Nombres: ");
                        String nom = tecla.nextLine();

                        System.out.print("Apellidos: ");
                        String ape = tecla.nextLine();

                        System.out.print("Correo: ");
                        String correo = tecla.nextLine();

                        System.out.print("Teléfono: ");
                        String telf = tecla.nextLine();

                        System.out.println("Ingrese las notas:");
                        System.out.print("Nota 1: ");
                        double n1 = Double.parseDouble(tecla.nextLine());
                        System.out.print("Nota 2: ");
                        double n2 = Double.parseDouble(tecla.nextLine());
                        System.out.print("Nota 3: ");
                        double n3 = Double.parseDouble(tecla.nextLine());
                        System.out.print("Nota 4: ");
                        double n4 = Double.parseDouble(tecla.nextLine());

                        Alumno nuevo = new Alumno(dni, nom, ape, correo, telf, n1, n2, n3, n4, null);
                        sistema.registrarAlumno(nuevo);

                        System.out.println("¡Alumno registrado correctamente!");

                    } catch (NumberFormatException e) {
                        System.out.println("Error: Debe ingresar valores numéricos en las notas.");
                    }
                    break;

                case 5:
                    System.out.println("\n--- RETIRAR ALUMNO ---");
                    System.out.print("Ingrese DNI del alumno a retirar: ");
                    String dniRet = tecla.nextLine();
                    if (sistema.retirarAlumno(dniRet)) {
                        System.out.println("Alumno con DNI " + dniRet + " marcado como RETIRADO.");
                    } else {
                        System.out.println("No se pudo retirar alumno. Verifique el DNI o si ya está retirado.");
                    }
                    break;

                case 6:
                    sistema.editarDatosAlumno(tecla);
                    break;

                case 7:
                    sistema.exportarCSV(archivoSalida);
                    break;

                case 8:
                    System.out.println("Cerrando sistema...");
                    break;

                default:
                    System.out.println("Opción inválida, intente nuevamente.");
            }

        } while (opcion != 7);

        tecla.close();
    }
}
