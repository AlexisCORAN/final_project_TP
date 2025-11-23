/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.programaescuela;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author Alexis
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

public class SistemaNotas {

    private ArrayList<Alumno> listaEstudiantes;

    public SistemaNotas() {
        this.listaEstudiantes = new ArrayList<>();
    }

    public void registrarAlumno(Alumno nuevo) {
        listaEstudiantes.add(nuevo);
    }

    private Alumno getAlumnoIndice(int index) {
        if (index < 0 || index >= listaEstudiantes.size()) return null;
        return listaEstudiantes.get(index);
    }

    public boolean cargaDatosArchivo(String ruta) {
        listaEstudiantes.clear();
        File archivo = new File(ruta);

        if (!archivo.exists()){
            return false;
        }
        try {

            byte[] bytes = Files.readAllBytes(Paths.get(ruta));

            boolean tieneBomUtf8 = bytes.length >= 3 &&
                    (bytes[0] & 0xFF) == 0xEF &&
                    (bytes[1] & 0xFF) == 0xBB &&
                    (bytes[2] & 0xFF) == 0xBF;

            java.nio.charset.Charset seleccionado = StandardCharsets.UTF_8;

            if (!tieneBomUtf8) {
                String sUtf8 = new String(bytes, StandardCharsets.UTF_8);
                String sWin = new String(bytes, java.nio.charset.Charset.forName("windows-1252"));

                if (!sUtf8.matches(".*[áéíóúÁÉÍÓÚñÑ].*") &&
                        sWin.matches(".*[áéíóúÁÉÍÓÚñÑ].*")) {
                    seleccionado = java.nio.charset.Charset.forName("windows-1252");
                }
            }

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(new ByteArrayInputStream(bytes), seleccionado))) {

                String linea;
                boolean header = true;

                while ((linea = br.readLine()) != null) {
                    if (header) {
                        header = false;
                        continue;
                    }
                    if (!linea.trim().isEmpty()) procesarLineaCSV(linea);
                }
            }

