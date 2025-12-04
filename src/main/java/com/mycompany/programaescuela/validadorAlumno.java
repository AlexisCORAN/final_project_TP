/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.programaescuela;
import com.mycompany.programaescuela.model.AlumnoDTO;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Alexis
 */
public class validadorAlumno {

    private static final Pattern patronCorreo = Pattern.compile("^[A-Za-z0-9._%+-]+@gmail\\.com$", Pattern.CASE_INSENSITIVE);
    private static final Pattern patronTelefono = Pattern.compile("^9[0-9]{8}$");
    private static final Pattern patronNyA = Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúñÑ]+( [A-Za-zÁÉÍÓÚáéíóúñÑ]+){0,3}$");
    private static final Pattern patronNota = Pattern.compile("^(\\d{1,2})(\\.\\d{1,2})?$");
    private static final Pattern patronDNI = Pattern.compile("^\\d{8}$");
    private static final Pattern patronDireccion = Pattern.compile("^[A-Za-z0-9ÁÉÍÓÚáéíóúñÑ\\.\\-# ]{3,}$");

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

    public static boolean validarNyA(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) return true;
        return patronNyA.matcher(nombre.trim()).matches();
    }

    public static boolean validarDNI(String dni) {
        return patronDNI.matcher(dni).matches();
    }

    public static boolean validarDireccion(String direcc) {
        return patronDireccion.matcher(direcc).matches();
    }

    public static boolean validarAsistencia(String asistencia) {
        Matcher m = patronNota.matcher(asistencia);
        if (!m.matches()) return false;
        double valor = Double.parseDouble(asistencia);
        return valor >= 0 && valor <= 100;
    }
    
    public static String validar(AlumnoDTO dto) {

        if (!validarDNI(dto.dni))
            return "DNI inválido (deben ser 8 dígitos).";

        if (!validarNyA(dto.nombres))
            return "Nombres inválidos.";

        if (!validarNyA(dto.apellidos))
            return "Apellidos inválidos.";

        if (!validarTelefono(dto.telefono))
            return "Teléfono del alumno inválido.";

        if (!validarCorreo(dto.correo))
            return "Correo inválido (solo Gmail).";

        if (!validarDireccion(dto.direccion))
            return "Dirección inválida.";

        // APODERADO
        if (!validarNyA(dto.apoNombre))
            return "Nombre del apoderado inválido.";

        if (!validarNyA(dto.apoApellido))
            return "Apellido del apoderado inválido.";

        if (!validarTelefono(dto.apoTelefono))
            return "Teléfono del apoderado inválido.";

        // NOTAS
        if (dto.nota1 < 0 || dto.nota1 > 20) return "Nota 1 inválida.";
        if (dto.nota2 < 0 || dto.nota2 > 20) return "Nota 2 inválida.";
        if (dto.nota3 < 0 || dto.nota3 > 20) return "Nota 3 inválida.";
        if (dto.nota4 < 0 || dto.nota4 > 20) return "Nota 4 inválida.";

        // ASISTENCIA
        if (dto.asistencia < 0 || dto.asistencia > 100)
            return "Asistencia inválida (0 a 100).";

        return null;
    }
}
