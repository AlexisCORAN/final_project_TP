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

        } while (opcion != 7&&opcion !=8);

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
        char genero='K';
        char genApo;
        String nomApo, apeApo, paren, telfApo,asistencia;
        double n1, n2, n3, n4, asist;
        try {
            System.out.println("\n--- REGISTRO DE NUEVO ALUMNO ---");

            System.out.print("DNI: ");
            do {                          
                        dni = scanner.nextLine();
                        if (!SistemaNotas.validarDNI(dni)) {
                            System.out.println("Error: Ingrese un número de DNI correcto");}
                        if (sistema.buscarAlumnoDni(dni) != null) {
                            System.out.println("Error: Ya existe un alumno con ese DNI.");}
                        
                        } while (!SistemaNotas.validarDNI(dni)||sistema.buscarAlumnoDni(dni) != null);
            
           System.out.print("Nombres: ");
           do {                          
                        nom = scanner.nextLine();
                        if (!SistemaNotas.validarNyA(nom)) {
                            System.out.println("Nombres incorrectos (Ejemplo correcto: Juan Gilbert)");}
                        if (sistema.buscarNyA(nom) != null) {
                            System.out.println("Error: Ya existe un alumno con ese nombre.");}
                        
                        } while (!SistemaNotas.validarNyA(nom)||sistema.buscarNyA(nom) != null);
           
           System.out.print("Apellidos: ");
           do {                          
                        ape = scanner.nextLine();
                        if (!SistemaNotas.validarNyA(ape)) {
                            System.out.println("Apellidos incorrectos (Ejemplo correcto: Alva León)");}
                        if (sistema.buscarApellido(ape) != null) {
                            System.out.println("Error: Ya existe un alumno con ese apellido.");}
                        
                        } while (!SistemaNotas.validarNyA(ape)||sistema.buscarApellido(ape) != null);
           
           //Genero
           System.out.print("Genero (M/F): "); 
            do {
            
            g = scanner.nextLine();
            switch(g) {
                case "M","F"-> {genero = g.isEmpty() ? ' ' : g.charAt(0);}          
                default-> { System.out.println("Error: Ingrese M o F");              
                           break;}
            }   
            } while (genero!='M'&&genero!='F');

           //Seccion
            System.out.print("Seccion: ");
            int a=1;
            do {
            
            seccion = scanner.nextLine();
            switch(seccion) {
                case "A","B","C"-> a=2;          
                default-> { System.out.println("Error: Ingrese A o B o C");              
                           break;}
            }   
            } while (a==1);
                
            //Nivel
            System.out.print("Nivel: ");
            int b=1;
            do {
            
            nivel = scanner.nextLine();
            switch(nivel) {
                case "Inicial","Primaria","Secundaria","4 años","3 años","5 años"-> b=2;          
                default-> { System.out.println("Error: Formato inválido");              
                           break;}
            }   
            } while (b==1);
            
            //Grado
            System.out.print("Grado: ");
            int c=1;
            do {
            
            grado = scanner.nextLine();
            switch(grado) {
                case "Primero","Segundo","Tecero","Cuarto","Quinto","Sexto"-> c=2;          
                default-> { System.out.println("Error: Ingrese del 1 al 6 en formato ordinal");              
                           break;}
            }   
            } while (c==1);
            
            //Teléfono
            System.out.print("Teléfono: ");
                        do{
                        telf = scanner.nextLine();
                        if (!SistemaNotas.validarTelefono(telf)) {
                            System.out.println("Teléfono inválido. Debe comenzar con 9 y tener 9 dígitos");}
                        if (sistema.buscarTelef(telf) != null) {
                            System.out.println("Error: Ya existe un alumno con ese teléfono");
                        }
                        } while (!SistemaNotas.validarTelefono(telf)||sistema.buscarTelef(telf) != null);
            
            //Correo
            System.out.print("Correo: ");
                        do{
                        correo = scanner.nextLine();
                        if (!SistemaNotas.validarCorreo(correo)) {
                            System.out.println("Correo inválido. Debe terminar en @gmail.com");}
                        if (sistema.buscarCorreo(correo) != null) {
                            System.out.println("Error: Ya existe un alumno con ese correo");
                        }
                        } while (!SistemaNotas.validarCorreo(correo)||sistema.buscarCorreo(correo) != null);
            
            System.out.print("Direccion: ");
                       do{
                        direccion = scanner.nextLine();
                        if (!SistemaNotas.validarDireccion(direccion)) {
                            System.out.println("Dirección inválida");}
                        } while (!SistemaNotas.validarDireccion(direccion));

            // Datos Apoderado
            System.out.print("Nombre Apoderado: ");
            do {                          
                        nomApo = scanner.nextLine();
                        if (!SistemaNotas.validarNyA(nomApo)) {
                            System.out.println("Nombres incorrectos (Ejemplo correcto: Juan Gilbert)");}
                        if (sistema.buscarNyA(nomApo) != null) {
                            System.out.println("Error: Ya existe un apoderado/alumno con ese nombre.");}
                        
                        } while (!SistemaNotas.validarNyA(nomApo)||sistema.buscarNyA(nomApo) != null);
            
            
            System.out.print("Apellido Apoderado: ");
            do {                          
                        apeApo = scanner.nextLine();
                        if (!SistemaNotas.validarNyA(apeApo)) {
                            System.out.println("Apellidos incorrectos (Ejemplo correcto: Alva León)");}
                        if (sistema.buscarApellido(apeApo) != null) {
                            System.out.println("Error: Ya existe un apoderado/alumno con ese apellido.");}
                        
                        } while (!SistemaNotas.validarNyA(apeApo)||sistema.buscarApellido(apeApo) != null);
            
            
            System.out.print("Genero (M/F): "); 
            String ga;
            genApo='M';
            do {
            
            ga = scanner.nextLine();
            switch(g) {
                case "M","F"-> {genApo = ga.isEmpty() ? ' ' : ga.charAt(0);}          
                default-> { System.out.println("Error: Ingrese M o F");              
                           break;}
            }   
            } while (genero!='M'&&genero!='F');
            

            
            System.out.print("Parentesco Apoderado: ");
            int d=1;
            do {
            
            paren = scanner.nextLine();
            switch(paren) {
                case "Padre","Madre","Tío","Tía","Abuelo","Abuela"-> d=2;          
                default-> { System.out.println("Error: Ingrese un parentesco real que inicie en mayúscula");              
                           break;}
            }   
            } while (d==1);
            
            
            System.out.print("Teléfono Apoderado: ");
                        do{
                        telfApo = scanner.nextLine();
                        if (!SistemaNotas.validarTelefono(telfApo)) {
                            System.out.println("Teléfono inválido. Debe comenzar con 9 y tener 9 dígitos");}
                        if (sistema.buscarTelefonoApoderado(telfApo) != null) {
                            System.out.println("Error: Ya existe un apoderado/alumno con ese teléfono");
                        }
                        } while (!SistemaNotas.validarTelefono(telfApo)||sistema.buscarTelefonoApoderado(telfApo) != null);
            

            // Notas y Asistencia
            System.out.println("Ingrese las notas:");
            System.out.print("Nota 1: ");
            String nota1,nota2,nota3,nota4;
            
            do {
            nota1=scanner.nextLine();
            if (!SistemaNotas.validarNota(nota1)) {
                            System.out.println("Nota inválida. Debe estar entre 0 a 20 y tener un sólo decimal (y que esté separado por un punto)");}
                        
                        } while (!SistemaNotas.validarNota(nota1));          
            n1 = Double.parseDouble(nota1);
            
            
            System.out.print("Nota 2: ");
            do {
            nota2=scanner.nextLine();
            if (!SistemaNotas.validarNota(nota2)) {
                            System.out.println("Nota inválida. Debe estar entre 0 a 20 y tener un sólo decimal (y que esté separado por un punto)");}
                        
                        } while (!SistemaNotas.validarNota(nota2));          
            n2 = Double.parseDouble(nota2);
            
            
            System.out.print("Nota 3: ");
            do {
            nota3=scanner.nextLine();
            if (!SistemaNotas.validarNota(nota3)) {
                            System.out.println("Nota inválida. Debe estar entre 0 a 20 y tener un sólo decimal (y que esté separado por un punto)");}
                        
                        } while (!SistemaNotas.validarNota(nota3));          
            n3 = Double.parseDouble(nota3);
            
            
            System.out.print("Nota 4: ");
            do {
            nota4=scanner.nextLine();
            if (!SistemaNotas.validarNota(nota4)) {
                            System.out.println("Nota inválida. Debe estar entre 0 a 20 y tener un sólo decimal (y que esté separado por un punto)");}
                        
                        } while (!SistemaNotas.validarNota(nota4));          
            n4 = Double.parseDouble(nota4);
            
            
            
            System.out.print("Porcentaje Asistencia: ");
            do {
            asistencia=scanner.nextLine();
            if (!SistemaNotas.validarAsistencia(asistencia)) {
                            System.out.println("Nota inválida. Debe estar entre 0 a 100 y tener un sólo decimal (y que esté separado por un punto)");}
                        
                        } while (!SistemaNotas.validarAsistencia(asistencia));          
            asist =  Double.parseDouble(asistencia);

         
            
            System.out.print("Comportamiento: ");
            int e=1;
            do {
            
            comp = scanner.nextLine();
            switch(comp) {
                case "Excelente","Regular","Bueno","Malo"-> e=2;          
                default-> { System.out.println("Error: Valor inválido");    
                            System.out.println("Valores: Bueno, Malo, Regular y Exceente");  
                           break;}
            }   
            } while (e==1);
            

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