            return true;

        } catch (IOException e) {
            System.out.println("Error leyendo archivo: " + e.getMessage());
            return false;
        }
    }


    public void procesarLineaCSV(String linea) {
        try {
            String[] datos = linea.split(",");

            if (datos.length < 21) return;

            String dni = datos[0];
            String nombres = datos[1];
            String apellidos = datos[2];
            String telefono = datos[7];
            String correo = datos[9];

            double n1 = Double.parseDouble(datos[17]);
            double n2 = Double.parseDouble(datos[18]);
            double n3 = Double.parseDouble(datos[19]);
            double n4 = Double.parseDouble(datos[20]);

            String retirado = (datos.length > 23) ? datos[23].trim() : "No";

            Alumno nuevo = new Alumno(dni, nombres, apellidos, correo, telefono, n1, n2, n3, n4, retirado);
            registrarAlumno(nuevo);

        } catch (Exception e) {
            System.out.println("Error procesando línea: " + e.getMessage());
        }
    }

    public void listarPromediosFiltro(int opcionFiltro) {
        if (listaEstudiantes.isEmpty()) {
            System.out.println("No hay alumnos registrados.");
            return;
        }

        String titulo = switch (opcionFiltro) {
            case 2 -> "SOLO ALUMNOS ACTIVOS";
            case 3 -> "SOLO ALUMNOS RETIRADOS";
            default -> "TODOS LOS ALUMNOS";
        };

        System.out.println("\n--- " + titulo + " ---");

        boolean hayResultados = false;

        for (int i = 0; i < listaEstudiantes.size(); i++) {

            Alumno alum = getAlumnoIndice(i);

            if (alum != null && alumnoCumpleFiltro(alum, opcionFiltro)) {
                System.out.println(alum);
                hayResultados = true;
            }
        }

        if (!hayResultados) {
            System.out.println("No hay alumnos en esta categoría.");
        }
    }

    private boolean alumnoCumpleFiltro(Alumno alumno, int opcionFiltro) {
        return opcionFiltro == 1 ||
               (opcionFiltro == 2 && alumno.estaActivo()) ||
               (opcionFiltro == 3 && !alumno.estaActivo());
    }

    public Alumno buscarAlumnoDni(String dni) {
        for (int i = 0; i < listaEstudiantes.size(); i++) {
            Alumno a = getAlumnoIndice(i);
            if (a != null && a.getDni().equals(dni)){
                return a;
            }
        }
        return null;
    }
    
    public void editarDatosAlumno(Scanner sc) {

        System.out.print("Ingrese DNI del alumno a editar: ");
        String dni = sc.nextLine();

        Alumno alumno = buscarAlumnoDni(dni);

        if (alumno == null) {
            System.out.println("Error: Alumno no encontrado.");
            return;
        }

        int op = 0;

        do {
            System.out.println("\n--- EDITANDO A: " + alumno.getNombreCompleto() + " ---");
            System.out.println("1. Actualizar Correo");
            System.out.println("2. Actualizar Teléfono");
            System.out.println("3. Corregir Nota 1");
            System.out.println("4. Corregir Nota 2");
            System.out.println("5. Volver");
            System.out.print("¿Qué desea hacer? ");

            try {
                String entrada = sc.nextLine();
                op = entrada.isEmpty() ? -1 : Integer.parseInt(entrada);

                switch (op) {
                    case 1 -> {
                        System.out.println("Correo actual: " + alumno.getCorreo());
                        System.out.print("Nuevo Correo: ");
                        alumno.setCorreo(sc.nextLine());
                        System.out.println(">> Correo actualizado.");
                    }

                    case 2 -> {
                        System.out.println("Teléfono actual: " + alumno.getTelefono());
                        System.out.print("Nuevo Teléfono: ");
                        alumno.setTelefono(sc.nextLine());
                        System.out.println(">> Teléfono actualizado.");
                    }

                    case 3 -> {
                        System.out.println("Nota 1 actual: " + alumno.getNota1());
                        System.out.print("Nueva Nota 1: ");
                        double n1 = Double.parseDouble(sc.nextLine());
                        alumno.setNota1(n1);
                        System.out.println(">> Nota actualizada. Nuevo promedio: " + alumno.getPromedio());
                    }

                    case 4 -> {
                        System.out.println("Nota 2 actual: " + alumno.getNota2());
                        System.out.print("Nueva Nota 2: ");
                        double n2 = Double.parseDouble(sc.nextLine());
                        alumno.setNota2(n2);
                        System.out.println(">> Nota actualizada. Nuevo promedio: " + alumno.getPromedio());
                    }
                    case 5 -> {
                        System.out.println("Nota 3 actual: " + alumno.getNota3());
                        System.out.print("Nueva Nota 3: ");
                        double n3 = Double.parseDouble(sc.nextLine());
                        alumno.setNota3(n3);
                        System.out.println(">> Nota actualizada. Nuevo promedio: " + alumno.getPromedio());
                    }
                    case 6 -> {
                        System.out.println("Nota 4 actual: " + alumno.getNota4());
                        System.out.print("Nueva Nota 4: ");
                        double n4 = Double.parseDouble(sc.nextLine());
                        alumno.setNota4(n4);
                        System.out.println(">> Nota actualizada. Nuevo promedio: " + alumno.getPromedio());
                    }

                    case 7 -> System.out.println("Regresando...");

                    default -> System.out.println("Opción no válida.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            }

        } while (op != 5);
    }
    
    public void exportarCSV(String ruta) {

        if (listaEstudiantes.isEmpty()) {
            System.out.println("No hay alumnos para exportar.");
            return;
        }

        File archivo = new File(ruta);

        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(archivo), StandardCharsets.UTF_8))) {

            bw.write('\uFEFF'); // BOM UTF-8

            bw.write("DNI,Nombres,Apellidos,Telefono,Correo,Nota1,Nota2,Nota3,Nota4,Promedio,Estado,Situacion");
            bw.newLine();

            for (int i = 0; i < listaEstudiantes.size(); i++) {
                Alumno a = getAlumnoIndice(i);

                String base = a.generarLineaCSV();

                String linea = String.format(
                        java.util.Locale.US,
                        "%s,%.2f,%s,%s",
                        base,
                        a.getPromedio(),
                        a.getEstadoAcademico(),
                        a.getSituacionTexto()
                );

                bw.write(linea);
                bw.newLine();
            }

            System.out.println("Archivo exportado correctamente a: " + ruta);

        } catch (IOException e) {
            System.out.println("Error al guardar archivo: " + e.getMessage());
        }
    }
}
