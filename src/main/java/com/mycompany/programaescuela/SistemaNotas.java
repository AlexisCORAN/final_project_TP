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
    private String sep = ",";

    private static class OpcionMenu {
        String titulo;
        BiConsumer<Alumno, Scanner> accion;

        public OpcionMenu(String titulo, BiConsumer<Alumno, Scanner> accion) {
            this.titulo = titulo;
            this.accion = accion;
        }
    }

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

        if (!archivo.exists()) return false;

        try {
            byte[] bytes = Files.readAllBytes(Paths.get(ruta));
            boolean tieneBomUtf8 = bytes.length >= 3 &&
                    (bytes[0] & 0xFF) == 0xEF &&
                    (bytes[1] & 0xFF) == 0xBB &&
                    (bytes[2] & 0xFF) == 0xBF;

            java.nio.charset.Charset charsetSeleccionado = StandardCharsets.UTF_8;

            if (!tieneBomUtf8) {
                String sUtf8 = new String(bytes, StandardCharsets.UTF_8);
                String sWin = new String(bytes, java.nio.charset.Charset.forName("windows-1252"));
                if (!sUtf8.matches(".*[áéíóúÁÉÍÓÚñÑ].*") && sWin.matches(".*[áéíóúÁÉÍÓÚñÑ].*")) {
                    charsetSeleccionado = java.nio.charset.Charset.forName("windows-1252");
                }
            }

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(new ByteArrayInputStream(bytes), charsetSeleccionado))) {

                String linea = br.readLine();

                if (linea != null) {
                    if (linea.startsWith("sep=") || linea.startsWith("SEP=")) {
                        String posible = linea.length() > 4 ? linea.substring(4) : ",";
                        this.sep = posible;
                        
                        linea = br.readLine();
                    } else {
                        int cuentaPuntoComa = linea.length() - linea.replace(";", "").length();
                        int cuentaComa = linea.length() - linea.replace(",", "").length();
                        this.sep = (cuentaPuntoComa > cuentaComa) ? ";" : ",";
                       
                    }

                    while ((linea = br.readLine()) != null) {
                        if (!linea.trim().isEmpty()) procesarLineaCSV(linea);
                    }
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
            String[] datos = linea.split(Pattern.quote(this.sep), -1);

            if (datos.length < 21) return;

            String dni = datos[0].trim();
            String nombres = datos[1].trim();
            String apellidos = datos[2].trim();
            char genero = datos[3].trim().charAt(0);
            String seccion = datos[4].trim();
            String nivel = datos[5].trim();
            String grado = datos[6].trim();
            String telefono = datos[7].trim();
            String correo = datos[9].trim();
            String direccion = datos[10].trim();
            String nombreApoderado = datos[12].trim();
            String apellidoApoderado = datos[13].trim();
            char generoApoderado = datos[14].charAt(0);
            String parentescoApoderado = datos[15].trim();
            String telefonoApoderado = datos[16].trim();
        
   
            double n1 = parsearDouble(datos[17]);
            double n2 = parsearDouble(datos[18]);
            double n3 = parsearDouble(datos[19]);
            double n4 = parsearDouble(datos[20]);

            double porcentajeAsistencia = parsearDouble(datos[21]);
            String comportamiento = datos[22].trim();
            
            String retirado = (datos.length > 23) ? datos[23].trim() : "No";
            
            
            Alumno nuevo = new Alumno(
                dni,
                nombres,
                apellidos,
                genero,
                seccion,
                nivel,
                grado,
                telefono,
                correo,
                direccion,
                nombreApoderado,
                apellidoApoderado,
                generoApoderado,
                parentescoApoderado,
                telefonoApoderado,
                n1,
                n2,
                n3,
                n4,
                retirado,
                porcentajeAsistencia,
                comportamiento) ;
            registrarAlumno(nuevo);


        } catch (Exception e) {
            System.out.println("Error procesando línea: " + e.getMessage());
        }
    }
    
    private double parsearDouble(String val) {
        if (val == null) return 0.0;
        String t = val.replace("\"", "").trim();
        if (t.isEmpty()) return 0.0;
        if (t.contains(",") && !t.contains(".")) t = t.replace(',', '.');
        try { return Double.parseDouble(t); } catch (Exception e) { return 0.0; }
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

    public boolean retirarAlumno(String dni) {
        Alumno a = buscarAlumnoDni(dni);
        if (a == null) return false;
        if (!a.estaActivo()) return false; // ya retirado
        a.retirar();
        return true;
    }
    
    public void editarDatosAlumno(Scanner sc) {
        System.out.print("Ingrese DNI del alumno a editar: ");
        String dni = sc.nextLine().trim();
        Alumno alumno = buscarAlumnoDni(dni);

        if (alumno == null) {
            System.out.println("Error: Alumno no encontrado.");
            return;
        }

        List<OpcionMenu> opciones = new ArrayList<>();
        
        // --- DATOS DEL ALUMNO ---
        opciones.add(new OpcionMenu("Actualizar Sección", (a, s) -> {
            System.out.println("La sección actual: " + a.getSeccion());
            System.out.print("Ingrese la nueva sección: "); a.setSeccion(s.nextLine());
        }));
        opciones.add(new OpcionMenu("Actualizar Nivel", (a, s) -> {
            System.out.println("El nivel actual: " + a.getNivel());
            System.out.print("Ingrese el nuevo nivel: "); a.setNivel(s.nextLine());
        }));
        opciones.add(new OpcionMenu("Actualizar Grado", (a, s) -> {
            System.out.println("El grado actual: " + a.getGrado());
            System.out.print("Ingrese el nuevo grado: "); a.setGrado(s.nextLine());
        }));
        opciones.add(new OpcionMenu("Actualizar Teléfono", (a, s) -> {
            System.out.println("El teléfono actual: " + a.getTelefono());
            System.out.print("Ingrese el nuevo teléfono: "); a.setTelefono(s.nextLine());
        }));
        opciones.add(new OpcionMenu("Actualizar Correo", (a, s) -> {
            System.out.println("El correo actual: " + a.getCorreo());
            System.out.print("Ingrese el nuevo correo: "); a.setCorreo(s.nextLine());
        }));
        opciones.add(new OpcionMenu("Actualizar Dirección", (a, s) -> {
            System.out.println("La direccion actual: " + a.getDireccion());
            System.out.print("Ingrese la nueva dirección: "); a.setDireccion(s.nextLine());
        }));

        // --- DATOS DEL APODERADO ---
        opciones.add(new OpcionMenu("Actualizar Nombres del Apoderado", (a, s) -> {
            System.out.println("Los nombres del apoderado actual: " + a.getNombreApoderado());
            System.out.print("Ingrese los nuevos nombres del apoderado: "); a.setNombreApoderado(s.nextLine());
        }));
        opciones.add(new OpcionMenu("Actualizar Apellidos del Apoderado", (a, s) -> {
            System.out.println("Los apellidos del apoderado actual:" + a.getApellidoApoderado());
            System.out.print("Ingrese los nuevos apellidos del apoderado: "); a.setApellidoApoderado(s.nextLine());
        }));
        opciones.add(new OpcionMenu("Actualizar Género del Apoderado", (a, s) -> {
            System.out.println("El genero del apoderado actual: " + a.getGeneroApoderado());
            System.out.print("ingrese el nuevo género del poderado (M/F): ");
            String g = s.nextLine().trim(); 
            if (!g.isEmpty()) a.setGeneroApoderado(g.charAt(0));
        }));
        opciones.add(new OpcionMenu("Actualizar Parentesco con el Alumno", (a, s) -> {
            System.out.println("El parentesco actual: " + a.getParentescoApoderado());
            System.out.print("Ingrese el nuevo parentesco: "); a.setParentescoApoderado(s.nextLine());
        }));
        opciones.add(new OpcionMenu("Actualizar Teléfono del Apoderado", (a, s) -> {
            System.out.println(" El teléfono del apoderado actual: " + a.getTelefonoApoderado());
            System.out.print("Ingrese el nuevo teléfono del apoderado: "); a.setTelefonoApoderado(s.nextLine());
        }));

        // --- NOTAS Y ASISTENCIA ---
        opciones.add(new OpcionMenu("Actualizar Nota 1", (a, s) -> {
            System.out.println("La nota 1 actual: " + a.getNota1());
            double n = leerDouble(s, "ingrese la nueva Nota 1: "); 
            if (n >= 0) a.setNota1(n);
        }));
        opciones.add(new OpcionMenu("Actualizar Nota 2", (a, s) -> {
            System.out.println("La nota 2 actual: " + a.getNota2());
            double n = leerDouble(s, "Ingrese la nueva Nota 2: "); 
            if (n >= 0) a.setNota2(n);
        }));
        opciones.add(new OpcionMenu("Actualizar Nota 3", (a, s) -> {
            System.out.println("La nota actual: " + a.getNota3());
            double n = leerDouble(s, "Ingrese la nueva Nota 3: "); 
            if (n >= 0) a.setNota3(n);
        }));
        opciones.add(new OpcionMenu("Actualizar Nota 4", (a, s) -> {
            System.out.println(" La nota actual: " + a.getNota4());
            double n = leerDouble(s, "Ingrese la nueva Nota 4: "); 
            if (n >= 0) a.setNota4(n);
        }));
        opciones.add(new OpcionMenu("Actualizar Porcentaje Asistencia", (a, s) -> {
            System.out.println("El porcentaje de asistencia actual: " + a.getPorcentajeAsistencia() + "%");
            double p = leerDouble(s, "Ingrese el nuevo porcentaje de asistencia (0-100): "); 
            if (p >= 0) a.setPorcentajeAsistencia(p);
        }));
        opciones.add(new OpcionMenu("Actualizar Comportamiento", (a, s) -> {
            System.out.println("El comportamiento actual: " + a.getComportamiento());
            System.out.print("Ingrese el nuevo comportamiento: "); a.setComportamiento(s.nextLine());
        }));

        // --- ESTADO ---
        opciones.add(new OpcionMenu("Retirar/Reincorporar Alumno", (a, s) -> {
            System.out.println(" El estado actual: " + (a.estaRetirado() ? "RETIRADO" : "ACTIVO"));
            if (a.estaRetirado()) { 
                a.reintegrar(); 
                System.out.println(">> Alumno reincorporado (ACTIVO)."); 
            } else { 
                a.retirar(); 
                System.out.println(">> Alumno retirado (INACTIVO)."); 
            }
        }));
        
        
        while (true) {
            System.out.println("\n--- EDITANDO A: " + alumno.getNombreCompleto() + " ---");
            
            for (int i = 0; i < opciones.size(); i++) {
                System.out.println((i + 1) + ". " + opciones.get(i).titulo);
            }
            System.out.println((opciones.size() + 1) + ". Volver");

            int seleccion = (int) leerDouble(sc, "Elija una opción: "); 

            if (seleccion == opciones.size() + 1) break;
        

            if (seleccion > 0 && seleccion <= opciones.size()) {

                opciones.get(seleccion - 1).accion.accept(alumno, sc);
                System.out.println(">> Cambio realizado con éxito.");
            } else {
                System.out.println("Opción inválida.");
            }
        }
    }

    private double leerDouble(Scanner sc, String mensaje) {
        System.out.print(mensaje);
        try {
            String val = sc.nextLine().replace(",", ".");
            return val.isEmpty() ? -1 : Double.parseDouble(val);
        } catch (NumberFormatException e) {
            System.out.println("Valor numérico inválido.");
            return -1;
        }
    }
    
    public void exportarCSV(String ruta) {

        if (listaEstudiantes.isEmpty()) {
            System.out.println("No hay alumnos para exportar.");
            return;
        }

        try (FileOutputStream fos = new FileOutputStream(ruta);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8))) {

            fos.write(0xEF);
            fos.write(0xBB);
            fos.write(0xBF);


            bw.write("sep=" + this.sep);
            bw.newLine();


            String cabecera = String.join(this.sep, 
                "DNI", 
                "Nombres", 
                "Apellidos", 
                "Genero", 
                "Seccion",
                "Nivel", 
                "Grado",
                "Telefono", 
                "Correo", 
                "Direccion", 
                "NombreApoderado",    
                "ApellidoApoderado",  
                "GeneroApoderado",  
                "Parentesco",         
                "TelefonoApoderado",  
                "Nota1", 
                "Nota2", 
                "Nota3", 
                "Nota4", 
                "Promedio", 
                "Asistencia %",       
                "Comportamiento", 
                "Estado Academico", 
                "Retirado"
            );
            
            bw.write(cabecera);
            bw.newLine();

            for (int i = 0; i < listaEstudiantes.size(); i++) {
                Alumno a = getAlumnoIndice(i);
        
                String linea = a.generarLineaCSV(this.sep);

                bw.write(linea);
                bw.newLine();
            }

            System.out.println("Archivo exportado correctamente a: " + ruta);
            System.out.println("Separador usado: [" + this.sep + "]");

        } catch (IOException e) {
            System.out.println("Error al guardar archivo: " + e.getMessage());
        }
    }
}
