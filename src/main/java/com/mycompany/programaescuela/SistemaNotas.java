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
    
    public void agregarAlumno(Alumno nuevo) {
        listaEstudiantes.add(nuevo);
    }
    
    public boolean cargaDatos(String ruta) {
        listaEstudiantes.clear();
        File archivo = new File(ruta);
        
        if (!archivo.exists()) return false;
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(ruta));

            // Detect BOM
            boolean hasUtf8Bom = bytes.length >= 3 && (bytes[0] & 0xFF) == 0xEF && (bytes[1] & 0xFF) == 0xBB && (bytes[2] & 0xFF) == 0xBF;

            java.nio.charset.Charset chosen = StandardCharsets.UTF_8;

            if (hasUtf8Bom) {
                chosen = StandardCharsets.UTF_8;
            } else {
                // Try decoding as UTF-8 and check for likely correct accented characters
                String sUtf8 = new String(bytes, StandardCharsets.UTF_8);
                // If contains typical Latin-1 extended chars (common in Spanish), assume UTF-8 is correct
                if (sUtf8.matches(".*[áéíóúÁÉÍÓÚñÑ].*")) {
                    chosen = StandardCharsets.UTF_8;
                } else {
                    // Otherwise try windows-1252 and see if that yields accented characters
                    String sWin = new String(bytes, java.nio.charset.Charset.forName("windows-1252"));
                    if (sWin.matches(".*[áéíóúÁÉÍÓÚñÑ].*")) {
                        chosen = java.nio.charset.Charset.forName("windows-1252");
                    } else {
                        // Default to UTF-8 if unsure
                        chosen = StandardCharsets.UTF_8;
                    }
                }
            }

            System.out.println("[SistemaNotas] Codificación detectada para lectura: " + chosen.name());
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bytes), chosen))) {
                String linea;
                boolean header = true;

                while ((linea = br.readLine()) != null) {
                    if (header) {
                        header = false;
                        continue;
                    }
                    if (!linea.trim().isEmpty()) {
                        procesarDatos(linea);
                    }
                }
            }

            return true;
        } catch (IOException e) {
            System.out.println("Error leyendo archivo: " + e.getMessage());
            return false;
        }
    }
    
    public void procesarDatos(String linea) {
        try {
            String[] datos = linea.split(","); 
      
            if (datos.length < 20) {
                 datos = linea.split(",");
            }
      
            if (datos.length > 20) {
                String dni = datos[0];
                String nombres  = datos[1];
                String apellidos = datos[2];
                String telefono = datos[7]; 
                String correo = datos[9];   

                double n1 = Double.parseDouble(datos[17]);
                double n2 = Double.parseDouble(datos[18]);
                double n3 = Double.parseDouble(datos[19]);
                double n4 = Double.parseDouble(datos[20]);
                
                Alumno nuevo = new Alumno(dni, nombres, apellidos, correo, telefono, n1, n2, n3, n4);
                agregarAlumno(nuevo);
            }
        } catch (Exception e) {
            System.out.println("Error procesando línea: " + e.getMessage());
        }
    }
    
    public void listarPromedios() {
        if (listaEstudiantes.isEmpty()) {
            System.out.println("No hay alumnos registrados.");
            return;
        }
        System.out.println("--- LISTADO DE ALUMNOS ---");
        
        for (int i = 0; i < listaEstudiantes.size(); i++) {
            Alumno a = listaEstudiantes.get(i);
            System.out.println(a.toString());
        }
    }

    public Alumno buscarPorDni(String dni) {
        for (int i = 0; i < listaEstudiantes.size(); i++) {
            Alumno a = listaEstudiantes.get(i);
            if (a.getDni().equals(dni)) {
                return a; 
            }
        }
        return null; 
    }

    public void actualizarDatosAlumno(Scanner sc) {
        System.out.print("Ingrese DNI del alumno a editar: ");
        String dni = sc.nextLine();
        
        Alumno alumno = buscarPorDni(dni);
        
        if (alumno != null) {
            int op = 0;
            do {
                System.out.println("\n--- EDITANDO A: " + alumno.getNombreCompleto() + " ---");
                System.out.println("1. Actualizar Correo");
                System.out.println("2. Actualizar Teléfono");
                System.out.println("3. Corregir Nota 1");
                System.out.println("4. Volver al Menú Principal");
                System.out.print("¿Qué desea hacer? ");
                
                try {
                    String entrada = sc.nextLine();
                    if (!entrada.isEmpty()) {
                        op = Integer.parseInt(entrada);
                    } else {
                        op = -1;
                    }
                    
                    switch(op) {
                        case 1:
                            System.out.println("Correo actual: " + alumno.getCorreo());
                            System.out.print("Nuevo Correo: ");
                            alumno.setCorreo(sc.nextLine());
                            System.out.println(">> Correo actualizado.");
                            break;
                        case 2:
                            System.out.println("Teléfono actual: " + alumno.getTelefono());
                            System.out.print("Nuevo Teléfono: ");
                            alumno.setTelefono(sc.nextLine());
                            System.out.println(">> Teléfono actualizado.");
                            break;
                        case 3:
                            System.out.println("Nota 1 actual: " + alumno.getNota1());
                            System.out.print("Nueva Nota 1: ");
                            double n1 = Double.parseDouble(sc.nextLine());
                            alumno.setNota1(n1); 
                            System.out.println(">> Nota actualizada. Nuevo promedio: " + alumno.getPromedio());
                            break;
                        case 4:
                            System.out.println("Regresando...");
                            break;
                        default:
                            System.out.println("Opción no válida.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Ingrese un número válido.");
                    op = 0;
                }
                
            } while (op != 4);
            
        } else {
            System.out.println("Error: Alumno con DNI " + dni + " no encontrado.");
        }
    }
    
    public void guardarArchivo(String ruta) {
        if (listaEstudiantes.isEmpty()) {
            System.out.println("No hay alumnos para exportar.");
            return;
        }

        File archivo = new File(ruta);

        try (BufferedWriter bw = new BufferedWriter(
            new OutputStreamWriter(new FileOutputStream(archivo), java.nio.charset.StandardCharsets.UTF_8)
        )) {
            
            bw.write('\uFEFF');
            
            bw.write("DNI,Nombres,Apellidos,Telefono,Correo,Nota1,Nota2,Nota3,Nota4,Promedio,Estado");
            bw.newLine();

            for (int i = 0; i < listaEstudiantes.size(); i++) {
                Alumno a = listaEstudiantes.get(i);
                String base = a.generarLineaCSV();

                String linea = String.format(java.util.Locale.US, "%s,%.2f,%s", base, a.getPromedio(), a.getEstado());
                bw.write(linea);
                bw.newLine();
            }

            System.out.println("Archivo exportado correctamente a: " + ruta);
        } catch (IOException e) {
            System.out.println("Error al guardar archivo: " + e.getMessage());
        }
    }
}