/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.programaescuela;

/**
 *
 * @author Alexis
 */

public class Alumno {

    private String dni;
    private String nombre;
    private String apellido;
    private char genero;
    private String seccion;
    private String nivel;
    private String grado;
    private String telefono;
    private String correo;
    private String direccion;
    private String nombreApoderado;
    private String apellidoApoderado;
    private char generoApoderado;
    private String parentescoApoderado;
    private String telefonoApoderado;
    private double porcentajeAsistencia;
    private String comportamiento;
    private double nota1, nota2, nota3, nota4;
    private double promedio;
    private boolean activo;

    public Alumno(
        String dni, 
        String nombre,
        String apellido,
        char genero,
        String seccion, 
        String nivel, 
        String grado, 
        String telefono,
        String correo,
        String direccion,
        String nombreApoderado, 
        String apellidoApoderado,
        char generoApoderado,
        String parentescoApoderado,
        String telefonoApoderado,
        double n1,
        double n2,
        double n3,
        double n4,
        String retirado,
        double porcentajeAsistencia,
        String comportamiento) {

        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.genero = genero;
        this.seccion = seccion;
        this.nivel = nivel;
        this.grado = grado;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.nombreApoderado = nombreApoderado;
        this.apellidoApoderado = apellidoApoderado;
        this.generoApoderado = generoApoderado;
        this.parentescoApoderado = parentescoApoderado;
        this.telefonoApoderado = telefonoApoderado;
        this.porcentajeAsistencia = porcentajeAsistencia;
        this.comportamiento = comportamiento;

        this.nota1 = n1;
        this.nota2 = n2;
        this.nota3 = n3;
        this.nota4 = n4;

        this.activo = (retirado == null || retirado.equalsIgnoreCase("No"));

        recalcularPromedio();
    }


    private void recalcularPromedio() {
        this.promedio = (nota1 + nota2 + nota3 + nota4) / 4.0;
    }

    public String getEstadoAcademico() {
        return (promedio >= 11.5) ? "APROBADO" : "DESAPROBADO";
    }

    public boolean estaActivo() {
        return activo;
    }

    public String getSituacion() {
        return activo ? "ACTIVO" : "RETIRADO";
    }

    public String getRetirado() {
        return activo ? "No" : "Sí";
    }

    public String getDni() { return dni; }

    public String getNombreCompleto() {
        return String.format("%s %s", nombre, apellido);
    }

    public String getGenero() {
        return String.valueOf(genero);
    }
    
    public String getSeccion() {
        return seccion;
    }
    
    public String getNivel() {
        return nivel;
    }
    
    public String getGrado() {
        return grado;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public String getCorreo() {
        return correo;
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public String getNombreApoderado() {
        return nombreApoderado;
    }
    
    public String getApellidoApoderado() {
        return apellidoApoderado;
    }
    
    public char getGeneroApoderado() {
        return generoApoderado;
    }
    
    public String getParentescoApoderado() {
        return parentescoApoderado;
    }
    
    public String getTelefonoApoderado() {
        return telefonoApoderado;
    }
    
    public double getPromedio() {
        return promedio;
    }
    
    public double getNota1() {
        return nota1;
    }

    public double getNota2() {
        return nota2;
    }
    public double getNota3() {
        return nota3;
    }
    public double getNota4() {
        return nota4;
    }
    
    public double getPorcentajeAsistencia() {
        return porcentajeAsistencia;
    }
    
    public String getComportamiento() {
        return comportamiento;
    }

    
    
    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }
    
    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
    
    public void setGrado(String grado) {
        this.grado = grado;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public void setNombreApoderado(String NombreApoderado) {
        this.nombreApoderado = NombreApoderado;
    }
    
    public void setApellidoApoderado(String apellidoApoderado) {
        this.apellidoApoderado = apellidoApoderado;
    }
    
    public void setGeneroApoderado(char generoApoderado) {
        this.generoApoderado = generoApoderado;
    }
    
    public void setParentescoApoderado(String parentescoApoderado) {
        this.parentescoApoderado = parentescoApoderado;
    }
    
    public void setTelefonoApoderado(String telefonoApoderado) {
        this.telefonoApoderado = telefonoApoderado;
    }   
    
    public void setNota1(double n1) {
        this.nota1 = n1;
        recalcularPromedio();
    }

    public void setNota2(double n2) {
         this.nota2 = n2;
        recalcularPromedio();
    }
    
    public void setNota3(double n3) {
        this.nota3 = n3;
        recalcularPromedio();
    }
    
    public void setNota4(double n4) {
        this.nota4 = n4;
        recalcularPromedio();
    }
    
    public void setPorcentajeAsistencia(double porcentajeAsistencia) {
        this.porcentajeAsistencia = porcentajeAsistencia;
    }
    
    public String setComportamiento(String comportamiento) {
        this.comportamiento = comportamiento;
        return this.comportamiento;
    }


    public void retirar() {
        this.activo = false;
    }

    public void reintegrar() {
        this.activo = true;
    }

    public boolean estaRetirado() {
        return !this.activo;
    }

    public String generarLineaCSV(String separador) {
        StringBuilder sb = new StringBuilder();
        
        sb.append(dni).append(separador);
        // Validamos nulos
        sb.append(nombre == null ? "" : nombre).append(separador);
        sb.append(apellido == null ? "" : apellido).append(separador);
        sb.append(telefono == null ? "" : telefono).append(separador);
        sb.append(correo == null ? "" : correo).append(separador);
        
        sb.append(String.format(java.util.Locale.US, "%.2f", nota1)).append(separador);
        sb.append(String.format(java.util.Locale.US, "%.2f", nota2)).append(separador);
        sb.append(String.format(java.util.Locale.US, "%.2f", nota3)).append(separador);
        sb.append(String.format(java.util.Locale.US, "%.2f", nota4)).append(separador);
        

        sb.append(String.format(java.util.Locale.US, "%.2f", promedio)).append(separador);
        sb.append(getEstadoAcademico()).append(separador);

        sb.append(getRetirado()); 
        
        return sb.toString();
    }

    @Override
    public String toString() {
        return String.format(java.util.Locale.US,
            "DNI: %-10s | Alumno: %-35s | Sección: %-5s | Nivel: %-10s |"
                    + " Grado: %-10s | Comportamiento: %-10s | Porcentaje Asistencia : %.2f |"
                    + " Promedio: %05.2f | Estado Académico: %-7s | Situación: %s",
            dni,
            getNombreCompleto(),
            seccion,
            nivel,
            grado,
            comportamiento,
            porcentajeAsistencia,
            promedio,
            getEstadoAcademico(),
            getSituacion()
        );
    }
}
