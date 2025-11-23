# Sistema de Control de Notas Escolar

Este es una aplicación de consola desarrollada en Java para el curso de **Taller de Programación**. Permite la gestión automatizada de calificaciones de estudiantes, facilitando la carga, edición y exportación de datos compatibles con Excel.

## Características Principales

* **Carga Masiva:** Importación de datos desde archivos CSV.
* **Cálculo Automático:** Genera el promedio y determina el estado (Aprobado/Desaprobado) al instante.
* **Gestión Dinámica:** Uso de `ArrayList` para manejar una cantidad ilimitada de estudiantes.
* **CRUD de Estudiantes:**
    * Buscar por DNI.
    * Agregar nuevos alumnos manualmente.
    * Editar datos existentes (Correo, Teléfono, Notas).
* **Exportación Inteligente:** Genera reportes CSV compatibles con Excel en Español e Inglés (usa el truco `sep=;` y codificación `Cp1252`).

## Tecnologías Utilizadas

* **Lenguaje:** Java.
* **Entorno:** NetBeans IDE.
* **Gestor de Dependencias:** Maven.
* **Manejo de Archivos:** `BufferedReader`, `BufferedWriter`, `FileOutputStream`.
* **Conceptos:** POO, Encapsulamiento, Manejo de Excepciones, Colecciones (ArrayList).