/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.programaescuela;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.FileDescriptor;
import java.util.List;
/**
 *
 * @author Alexis
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

public class ProgramaEscuela {

    private static final Scanner scanner = new Scanner(System.in);
    private static final SistemaController controller = new SistemaController();

    public static void main(String[] args) {
        configurarConsolaUTF8();

        String archivoEntrada = "base_de_datos2.csv";
        String archivoSalida = "Reporte_Actualizado.csv";
        int opcion;

        do {
            mostrarMenuPrincipal();
            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1 -> cargarDatos(archivoEntrada);
                case 2 -> menuReportes();
                case 3 -> buscarAlumnoUI();
                case 4 -> registrarAlumnoUI();
                case 5 -> editarAlumnoUI();
                case 6 -> exportarResultados(archivoSalida);
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
        if (controller.cargarDatos(archivo)) {
            System.out.println("¡Datos cargados correctamente!");
        } else {
            System.out.println("No se pudo cargar el archivo.");
        }
    }

    private static void menuReportes() {
        System.out.println("\n--- TIPO DE REPORTE ---");
        System.out.println("1. Ver TODOS | 2. Solo ACTIVOS | 3. Solo RETIRADOS");
        System.out.print("Seleccione una opción: ");
        int filtro = Integer.parseInt(scanner.nextLine());
        List<Alumno> alumnos = controller.obtenerTodosAlumnos();

        for (Alumno a : alumnos) {
            if (filtro == 2 && a.isRetirado()) continue;
            if (filtro == 3 && !a.isRetirado()) continue;
            System.out.println(a);
        }
    }

    private static void buscarAlumnoUI() {
        System.out.print("\nIngrese DNI a buscar: ");
        String dni = scanner.nextLine();
        Alumno a = controller.buscarAlumnoPorDni(dni);
        if (a != null) {
            System.out.println("\n>>> ALUMNO ENCONTRADO <<<");
            System.out.println(a);
        } else {
            System.out.println("No se encontró alumno con DNI: " + dni);
        }
    }

    private static void registrarAlumnoUI() {
        try {
            System.out.println("\n--- REGISTRO DE NUEVO ALUMNO ---");

            String dni = leerDatoValidado("DNI: ", controller::validarDNI);
            String nombres = leerDatoValidado("Nombres: ", controller::validarNyA);
            String apellidos = leerDatoValidado("Apellidos: ", controller::validarNyA);
            char genero = leerGenero("Género (M/F): ");

            String seccion = leerOpcion("Sección (A/B/C): ", List.of("A", "B", "C"));
            String nivel = leerOpcion("Nivel (Inicial/Primaria/Secundaria): ", List.of("Inicial", "Primaria", "Secundaria"));
            int gradoNum = controller.convertirGrado(leerOpcion("Grado (Primero/Segundo/Tercero/Cuarto/Quinto/Sexto): ",
                    List.of("Primero","Segundo","Tercero","Cuarto","Quinto","Sexto")));

            String telefono = leerDatoValidado("Teléfono: ", controller::validarTelefono);
            String correo = leerDatoValidado("Correo: ", controller::validarCorreo);
            String direccion = leerDatoValidado("Dirección: ", controller::validarDireccion);

            System.out.println("\n--- DATOS DEL APODERADO ---");
            String nomApo = leerDatoValidado("Nombre Apoderado: ", controller::validarNyA);
            String apeApo = leerDatoValidado("Apellido Apoderado: ", controller::validarNyA);
            char genApo = leerGenero("Género Apoderado (M/F): ");
            String parentezco = leerOpcion("Parentesco (Padre/Madre/Tío/Tía/Abuelo/Abuela): ",
                    List.of("Padre","Madre","Tío","Tía","Abuelo","Abuela"));
            String telfApo = leerDatoValidado("Teléfono Apoderado: ", controller::validarTelefono);

            Apoderado apoderado = new Apoderado(parentezco, nomApo, apeApo, genApo, telfApo);
            Grado grado = new Grado(nivel, gradoNum, seccion);

            System.out.println("\n--- NOTAS Y ASISTENCIA ---");
            double n1 = Double.parseDouble(leerDatoValidado("Nota 1: ", controller::validarNota));
            double n2 = Double.parseDouble(leerDatoValidado("Nota 2: ", controller::validarNota));
            double n3 = Double.parseDouble(leerDatoValidado("Nota 3: ", controller::validarNota));
            double n4 = Double.parseDouble(leerDatoValidado("Nota 4: ", controller::validarNota));
            double asistencia = Double.parseDouble(leerDatoValidado("Asistencia (%): ", controller::validarAsistencia));

            String comportamiento = leerOpcion("Comportamiento (Excelente/Bueno/Regular/Malo): ",
                    List.of("Excelente","Bueno","Regular","Malo"));

            RegistroAcademico registro = new RegistroAcademico(n1, n2, n3, n4, asistencia, comportamiento);
            Alumno nuevo = new Alumno(dni, nombres, apellidos, genero,
                    telefono, correo, direccion, apoderado, grado, registro);

            controller.registrarAlumno(nuevo);
            System.out.println("¡Alumno registrado correctamente!");
        } catch (Exception e) {
            System.out.println("Error en el registro: " + e.getMessage());
        }
    }

    private static void editarAlumnoUI() {
        System.out.print("\nIngrese DNI del alumno a editar: ");
        String dni = scanner.nextLine();
        Alumno a = controller.buscarAlumnoPorDni(dni);

        if (a == null) {
            System.out.println("No se encontró alumno con DNI: " + dni);
            return;
        }

        System.out.println("Alumno encontrado:\n" + a);
        System.out.println("\n--- CAMPOS EDITABLES ---");
        System.out.println("1. Teléfono");
        System.out.println("2. Correo");
        System.out.println("3. Dirección");
        System.out.println("4. Retirado (Sí/No)");
        System.out.print("Seleccione campo a editar: ");
        int opcion = Integer.parseInt(scanner.nextLine());

        switch (opcion) {
            case 1 -> a.setTelefono(leerDatoValidado("Nuevo Teléfono: ", controller::validarTelefono));
            case 2 -> a.setCorreo(leerDatoValidado("Nuevo Correo: ", controller::validarCorreo));
            case 3 -> a.setDireccion(leerDatoValidado("Nueva Dirección: ", controller::validarDireccion));
            case 4 -> {
                System.out.print("Está retirado? (Sí/No): ");
                String r = scanner.nextLine();
                a.setRetirado(r.equalsIgnoreCase("Sí"));
            }
            default -> System.out.println("Opción inválida.");
        }

        System.out.println("Alumno actualizado:\n" + a);
    }

    private static void exportarResultados(String archivo) {
        if (controller.guardarDatos(archivo)) {
            System.out.println("Archivo exportado correctamente: " + archivo);
        } else {
            System.out.println("Error al exportar el archivo.");
        }
    }

    // -------------------- MÉTODOS AUXILIARES --------------------
    private static String leerDatoValidado(String prompt, java.util.function.Predicate<String> validador) {
        String dato;
        do {
            System.out.print(prompt);
            dato = scanner.nextLine();
            if (!validador.test(dato)) System.out.println("Dato inválido, intente nuevamente.");
        } while (!validador.test(dato));
        return dato;
    }

    private static char leerGenero(String prompt) {
        char g;
        do {
            System.out.print(prompt);
            String linea = scanner.nextLine().toUpperCase();
            g = linea.isEmpty() ? ' ' : linea.charAt(0);
        } while (g != 'M' && g != 'F');
        return g;
    }

    private static String leerOpcion(String prompt, List<String> opcionesValidas) {
        String dato;
        do {
            System.out.print(prompt);
            dato = scanner.nextLine();
            if (!opcionesValidas.contains(dato)) {
                System.out.println("Opción inválida, intente nuevamente.");
            }
        } while (!opcionesValidas.contains(dato));
        return dato;
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
