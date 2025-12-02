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
import java.util.List;
import java.util.regex.Pattern;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
/**
 *
 * @author Alexis
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

public class SistemaNotas {
    
    

    private final List<Alumno> alumnos = new ArrayList<>();
    private String sep = ";";
    
    private static final Pattern patronCorreo = Pattern.compile("^[A-Za-z0-9._%+-]+@gmail\\.com$");
    private static final Pattern patronTelefono = Pattern.compile("^9[0-9]{8}$");
    private static final Pattern patronNyA = Pattern.compile("^[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+ [A-ZÁÉÍÓÚÑ][a-záéíóúñ]+$");
    private static final Pattern patronNota = Pattern.compile("^[0-9]+.[0-9]$");
    private static final Pattern patronDNI = Pattern.compile("^[678][0-9]{7}$");
    private static final Pattern patronDireccion = Pattern.compile("^([A-ZÁÉÍÓÚÑ][a-záéíóúñ]*)(\\s[A-ZÁÉÍÓÚÑ][a-záéíóúñ]*)*$");
    
    
    
    public static boolean validarCorreo(String correo) {
        return patronCorreo.matcher(correo).matches();
    }

    public static boolean validarTelefono(String dni) {
        return patronTelefono.matcher(dni).matches();
    }

    public static boolean validarNota(String nota) {
        Matcher m = patronNota.matcher(nota);
        if (!m.matches()) return false;
        double valor = Double.parseDouble(nota);
        return valor >= 0 && valor <= 20;

    }

    public static boolean validarNyA(String nya) {
        return patronNyA.matcher(nya).matches();
    }

    public static boolean validarDNI(String dni) {
        return patronDNI.matcher(dni).matches();
    }
    
    public static boolean validarDireccion(String direc) {
        return patronDireccion.matcher(direc).matches();
    }
    
    public static boolean validarAsistencia(String asistencia) {
        Matcher m = patronNota.matcher(asistencia);
        if (!m.matches()) return false;
        double valor = Double.parseDouble(asistencia);
        return valor >= 0 && valor <= 100;

    }

    public void agregarAlumno(Alumno alumno) {
        alumnos.add(alumno);
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public Alumno buscarPorDni(String dni) {
        for (Alumno a : alumnos) {
            if (a.getDni().equalsIgnoreCase(dni)) {
                return a;
            }
        }
        return null;
    }

    public List<Alumno> buscarPorNombre(String nombre) {
        List<Alumno> resultados = new ArrayList<>();
        for (Alumno a : alumnos) {
            if ((a.getNombres() + " " + a.getApellidos())
                    .toLowerCase()
                    .contains(nombre.toLowerCase())) {
                resultados.add(a);
            }
        }
        return resultados;
    }

    public void retirarAlumno(String dni) {
        Alumno a = buscarPorDni(dni);
        if (a != null) {
            a.setRetirado(true);
        }
    }

    public boolean cargarDesdeCSV(String nombreArchivo) {
        alumnos.clear();
        
        File archivo = new File(nombreArchivo);

        if (!archivo.exists()) return false;

        try {
            
            byte[] bytes = Files.readAllBytes(Paths.get(nombreArchivo));
            
            boolean tieneBomUtf8 = bytes.length >= 3 &&
                    (bytes[0] & 0xFF) == 0xEF &&
                    (bytes[1] & 0xFF) == 0xBB &&
                    (bytes[2] & 0xFF) == 0xBF;

            
            java.nio.charset.Charset charsetSeleccionado = StandardCharsets.UTF_8;

            
            if (!tieneBomUtf8) {
                
                charsetSeleccionado = java.nio.charset.Charset.forName("windows-1252");
            }

            
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(new ByteArrayInputStream(bytes), charsetSeleccionado))) {

                String linea = br.readLine();

          
                if (linea == null) return true;

               
                if (linea.startsWith("sep=") || linea.startsWith("SEP=")) {
                    this.sep = linea.substring(4).trim(); 
                    linea = br.readLine(); 
                } else {
                    
                    int cuentaPuntoComa = linea.length() - linea.replace(";", "").length();
                    int cuentaComa = linea.length() - linea.replace(",", "").length();
                    
                    this.sep = (cuentaPuntoComa > cuentaComa) ? ";" : ",";
                }
                
                System.out.println("[Sistema] Separador detectado: " + this.sep);

   
                while ((linea = br.readLine()) != null) {
                    if (!linea.trim().isEmpty()) {
                        Alumno a = procesarLineaCSV(linea);
                            if (a != null) {
                               alumnos.add(a);             
                            }
                        
                    }
                }
            }
            return true;

        } catch (IOException e) {
            System.out.println("Error leyendo archivo: " + e.getMessage());
            return false;
        }
    }

    private Alumno procesarLineaCSV(String linea) {

        String[] c = linea.split("\t|,", -1);

        if (c.length < 24) return null; // línea incompleta

        // -------- Datos del alumno --------
        String dni = c[0];
        String nombres = c[1];
        String apellidos = c[2];
        char genero = c[3].charAt(0);

        // -------- Grado --------
        String seccion = c[4];
        String nivel = c[5];
        int gradoNum = convertirGrado(c[6]);

        Grado grado = new Grado(nivel, gradoNum, seccion);

        // -------- Contacto alumno --------
        String telefono = c[7];
        String correo = c[9];
        String direccion = c[10];

        // -------- Apoderado --------
        String nomApo = c[12];
        String apeApo = c[13];
        char genApo = c[14].charAt(0);
        String telApo = c[16];

        Apoderado apoderado = new Apoderado("-", nomApo, apeApo, genApo, telApo);

        // -------- Registro académico --------
        double n1 = Double.parseDouble(c[17]);
        double n2 = Double.parseDouble(c[18]);
        double n3 = Double.parseDouble(c[19]);
        double n4 = Double.parseDouble(c[20]);
        double asistencia = Double.parseDouble(c[21]);
        String comportamiento = c[22];

        RegistroAcademico registro = new RegistroAcademico(
                n1, n2, n3, n4, asistencia, comportamiento
        );

        // -------- Retirado --------
        boolean retirado = c[23].trim().equalsIgnoreCase("Sí");

        // -------- Crear alumno --------
        Alumno alumno = new Alumno(
                dni, nombres, apellidos, genero,
                telefono, correo, direccion,
                apoderado, grado, registro
        );

        alumno.setRetirado(retirado);

        return alumno;
    }
    
    public int convertirGrado(String valor) {
    valor = valor.trim().toLowerCase();
        switch (valor) {
            case "3 años", "primero" -> {
                return 1;
            }
            case "4 años", "segundo" -> {
                return 2;
            }
            case "5 años", "tercero" -> {
                return 3;
            }
            case "cuarto" -> {
                return 4;
            }
            case "quinto" -> {
                return 5;
            }
            case "sexto" -> {
                return 6;
            }
            default -> throw new IllegalArgumentException("Grado inválido: " + valor);
        }
    }
    
    int convertirGradoInterno(String valor) {
        return convertirGrado(valor); // llama al método privado existente
    }

    public void guardarCSV(String archivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {

            for (Alumno a : alumnos) {
                bw.write(convertirCSV(a));
                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String convertirCSV(Alumno a) {
        RegistroAcademico r = a.getRegistroAcademico();
        Apoderado p = a.getApoderado();
        Grado g = a.getGrado();

        return a.getDni() + "," +
                a.getNombres() + "," +
                a.getApellidos() + "," +
                a.getGenero() + "," +
                a.getTelefono() + "," +
                a.getCorreo() + "," +
                a.getDireccion() + "," +
                p.getDni() + "," +
                p.getNombre() + "," +
                p.getApellido() + "," +
                p.getGenero() + "," +
                p.getTelefono() + "," +
                g.getNivel() + "," +
                g.getGrado() + "," +
                g.getSeccion() + "," +
                r.getNota1() + "," +
                r.getNota2() + "," +
                r.getNota3() + "," +
                r.getNota4() + "," +
                r.getAsistencia() + "," +
                r.getComportamiento() + "," +
                a.isRetirado();
    }
    
    public String generarReporteEstadisticas() {
        if (alumnos.isEmpty()) return "No hay datos para analizar.";

        int total = 0, aprobados = 0, desaprobados = 0, retirados = 0;
        double sumaPromedios = 0;
        
        // Variables para Rangos
        int rangoMalo = 0;      // 0 - 10.4
        int rangoRegular = 0;   // 10.5 - 14
        int rangoBueno = 0;     // 15 - 17
        int rangoExcel = 0;     // 18 - 20

        // Variables para Género
        double sumaM = 0, sumaF = 0;
        int countM = 0, countF = 0;

        // Variables para Ranking
        Alumno mejorAlumno = null;
        Alumno peorAlumno = null;

        for (Alumno a : alumnos) {
            if (a.isRetirado()) {
                retirados++;
                continue; 
            }

            total++;
            double p = a.getRegistroAcademico().getPromedio();
            sumaPromedios += p;

            if (p >= 12) {
                aprobados++;
            } else {
                desaprobados++;
            }

            if (p < 10.5) rangoMalo++;
            else if (p <= 14) rangoRegular++;
            else if (p <= 17) rangoBueno++;
            else rangoExcel++;

            
            if (a.getGenero() == 'M') { sumaM += p; countM++; }
            else if (a.getGenero() == 'F') { sumaF += p; countF++; }

 
            if (mejorAlumno == null || p > mejorAlumno.getRegistroAcademico().getPromedio()) mejorAlumno = a;
            if (peorAlumno == null || p < peorAlumno.getRegistroAcademico().getPromedio()) peorAlumno = a;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("========== ANALÍTICA ACADÉMICA ==========\n\n");
        
        sb.append("--- 1. RESUMEN GENERAL ---\n");
        sb.append("Total Activos:      ").append(total).append("\n");
        sb.append("Total Retirados:    ").append(retirados).append("\n");
        sb.append(String.format("PROMEDIO DEL SALÓN: %.2f\n", (total > 0 ? sumaPromedios/total : 0)));
        sb.append("Aprobados:          ").append(aprobados).append(" (").append(total > 0 ? (aprobados*100/total) : 0).append("%)\n");
        sb.append("Desaprobados:       ").append(desaprobados).append(" (").append(total > 0 ? (desaprobados*100/total) : 0).append("%)\n\n");

        sb.append("--- 2. DISTRIBUCIÓN DE RENDIMIENTO ---\n");
        sb.append("Excelencia [18-20]: ").append(rangoExcel).append("\n");
        sb.append("Bueno      [15-17]: ").append(rangoBueno).append("\n");
        sb.append("Regular    [11-14]: ").append(rangoRegular).append("\n");
        sb.append("Crítico    [00-10]: ").append(rangoMalo).append("\n\n");

        sb.append("--- 3. RANKING ---\n");
        if (mejorAlumno != null) {
            sb.append(" 1er Puesto: ").append(mejorAlumno.getNombres())
              .append(" [").append(String.format("%.2f", mejorAlumno.getRegistroAcademico().getPromedio())).append("]\n");
        }
        if (peorAlumno != null) {
            sb.append("️Último Puesto: ").append(peorAlumno.getNombres())
              .append(" [").append(String.format("%.2f", peorAlumno.getRegistroAcademico().getPromedio())).append("]\n");
        }
        sb.append("\n");

        sb.append("--- 4. ANÁLISIS DEMOGRÁFICO (Género) ---\n");
        sb.append(String.format("Promedio Hombres (%d): %.2f\n", countM, (countM>0 ? sumaM/countM : 0)));
        sb.append(String.format("Promedio Mujeres (%d): %.2f\n", countF, (countF>0 ? sumaF/countF : 0)));

        return sb.toString();
    }

}
