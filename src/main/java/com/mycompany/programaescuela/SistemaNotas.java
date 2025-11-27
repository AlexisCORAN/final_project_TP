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

    private ArrayList<Alumno> listaEstudiantes;
    private String sep = ",";
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
    
    private static String pedirSeccion(Scanner s) {
    String seccion;
    do {
        System.out.print("Ingrese la sección: ");
        seccion = s.nextLine();
        if (!seccion.equals("A") && !seccion.equals("B") && !seccion.equals("C")) {
            System.out.println("Error: Ingrese A, B o C");
        }
    } while (!seccion.equals("A") && !seccion.equals("B") && !seccion.equals("C"));
    return seccion;
    }
    
    private static String pedirNivel(Scanner s) {
    String nivel;
    do {
        System.out.print("Ingrese el nivel: ");
        nivel = s.nextLine();
        if (!nivel.equals("Inicial") && !nivel.equals("Primaria") && !nivel.equals("Secundaria")) {
            System.out.println("Error: Ingrese Inicial o Primaria o Secundaria");
        }
    } while (!nivel.equals("Inicial") && !nivel.equals("Primaria") && !nivel.equals("Secundaria"));
    return nivel;
    }
    
    private static String pedirGrado(Scanner s) {
    String ngrado;
    int c=1;
    do {
        System.out.print("Ingrese el grado: ");
        ngrado = s.nextLine();
        switch(ngrado) {
                case "Primero","Segundo","Tecero","Cuarto","Quinto","Sexto"-> c=2;          
                default-> { System.out.println("Error: Ingrese del 1 al 6 en formato ordinal");              
                           break;}
            }   
            } while (c==1);
    return ngrado;
    }
    
    private static String pedirComportamiento(Scanner s) {
    String comp;
    int c=1;
    do {
        System.out.print("Ingrese el comportamiento: ");
        comp = s.nextLine();
        switch(comp) {
                case "Excelente","Bueno","Regular","Malo"-> c=2;          
                default-> { System.out.println("Valores admitidos: Excelente, Bueno, Regular y Malo");              
                           break;}
            }   
            } while (c==1);
    return comp;
    }
    
    public String pedirTelefono(Scanner s) {
    String telefono;
    do {
        System.out.print("Ingrese el teléfono (9 dígitos, empezando con 9): ");
        telefono = s.nextLine();

        if (!SistemaNotas.validarTelefono(telefono)) {
            System.out.println("Teléfono inválido. Debe comenzar con 9 y tener 9 dígitos.");
        } else if (this.buscarTelef(telefono) != null) {
            System.out.println("Error: Ya existe un alumno con ese teléfono.");
        }

    } while (!SistemaNotas.validarTelefono(telefono) || this.buscarTelef(telefono) != null);

    return telefono;
    }
  
    public String pedirCorreo(Scanner s) {
    String correo;
    do{
                        correo = s.nextLine();
                        if (!SistemaNotas.validarCorreo(correo)) {
                            System.out.println("Correo inválido. Debe terminar en @gmail.com");}
                        if (this.buscarCorreo(correo) != null) {
                            System.out.println("Error: Ya existe un alumno con ese correo");
                        }
                        } while (!SistemaNotas.validarCorreo(correo)||this.buscarCorreo(correo) != null);

    return correo;
    }

    public String pedirDireccion(Scanner s) {
    String direccion;
    do{
                        direccion = s.nextLine();
                        if (!SistemaNotas.validarDireccion(direccion)) {
                            System.out.println("Correo inválido. Debe terminar en @gmail.com");}
                        if (this.buscarCorreo(direccion) != null) {
                            System.out.println("Error: Ya existe un alumno con ese correo");
                        }
                        } while (!SistemaNotas.validarDireccion(direccion)||this.buscarCorreo(direccion) != null);

    return direccion;
    }
   
    public String pedirNombreApoderado(Scanner s) {
    String nomApo;
    do{
                        nomApo = s.nextLine();
                        if (!SistemaNotas.validarNyA(nomApo)) {
                            System.out.println("Nombres incorrectos (Ejemplo correcto: Juan Gilbert)");}
                        if (this.buscarNyA(nomApo) != null) {
                            System.out.println("Error: Ya existe un apoderado/alumno con ese nombre.");}
                        
                        } while (!SistemaNotas.validarNyA(nomApo)||this.buscarNyA(nomApo) != null);

    return nomApo;
    }
    
    public String pedirApellidoApoderado(Scanner s) {
    String apeApo;
    do{
                        apeApo = s.nextLine();
                        if (!SistemaNotas.validarNyA(apeApo)) {
                            System.out.println("Apellidos incorrectos (Ejemplo correcto: Alva León)");}
                        if (this.buscarNyA(apeApo) != null) {
                            System.out.println("Error: Ya existe un apoderado/alumno con ese apellido.");}
                        
                        } while (!SistemaNotas.validarNyA(apeApo)||this.buscarNyA(apeApo) != null);

    return apeApo;
    }
    
    public String pedirGeneroApoderado(Scanner s) {
    String apeApo;
    do{
                        apeApo = s.nextLine();
                        if (!SistemaNotas.validarNyA(apeApo)) {
                            System.out.println("Apellidos incorrectos (Ejemplo correcto: Alva León)");}
                        if (this.buscarNyA(apeApo) != null) {
                            System.out.println("Error: Ya existe un apoderado/alumno con ese apellido.");}
                        
                        } while (!SistemaNotas.validarNyA(apeApo)||this.buscarNyA(apeApo) != null);

    return apeApo;
    }
    
    private static String pedirParentescoApoderado(Scanner s) {
    String parenApo;
    int d=1;
    do {
        
        parenApo = s.nextLine();
        switch(parenApo) {
                case "Tío","Tía","Padre","Madre","Abuelo","Abuela"-> d=2;          
                default-> { System.out.println("Error: Ingrese del 1 al 6 en formato ordinal");              
                           break;}
            }   
            } while (d==1);
    return parenApo;
    }
    
    public String pedirTelefonoApoderado(Scanner s) {
    String telefonoApo;
    do {
        System.out.print("Ingrese el teléfono (9 dígitos, empezando con 9): ");
        telefonoApo = s.nextLine();

        if (!SistemaNotas.validarTelefono(telefonoApo)) {
            System.out.println("Teléfono inválido. Debe comenzar con 9 y tener 9 dígitos.");
        } else if (this.buscarTelef(telefonoApo) != null) {
            System.out.println("Error: Ya existe un alumno con ese teléfono.");
        }

    } while (!SistemaNotas.validarTelefono(telefonoApo) || this.buscarTelef(telefonoApo) != null);

    return telefonoApo;
    }
    
    private static double pedirNota(Scanner s) {
    String notaa;
    double nota=-1;
    
    do {
            notaa=s.nextLine();
            if (!SistemaNotas.validarNota(notaa)) {
                            System.out.println("Nota inválida. Debe estar entre 0 a 20 y tener un sólo decimal (y que esté separado por un punto)");}
                        
                        } while (!SistemaNotas.validarNota(notaa));          
            
    nota = Double.parseDouble(notaa);
    return nota;
    }
    
    
    private static double pedirAsistencia(Scanner s) {
    String entrade;
    double porcentaje=-1;
    
    do {
            entrade=s.nextLine();
            if (!validarAsistencia(entrade)) {
                            System.out.println("Nota inválida. Debe estar entre 0 a 20 y tener un sólo decimal (y que esté separado por un punto)");}
                        
                        } while (!SistemaNotas.validarNota(entrade));          
            
    porcentaje = Double.parseDouble(entrade);
    return porcentaje;
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
    
    public Alumno buscarNyA(String nombre) {
        for (int i = 0; i < listaEstudiantes.size(); i++) {
            Alumno a = getAlumnoIndice(i);
            if (a != null && a.getNombre().equals(nombre)){
                return a;
            }
        }
        return null;
    }
    
    public Alumno buscarApellido(String apellido) {
        for (int i = 0; i < listaEstudiantes.size(); i++) {
            Alumno a = getAlumnoIndice(i);
            if (a != null && a.getApellido().equals(apellido)){
                return a;
            }
        }
        return null;
    }
    
    public Alumno buscarCorreo(String correo) {
        for (int i = 0; i < listaEstudiantes.size(); i++) {
            Alumno a = getAlumnoIndice(i);
            if (a != null && a.getCorreo().equals(correo)){
                return a;
            }
        }
        return null;
    }
    
    public Alumno buscarTelef(String telefono) {
        for (int i = 0; i < listaEstudiantes.size(); i++) {
            Alumno a = getAlumnoIndice(i);
            if (a != null && a.getTelefono().equals(telefono)){
                return a;
            }
        }
        return null;
    }
    
    public Alumno buscarNombreApoderado(String nomApo) {
        for (int i = 0; i < listaEstudiantes.size(); i++) {
            Alumno a = getAlumnoIndice(i);
            if (a != null && a.getNombreApoderado().equals(nomApo)){
                return a;
            }
        }
        return null;
    }
    
    public Alumno buscarApellidoApoderado(String apeApo) {
        for (int i = 0; i < listaEstudiantes.size(); i++) {
            Alumno a = getAlumnoIndice(i);
            if (a != null && a.getNombreApoderado().equals(apeApo)){
                return a;
            }
        }
        return null;
    }
    
    public Alumno buscarTelefonoApoderado(String telfApo) {
        for (int i = 0; i < listaEstudiantes.size(); i++) {
            Alumno a = getAlumnoIndice(i);
            if (a != null && a.getNombreApoderado().equals(telfApo)){
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

        
        String telefonoo;
        List<OpcionMenu> opciones = new ArrayList<>();
        
        // --- DATOS DEL ALUMNO ---
        
        opciones.add(new OpcionMenu("Actualizar Sección", (a, s) -> {  
            System.out.println("La sección actual: " + a.getSeccion());
            String nuevaSeccion = pedirSeccion(s);
            System.out.print("Ingrese la nueva sección: "); a.setSeccion(nuevaSeccion);
        }));
        opciones.add(new OpcionMenu("Actualizar Nivel", (a, s) -> {
            System.out.println("El nivel actual: " + a.getNivel());
            String nivel=pedirNivel(s);
            System.out.print("Ingrese el nuevo nivel: "); a.setNivel(nivel);
        }));
        opciones.add(new OpcionMenu("Actualizar Grado", (a, s) -> {
            System.out.println("El grado actual: " + a.getGrado());
            String ngrado=pedirGrado(s);
            System.out.print("Ingrese el nuevo grado: "); a.setGrado(ngrado);
        }));
        opciones.add(new OpcionMenu("Actualizar Teléfono", (a, s) -> {
            System.out.println("El teléfono actual: " + a.getTelefono());
            String nuevoTelefono = this.pedirTelefono(s);
            System.out.print("Ingrese el nuevo teléfono: "); a.setTelefono(nuevoTelefono);
        }));
        opciones.add(new OpcionMenu("Actualizar Correo", (a, s) -> {
            System.out.println("El correo actual: " + a.getCorreo());
            String nuevoCorreo= this.pedirCorreo(s);
            System.out.print("Ingrese el nuevo correo: "); a.setCorreo(nuevoCorreo);
        }));
        opciones.add(new OpcionMenu("Actualizar Dirección", (a, s) -> {
            System.out.println("La direccion actual: " + a.getDireccion());
            String nuevaDireccion=this.pedirDireccion(s);
            System.out.print("Ingrese la nueva dirección: "); a.setDireccion(nuevaDireccion);
        }));

        // --- DATOS DEL APODERADO ---
        opciones.add(new OpcionMenu("Actualizar Nombres del Apoderado", (a, s) -> {
            System.out.println("Los nombres del apoderado actual: " + a.getNombreApoderado());
            String nuevoNombreApo=this.pedirNombreApoderado(s);
            System.out.print("Ingrese los nuevos nombres del apoderado: "); a.setNombreApoderado(nuevoNombreApo);
        }));
        opciones.add(new OpcionMenu("Actualizar Apellidos del Apoderado", (a, s) -> {
            System.out.println("Los apellidos del apoderado actual:" + a.getApellidoApoderado());
            String nuevoApeApo=this.pedirApellidoApoderado(s);
            System.out.print("Ingrese los nuevos apellidos del apoderado: "); a.setApellidoApoderado(nuevoApeApo);
        }));
        opciones.add(new OpcionMenu("Actualizar Género del Apoderado", (a, s) -> {
            System.out.println("El genero del apoderado actual: " + a.getGeneroApoderado());
            System.out.print("ingrese el nuevo género del poderado (M/F): ");
            String g = this.pedirGeneroApoderado(s);
            if (!g.isEmpty()) a.setGeneroApoderado(g.charAt(0));
        }));
        opciones.add(new OpcionMenu("Actualizar Parentesco con el Alumno", (a, s) -> {
            System.out.println("El parentesco actual: " + a.getParentescoApoderado());
            String nuevoPP=this.pedirParentescoApoderado(s);
            System.out.print("Ingrese el nuevo parentesco: "); a.setParentescoApoderado(nuevoPP);
        }));
        opciones.add(new OpcionMenu("Actualizar Teléfono del Apoderado", (a, s) -> {
            System.out.println(" El teléfono del apoderado actual: " + a.getTelefonoApoderado());
            String nuevoTelfAPo=this.pedirTelefonoApoderado(s);
            System.out.print("Ingrese el nuevo teléfono del apoderado: "); a.setTelefonoApoderado(nuevoTelfAPo);
        }));

        // --- NOTAS Y ASISTENCIA ---
        opciones.add(new OpcionMenu("Actualizar Nota 1", (a, s) -> {
            System.out.println("La nota 1 actual: " + a.getNota1());  
            System.out.println("Ingrese la nueva Nota 1: ");
            double nuevaNota = pedirNota(s);
            a.setNota1(nuevaNota);

        }));
        opciones.add(new OpcionMenu("Actualizar Nota 2", (a, s) -> {
            System.out.println("La nota 2 actual: " + a.getNota2());
            System.out.println("Ingrese la nueva Nota 2: ");
            double nuevaNota = pedirNota(s);
            a.setNota2(nuevaNota);
        }));
        opciones.add(new OpcionMenu("Actualizar Nota 3", (a, s) -> {
            System.out.println("La nota actual: " + a.getNota3());
            System.out.println("Ingrese la nueva Nota 3: ");
            double nuevaNota = pedirNota(s);
            a.setNota3(nuevaNota);
        }));
        opciones.add(new OpcionMenu("Actualizar Nota 4", (a, s) -> {
            System.out.println(" La nota actual: " + a.getNota4());
            System.out.println("Ingrese la nueva Nota 4: ");
            double nuevaNota = pedirNota(s);
            a.setNota4(nuevaNota);
        }));
        opciones.add(new OpcionMenu("Actualizar Porcentaje Asistencia", (a, s) -> {
            System.out.println("El porcentaje de asistencia actual: " + a.getPorcentajeAsistencia() + "%");
            System.out.println("Ingrese el nuevo porcentaje de asistencia (0-100): "); 
            double p = pedirAsistencia(s);
            a.setPorcentajeAsistencia(p);

        }));
        opciones.add(new OpcionMenu("Actualizar Comportamiento", (a, s) -> {
            System.out.println("El comportamiento actual: " + a.getComportamiento());
            String nuevoComp= pedirComportamiento(s);
            System.out.print("Ingrese el nuevo comportamiento:  "); a.setComportamiento(nuevoComp);
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
