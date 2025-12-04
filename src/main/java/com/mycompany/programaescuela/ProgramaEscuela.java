/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.programaescuela;
import com.mycompany.programaescuela.controller.SistemaController;
import com.mycompany.programaescuela.model.RegistroAcademico;
import com.mycompany.programaescuela.model.Apoderado;
import com.mycompany.programaescuela.model.Grado;
import com.mycompany.programaescuela.model.Alumno;
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

            String dni = leerDatoValidado("DNI: ", validadorAlumno::validarDNI);
            String nombres = leerDatoValidado("Nombres: ", validadorAlumno::validarNyA);
            String apellidos = leerDatoValidado("Apellidos: ", validadorAlumno::validarNyA);
            char genero = leerGenero("Género (M/F): ");

            String seccion = leerOpcion("Sección (A/B/C): ", List.of("A", "B", "C"));
            String nivel = leerOpcion("Nivel (Inicial/Primaria/Secundaria): ", List.of("Inicial", "Primaria", "Secundaria"));
            String gradoText = leerOpcion("Grado (ej: 3 años, Primero, Segundo…): ",
                    List.of("3 años","4 años","5 años","Primero","Segundo","Tercero","Cuarto","Quinto","Sexto"));

            String telefono = leerDatoValidado("Teléfono: ", validadorAlumno::validarTelefono);
            String correo = leerDatoValidado("Correo: ", validadorAlumno::validarCorreo);
            String direccion = leerDatoValidado("Dirección: ", validadorAlumno::validarDireccion);

            System.out.println("\n--- DATOS DEL APODERADO ---");
            String nomApo = leerDatoValidado("Nombre Apoderado: ", validadorAlumno::validarNyA);
            String apeApo = leerDatoValidado("Apellido Apoderado: ", validadorAlumno::validarNyA);
            char genApo = leerGenero("Género Apoderado (M/F): ");
            String telfApo = leerDatoValidado("Teléfono Apoderado: ", validadorAlumno::validarTelefono);

            Apoderado apoderado = new Apoderado(nomApo, apeApo, genApo, telfApo);
            Grado grado = new Grado(nivel, gradoText, seccion);

            System.out.println("\n--- NOTAS Y ASISTENCIA ---");
            double n1 = Double.parseDouble(leerDatoValidado("Nota 1: ", validadorAlumno::validarNota));
            double n2 = Double.parseDouble(leerDatoValidado("Nota 2: ", validadorAlumno::validarNota));
            double n3 = Double.parseDouble(leerDatoValidado("Nota 3: ", validadorAlumno::validarNota));
            double n4 = Double.parseDouble(leerDatoValidado("Nota 4: ", validadorAlumno::validarNota));
            double asistencia = Double.parseDouble(leerDatoValidado("Asistencia (%): ", validadorAlumno::validarAsistencia));

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

        System.out.println("\n=== ALUMNO ENCONTRADO ===");
        System.out.println(a);

        int opcion;
        do {
            System.out.println("""
                \n--- CAMPOS EDITABLES ---
                1. Nombres
                2. Apellidos
                3. Género
                4. Teléfono
                5. Correo
                6. Dirección
                7. Grado / Nivel / Sección
                8. Apoderado
                9. Notas
                10. Asistencia
                11. Comportamiento
                12. Estado (Activo / Retirado)
                13. Salir
                """);

            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1 -> a.setNombres(leerDatoValidado("Nuevo nombre: ", validadorAlumno::validarNyA));
                case 2 -> a.setApellidos(leerDatoValidado("Nuevos apellidos: ", validadorAlumno::validarNyA));
                case 3 -> a.setGenero(leerGenero("Nuevo género (M/F): "));
                case 4 -> a.setTelefono(leerDatoValidado("Nuevo teléfono: ", validadorAlumno::validarTelefono));
                case 5 -> a.setCorreo(leerDatoValidado("Nuevo correo: ", validadorAlumno::validarCorreo));
                case 6 -> a.setDireccion(leerDatoValidado("Nueva dirección: ", validadorAlumno::validarDireccion));

                case 7 -> editarGrado(a);
                case 8 -> editarApoderado(a);
                case 9 -> editarNotas(a);
                case 10 -> a.getRegistroAcademico().setAsistencia(
                            Double.parseDouble(leerDatoValidado("Nueva asistencia (%): ", validadorAlumno::validarAsistencia))
                        );
                case 11 -> a.getRegistroAcademico().setComportamiento(
                            leerOpcion("Nuevo comportamiento (Excelente/Bueno/Regular/Malo): ",
                            List.of("Excelente","Bueno","Regular","Malo"))
                        );
                case 12 -> {
                    System.out.print("¿Está retirado? (Sí/No): ");
                    a.setRetirado(scanner.nextLine().equalsIgnoreCase("Sí"));
                }

                case 13 -> System.out.println("Saliendo del editor…");
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 13);

        System.out.println("\nAlumno actualizado correctamente:");
        System.out.println(a);
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
    
    private static void editarGrado(Alumno a) {
        System.out.println("\n--- EDITAR GRADO ---");

        String nivel = leerOpcion("Nuevo nivel (Inicial/Primaria/Secundaria): ",
                List.of("Inicial","Primaria","Secundaria"));

        String gradoTexto = leerOpcion("Nuevo grado (ej: 3 años, Primero, Segundo…): ",
                List.of("3 años","4 años","5 años","Primero","Segundo","Tercero","Cuarto","Quinto","Sexto"));


        String seccion = leerOpcion("Nueva sección (A/B/C): ", List.of("A","B","C"));

        Grado g = a.getGrado();
        g.setNivel(nivel);
        g.setGradoText(gradoTexto);
        g.setSeccion(seccion);

        System.out.println("Grado actualizado: " + g.getNivel() + " " + g.getGradoText() + " Sección " + g.getSeccion());
    }

    
    private static void editarNotas(Alumno a) {
        System.out.println("\n--- EDITAR NOTAS ---");
        RegistroAcademico reg = a.getRegistroAcademico();

        reg.setNota1(Double.parseDouble(leerDatoValidado("Nueva nota 1: ", validadorAlumno::validarNota)));
        reg.setNota2(Double.parseDouble(leerDatoValidado("Nueva nota 2: ", validadorAlumno::validarNota)));
        reg.setNota3(Double.parseDouble(leerDatoValidado("Nueva nota 3: ", validadorAlumno::validarNota)));
        reg.setNota4(Double.parseDouble(leerDatoValidado("Nueva nota 4: ", validadorAlumno::validarNota)));
    }
    
    private static void editarApoderado(Alumno a) {
        System.out.println("\n--- EDITAR APODERADO ---");

        a.getApoderado().setNombre(leerDatoValidado("Nombre apoderado: ", validadorAlumno::validarNyA));
        a.getApoderado().setApellido(leerDatoValidado("Apellido apoderado: ", validadorAlumno::validarNyA));
        a.getApoderado().setGenero(leerGenero("Género apoderado (M/F): "));
        a.getApoderado().setTelefono(leerDatoValidado("Teléfono apoderado: ", validadorAlumno::validarTelefono));
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
