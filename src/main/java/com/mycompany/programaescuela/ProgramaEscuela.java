/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.programaescuela;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.FileDescriptor;
/**
 *
 * @author Alexis
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

public class ProgramaEscuela {

    private static Scanner scanner = new Scanner(System.in);
    private static SistemaNotas sistema = new SistemaNotas();

    public static void main(String[] args) {
        configurarConsolaUTF8();

        String archivoEntrada = "base_de_datos2.csv";
        String archivoSalida = "Reporte_Actualizado.csv";
        int opcion = 0;

        do {
            mostrarMenuPrincipal();
            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1 -> cargarDatos(archivoEntrada);
                case 2 -> menuReportes();
                case 3 -> buscarAlumnoUI();
                case 4 -> registrarAlumnoUI();
                case 5 -> sistema.editarDatosAlumno(scanner);
                case 6 -> sistema.exportarCSV(archivoSalida);
                case 7 -> System.out.println("Cerrando sistema...");
                default -> System.out.println("Opción inválida, intente nuevamente.");
            }

        } while (opcion != 7);

        scanner.close();
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("""
            \n=== SISTEMA DE CONTROL DE NOTAS ===
            1. Cargar Datos
            2. Mostrar Reporte de Promedios
            3. Buscar Alumno por DNI
            4. Registrar Nuevo Alumno
            5. Editar Datos de Alumno
            6. Exportar Resultados
            7. Salir
            """);
    }

    private static void cargarDatos(String archivo) {
        if (sistema.cargaDatosArchivo(archivo)) {
            System.out.println("¡Datos cargados correctamente!");
        } else {
            System.out.println("No se pudo cargar el archivo.");
        }
    }

    private static void menuReportes() {
        int filtro;
        System.out.println("\n--- TIPO DE REPORTE ---");
        System.out.println("1. Ver TODOS | 2. Solo ACTIVOS | 3. Solo RETIRADOS");
        System.out.print("Seleccione una opción: ");
        filtro = Integer.parseInt(scanner.nextLine());

        if (filtro < 1 || filtro > 3) filtro = 1; 
        sistema.listarPromediosFiltro(filtro);
    }

    private static void buscarAlumnoUI() {
        String dni;
        Alumno encontrado;

        System.out.print("\nIngrese DNI a buscar: ");
        dni = scanner.nextLine();
        encontrado = sistema.buscarAlumnoDni(dni);

        if (encontrado != null) {
            System.out.println("\n>>> ALUMNO ENCONTRADO <<<");
            System.out.println(encontrado);
        } else {
            System.out.println("\nNo se encontró alumno con DNI: " + dni);
        }
    }

    
    private static void registrarAlumnoUI() {

        String dni, nom, ape, g, seccion, nivel, comp, grado, telf, correo, direccion;
        char genero, genApo;
        String nomApo, apeApo, paren, telfApo;
        double n1, n2, n3, n4, asist;
        try {
            System.out.println("\n--- REGISTRO DE NUEVO ALUMNO ---");

            System.out.print("DNI: ");
            dni = scanner.nextLine().trim();
            if (sistema.buscarAlumnoDni(dni) != null) {
                System.out.println("Error: Ya existe un alumno con ese DNI.");
                return;
            }

            System.out.print("Nombres: ");
            nom = scanner.nextLine();
            System.out.print("Apellidos: ");
            ape = scanner.nextLine();

            System.out.print("Genero (M/F): "); 
            g = scanner.nextLine(); 
            genero = g.isEmpty() ? ' ' : g.charAt(0);

            System.out.print("Seccion: ");
            seccion = scanner.nextLine();
            System.out.print("Nivel: ");
            nivel = scanner.nextLine();
            System.out.print("Grado: ");
            grado = scanner.nextLine();
            System.out.print("Teléfono: ");
            telf = scanner.nextLine();
            System.out.print("Correo: ");
            correo = scanner.nextLine();
            System.out.print("Direccion: ");
            direccion = scanner.nextLine();

            // Datos Apoderado
            System.out.print("Nombre Apoderado: ");
            nomApo = scanner.nextLine();
            System.out.print("Apellido Apoderado: ");
            apeApo = scanner.nextLine();
            
            System.out.print("Genero Apoderado (M/F): ");
            String ga = scanner.nextLine();
            genApo = ga.isEmpty() ? ' ' : ga.charAt(0);

            System.out.print("Parentesco Apoderado: ");
            paren = scanner.nextLine();
            System.out.print("Teléfono Apoderado: ");
            telfApo = scanner.nextLine();

            // Notas y Asistencia
            System.out.println("Ingrese las notas:");
            System.out.print("Nota 1: ");
            n1 = Double.parseDouble(scanner.nextLine());
            System.out.print("Nota 2: ");
            n2 = Double.parseDouble(scanner.nextLine());
            System.out.print("Nota 3: ");
            n3 = Double.parseDouble(scanner.nextLine());
            System.out.print("Nota 4: ");
            n4 = Double.parseDouble(scanner.nextLine());
            System.out.print("Porcentaje Asistencia: ");
            asist =  Double.parseDouble(scanner.nextLine());

            System.out.print("Comportamiento: ");
            comp = scanner.nextLine();

            Alumno nuevo = new Alumno(dni, nom, ape, genero, seccion, nivel, grado, telf, correo, direccion,
                    nomApo, apeApo, genApo, paren, telfApo,
                    n1, n2, n3, n4, null, asist, comp);

            sistema.registrarAlumno(nuevo);
            System.out.println("¡Alumno registrado correctamente!");

        } catch (Exception e) {
            System.out.println("Ocurrió un error en el registro: " + e.getMessage());
        }
    }

    private static void configurarConsolaUTF8() {
        try {
            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8.name()));
            System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.err), true, StandardCharsets.UTF_8.name()));
        } catch (Exception e) {
            System.out.println("Advertencia: No se pudo configurar UTF-8.");
        }
    }
}