/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.programaescuela;
import com.mycompany.programaescuela.model.RegistroAcademico;
import com.mycompany.programaescuela.model.Apoderado;
import com.mycompany.programaescuela.model.Grado;
import com.mycompany.programaescuela.model.Alumno;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
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

    public void agregarAlumno(Alumno alumno) {
        alumnos.add(alumno);
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public Alumno buscarPorDni(String dni) {
        for (Alumno a : alumnos) {
            if (a.getDni().equalsIgnoreCase(dni)) return a;
        }
        return null;
    }

    public List<Alumno> buscarPorNombre(String nombre) {
        List<Alumno> resultados = new ArrayList<>();
        for (Alumno a : alumnos) {
            if ((a.getNombres() + " " + a.getApellidos()).toLowerCase().contains(nombre.toLowerCase())) {
                resultados.add(a);
            }
        }
        return resultados;
    }

    public void retirarAlumno(String dni) {
        Alumno a = buscarPorDni(dni);
        if (a != null) a.setRetirado(true);
    }

    // ------------------- CSV -------------------
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
            if (!tieneBomUtf8) charsetSeleccionado = java.nio.charset.Charset.forName("windows-1252");

            try (BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bytes), charsetSeleccionado))) {
                String linea = br.readLine();
                if (linea == null) return true;

                // Detectar separador
                if (linea.startsWith("sep=") || linea.startsWith("SEP=")) {
                    this.sep = linea.substring(4).trim();
                    linea = br.readLine(); // leer encabezado
                } else {
                    int cuentaPuntoComa = linea.length() - linea.replace(";", "").length();
                    int cuentaComa = linea.length() - linea.replace(",", "").length();
                    this.sep = (cuentaPuntoComa > cuentaComa) ? ";" : ",";
                }

                System.out.println("[Sistema] Separador detectado: " + this.sep);

                if (linea != null && linea.toLowerCase().contains("dni")) linea = br.readLine();

                while (linea != null) {
                    if (!linea.trim().isEmpty()) {
                        Alumno a = procesarLineaCSV(linea);
                        if (a != null) alumnos.add(a);
                    }
                    linea = br.readLine();
                }
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error leyendo archivo: " + e.getMessage());
            return false;
        }
    }

    private Alumno procesarLineaCSV(String linea) {
        String[] c = linea.split(Pattern.quote(sep), -1);
        if (c.length < 24) return null; // mínimo de columnas

        try {
            // -------- Datos del alumno --------
            String dni = c[0];
            String nombres = c[1];
            String apellidos = c[2];
            char genero = c[3].isEmpty() ? ' ' : c[3].charAt(0);

            String seccion = c[4];
            String nivel = c[5];
            String gradoText = c[6];

            String telefono = c[7];
            String correo = c[9];
            String direccion = c[10];

            // -------- Apoderado --------
            String nomApo = c[12];
            String apeApo = c[13];
            char genApo = c[14].isEmpty() ? ' ' : c[14].charAt(0);
            String telApo = c[16];

            Apoderado apoderado = new Apoderado(nomApo, apeApo, genApo, telApo);
            Grado grado = new Grado(nivel, gradoText, seccion);

            // -------- Registro académico --------
            double n1 = Double.parseDouble(c[17]);
            double n2 = Double.parseDouble(c[18]);
            double n3 = Double.parseDouble(c[19]);
            double n4 = Double.parseDouble(c[20]);
            double asistencia = Double.parseDouble(c[21]);
            String comportamiento = c[22];

            RegistroAcademico registro = new RegistroAcademico(n1, n2, n3, n4, asistencia, comportamiento);

            // -------- Retirado --------
            boolean retirado = c[23].trim().equalsIgnoreCase("Sí");

            Alumno alumno = new Alumno(dni, nombres, apellidos, genero, telefono, correo, direccion, apoderado, grado, registro);
            alumno.setRetirado(retirado);

            return alumno;
        } catch (Exception e) {
            System.out.println("Error procesando línea CSV: " + linea + " -> " + e.getMessage());
            return null;
        }
    }

    public void guardarCSV(String archivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            bw.write("sep=" + sep);
            bw.newLine();
            // encabezado
            bw.write("DNI;Nombre;Apellidos;Género;Sección;Nivel;Grado;Teléfono;Operador Teléfono;Correo;Dirección;F/Nacimiento;Nombres Apoderado;Apellido Apoderado;Género Apoderado;Parentesco Apoderado;Teléfono Apoderado;Nota 1er Bimestre;Nota 2do Bimestre;Nota 3er Bimestre;Nota 4to Bimestre;Porcentaje Asistencia;Comportamiento;Retirado (Sí/No)");
            bw.newLine();

            for (Alumno a : alumnos) {
                bw.write(convertirCSV(a));
                bw.newLine();
            }
        } catch (IOException e) {}
    }

    private String convertirCSV(Alumno a) {
        RegistroAcademico r = a.getRegistroAcademico();
        Apoderado p = a.getApoderado();
        Grado g = a.getGrado();

        return String.join(sep,
                a.getDni(),
                a.getNombres(),
                a.getApellidos(),
                String.valueOf(a.getGenero()),
                g.getNivel(),
                g.getGradoText(),
                g.getSeccion(),
                a.getTelefono(),
                "", 
                a.getCorreo(),
                a.getDireccion(),
                "", 
                p.getNombre(),
                p.getApellido(),
                String.valueOf(p.getGenero()),
                "",
                p.getTelefono(),
                String.valueOf(r.getNota1()),
                String.valueOf(r.getNota2()),
                String.valueOf(r.getNota3()),
                String.valueOf(r.getNota4()),
                String.valueOf(r.getAsistencia()),
                r.getComportamiento(),
                a.isRetirado() ? "Sí" : "No"
        );
    }


    // ------------------- Reportes -------------------
    public String generarReporteEstadisticas() {
        if (alumnos.isEmpty()) return "No hay datos para analizar.";

        int total = 0, aprobados = 0, desaprobados = 0, retirados = 0;
        double sumaPromedios = 0;

        int rangoMalo = 0;      // 0 - 10.4
        int rangoRegular = 0;   // 10.5 - 14
        int rangoBueno = 0;     // 15 - 17
        int rangoExcel = 0;     // 18 - 20

        double sumaM = 0, sumaF = 0;
        int countM = 0, countF = 0;

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

            if (p >= 12) aprobados++; else desaprobados++;

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
        sb.append("Total Activos: ").append(total).append("\n");
        sb.append("Total Retirados: ").append(retirados).append("\n");
        sb.append(String.format("PROMEDIO DEL SALÓN: %.2f\n", (total > 0 ? sumaPromedios/total : 0)));
        sb.append("Aprobados: ").append(aprobados).append(" (").append(total > 0 ? (aprobados*100/total) : 0).append("%)\n");
        sb.append("Desaprobados: ").append(desaprobados).append(" (").append(total > 0 ? (desaprobados*100/total) : 0).append("%)\n\n");

        sb.append("--- 2. DISTRIBUCIÓN DE RENDIMIENTO ---\n");
        sb.append("Excelencia [18-20]: ").append(rangoExcel).append("\n");
        sb.append("Bueno      [15-17]: ").append(rangoBueno).append("\n");
        sb.append("Regular    [11-14]: ").append(rangoRegular).append("\n");
        sb.append("Crítico    [00-10]: ").append(rangoMalo).append("\n\n");

        sb.append("--- 3. RANKING ---\n");
        if (mejorAlumno != null)
            sb.append("1er Puesto: ").append(mejorAlumno.getNombres()).append(" [")
              .append(String.format("%.2f", mejorAlumno.getRegistroAcademico().getPromedio())).append("]\n");
        if (peorAlumno != null)
            sb.append("Último Puesto: ").append(peorAlumno.getNombres()).append(" [")
              .append(String.format("%.2f", peorAlumno.getRegistroAcademico().getPromedio())).append("]\n");

        sb.append("\n--- 4. ANÁLISIS DEMOGRÁFICO (Género) ---\n");
        sb.append(String.format("Promedio Hombres (%d): %.2f\n", countM, (countM > 0 ? sumaM/countM : 0)));
        sb.append(String.format("Promedio Mujeres (%d): %.2f\n", countF, (countF > 0 ? sumaF/countF : 0)));

        return sb.toString();
    }
}
