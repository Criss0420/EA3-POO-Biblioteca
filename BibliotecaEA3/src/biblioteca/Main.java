package biblioteca;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

/**
 * Programa de prueba con ejemplos concretos del caso de estudio.
 * Se usan fechas fijas para que los resultados (plazos y multas) sean siempre iguales.
 */
public class Main {

    public static void main(String[] args) {
        // Salida en UTF-8 para que tildes y eñes se vean bien en cualquier consola
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8));

        Biblioteca biblioteca = new Biblioteca("Biblioteca IU Digital");

        // ---- Libros: la variable es de tipo Libro, el objeto es de una subclase ----
        Libro cienAnios  = new LibroGeneral("Cien años de soledad", "Gabriel García Márquez", "Novela");
        Libro voragine   = new LibroGeneral("La vorágine", "José Eustasio Rivera", "Novela");
        Libro cleanCode  = new LibroGeneral("Clean Code", "Robert C. Martin", "Tecnología");
        Libro principito = new LibroGeneral("El principito", "Antoine de Saint-Exupéry", "Infantil");
        Libro diccionario = new LibroReferencia("Diccionario de la lengua española", "Real Academia Española", "Diccionario");
        Libro javaDeitel = new LibroDigital("Java: cómo programar", "Paul Deitel", "Tecnología", "PDF", 2);
        Libro relato     = new LibroDigital("Relato de un náufrago", "Gabriel García Márquez", "Crónica", "EPUB", 1);

        for (Libro libro : List.of(cienAnios, voragine, cleanCode, principito, diccionario, javaDeitel, relato)) {
            biblioteca.agregarLibro(libro);
        }

        // ---- Usuarios: la variable es de tipo Usuario ----
        Usuario laura  = new Estudiante("Laura", "Gómez", "1001234567", "Ing. de Software y Datos");
        Usuario andres = new Estudiante("Andrés", "Restrepo", "1007654321");   // constructor sobrecargado
        Usuario marta  = new Docente("Marta", "Ospina", "43123456", "Facultad de Ingeniería");

        biblioteca.registrarUsuario(laura);
        biblioteca.registrarUsuario(andres);
        biblioteca.registrarUsuario(marta);

        titulo("1. CATÁLOGO (polimorfismo: la misma llamada mostrarInformacion() responde distinto)");
        for (Libro libro : biblioteca.getLibros()) {
            System.out.println(libro.mostrarInformacion());
        }

        titulo("2. USUARIOS REGISTRADOS");
        for (Usuario usuario : biblioteca.getUsuarios()) {
            System.out.println(usuario.mostrarInformacion());
        }

        LocalDate inicio = LocalDate.of(2026, 11, 3);

        titulo("3. PRÉSTAMOS DE LAURA Y LÍMITE DE 3 LIBROS (R3, R4)");
        intentarPrestamo(biblioteca, cienAnios, laura, inicio);
        intentarPrestamo(biblioteca, cleanCode, laura, inicio);
        intentarPrestamo(biblioteca, principito, laura, inicio);
        intentarPrestamo(biblioteca, voragine, laura, inicio);        // cuarto libro: se rechaza

        titulo("4. LIBRO DE REFERENCIA: ESTUDIANTE VS. DOCENTE (sobrescritura de puedeLlevarRestringidos)");
        intentarPrestamo(biblioteca, diccionario, andres, inicio);    // estudiante: rechazado
        intentarPrestamo(biblioteca, diccionario, marta, inicio);     // docente: 3 + 7 días
        intentarPrestamo(biblioteca, cienAnios, andres, inicio);      // ya está prestado

        titulo("5. LIBRO DIGITAL CON LICENCIAS (sobrescritura de estaDisponible y marcarPrestado)");
        intentarPrestamo(biblioteca, javaDeitel, andres, inicio);
        intentarPrestamo(biblioteca, javaDeitel, marta, inicio);
        System.out.println("   Estado: " + javaDeitel.mostrarInformacion());
        try {
            // Sobrecarga: préstamo por id del libro y cédula del usuario
            biblioteca.prestarLibro(javaDeitel.getIdLibro(), "1001234567");
        } catch (PrestamoException e) {
            System.out.println("   RECHAZADO (por id y cédula): " + e.getMessage());
        }

        titulo("6. BÚSQUEDAS (R6, con sobrecarga de buscarPorAutor)");
        System.out.println("Título contiene \"java\":");
        imprimir(biblioteca.buscarPorTitulo("java"));
        System.out.println("Autor \"García Márquez\" (todos):");
        imprimir(biblioteca.buscarPorAutor("García Márquez"));
        System.out.println("Autor \"García Márquez\" (solo disponibles):");
        imprimir(biblioteca.buscarPorAutor("García Márquez", true));

        titulo("7. DEVOLUCIONES CON RETRASO (multa calculada según el tipo de libro)");
        LocalDate devolucion = LocalDate.of(2026, 11, 22);
        intentarDevolucion(biblioteca, cienAnios, laura, devolucion);   // general: 15 días
        intentarDevolucion(biblioteca, diccionario, marta, devolucion); // referencia: 3+7 días
        intentarDevolucion(biblioteca, javaDeitel, andres, devolucion); // digital: sin multa
        intentarDevolucion(biblioteca, voragine, laura, devolucion);    // no estaba prestado

        titulo("8. AHORA LAURA PUEDE PEDIR OTRO LIBRO");
        intentarPrestamo(biblioteca, voragine, laura, devolucion);

        titulo("9. PRÉSTAMOS ACTIVOS (R7)");
        for (Prestamo prestamo : biblioteca.listarPrestamosActivos()) {
            System.out.println(prestamo.obtenerResumen());
        }

        titulo("10. HISTORIAL DE LAURA");
        for (Prestamo prestamo : biblioteca.consultarPrestamosDeUsuario(laura)) {
            System.out.println(prestamo.obtenerResumen());
        }
        System.out.println(laura.mostrarInformacion());
    }

    // ---------- Métodos auxiliares (variables locales en cada uno) ----------

    private static void intentarPrestamo(Biblioteca biblioteca, Libro libro, Usuario usuario, LocalDate fecha) {
        try {
            Prestamo prestamo = biblioteca.prestarLibro(libro, usuario, fecha);
            System.out.println("   OK: " + prestamo.obtenerResumen());
        } catch (PrestamoException e) {
            System.out.println("   RECHAZADO: " + e.getMessage());
        }
    }

    private static void intentarDevolucion(Biblioteca biblioteca, Libro libro, Usuario usuario, LocalDate fecha) {
        try {
            Prestamo prestamo = biblioteca.devolverLibro(libro, usuario, fecha);
            System.out.println("   OK: " + prestamo.obtenerResumen());
        } catch (PrestamoException e) {
            System.out.println("   ERROR: " + e.getMessage());
        }
    }

    private static void imprimir(List<Libro> libros) {
        for (Libro libro : libros) {
            System.out.println("   " + libro.getTitulo() + " - " + libro.getAutor()
                    + " (" + (libro.estaDisponible() ? "disponible" : "prestado") + ")");
        }
    }

    private static void titulo(String texto) {
        System.out.println();
        System.out.println("=== " + texto + " ===");
    }
}
