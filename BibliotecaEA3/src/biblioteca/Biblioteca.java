package biblioteca;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase coordinadora. Trabaja siempre con las superclases Libro y Usuario,
 * por eso acepta cualquier subclase sin cambiar su código (polimorfismo).
 */
public class Biblioteca {

    private final String nombre;
    private final List<Libro> libros = new ArrayList<>();
    private final List<Usuario> usuarios = new ArrayList<>();
    private final List<Prestamo> prestamos = new ArrayList<>();

    public Biblioteca(String nombre) {
        this.nombre = nombre;
    }

    // ---------- Registro ----------

    public void agregarLibro(Libro libro) {
        libros.add(libro);
    }

    public void registrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    // ---------- Búsquedas (R6) ----------

    public List<Libro> buscarPorTitulo(String titulo) {
        List<Libro> resultado = new ArrayList<>();
        for (Libro libro : libros) {
            if (libro.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                resultado.add(libro);
            }
        }
        return resultado;
    }

    public List<Libro> buscarPorAutor(String autor) {
        List<Libro> resultado = new ArrayList<>();
        for (Libro libro : libros) {
            if (libro.getAutor().toLowerCase().contains(autor.toLowerCase())) {
                resultado.add(libro);
            }
        }
        return resultado;
    }

    /** Sobrecarga: misma búsqueda por autor, pero puede filtrar solo los disponibles. */
    public List<Libro> buscarPorAutor(String autor, boolean soloDisponibles) {
        List<Libro> resultado = new ArrayList<>();
        for (Libro libro : buscarPorAutor(autor)) {
            if (!soloDisponibles || libro.estaDisponible()) {
                resultado.add(libro);
            }
        }
        return resultado;
    }

    public Libro buscarLibro(int idLibro) throws PrestamoException {
        for (Libro libro : libros) {
            if (libro.getIdLibro() == idLibro) {
                return libro;
            }
        }
        throw new PrestamoException("No existe un libro con id " + idLibro);
    }

    public Usuario buscarUsuario(String numeroIdentificacion) throws PrestamoException {
        for (Usuario usuario : usuarios) {
            if (usuario.getNumeroIdentificacion().equals(numeroIdentificacion)) {
                return usuario;
            }
        }
        throw new PrestamoException("No existe un usuario con identificación " + numeroIdentificacion);
    }

    // ---------- Préstamo (R3, R4) con sobrecarga ----------

    public Prestamo prestarLibro(Libro libro, Usuario usuario) throws PrestamoException {
        return prestarLibro(libro, usuario, LocalDate.now());
    }

    /** Sobrecarga: préstamo usando el id del libro y la cédula del usuario. */
    public Prestamo prestarLibro(int idLibro, String numeroIdentificacion) throws PrestamoException {
        return prestarLibro(buscarLibro(idLibro), buscarUsuario(numeroIdentificacion));
    }

    /** Sobrecarga principal: permite indicar la fecha del préstamo. */
    public Prestamo prestarLibro(Libro libro, Usuario usuario, LocalDate fecha) throws PrestamoException {
        if (!libros.contains(libro) || !usuarios.contains(usuario)) {
            throw new PrestamoException("El libro o el usuario no están registrados en la biblioteca");
        }
        if (!libro.estaDisponible()) {
            throw new PrestamoException("\"" + libro.getTitulo() + "\" no está disponible");
        }
        if (usuario.tieneLibro(libro)) {
            throw new PrestamoException(usuario.nombreCompleto() + " ya tiene \"" + libro.getTitulo() + "\"");
        }
        if (!usuario.puedePrestar()) {
            throw new PrestamoException(usuario.nombreCompleto() + " ya tiene "
                    + Usuario.MAX_PRESTAMOS + " libros prestados");
        }
        if (!usuario.puedePrestar(libro)) {
            throw new PrestamoException("Un usuario de tipo " + usuario.getTipoUsuario().toLowerCase()
                    + " no puede llevar libros de tipo " + libro.getTipo().toLowerCase());
        }
        libro.marcarPrestado();          // polimórfico: Libro físico o LibroDigital
        usuario.agregarLibro(libro);
        Prestamo prestamo = new Prestamo(libro, usuario, fecha);
        prestamos.add(prestamo);
        return prestamo;
    }

    // ---------- Devolución (R5) con sobrecarga ----------

    public Prestamo devolverLibro(Libro libro, Usuario usuario) throws PrestamoException {
        return devolverLibro(libro, usuario, LocalDate.now());
    }

    public Prestamo devolverLibro(Libro libro, Usuario usuario, LocalDate fecha) throws PrestamoException {
        for (Prestamo prestamo : prestamos) {
            if (prestamo.estaActivo() && prestamo.getLibro() == libro && prestamo.getUsuario() == usuario) {
                prestamo.registrarDevolucion(fecha);
                libro.marcarDisponible();
                usuario.quitarLibro(libro);
                return prestamo;
            }
        }
        throw new PrestamoException("\"" + libro.getTitulo() + "\" no tiene un préstamo activo con "
                + usuario.nombreCompleto());
    }

    // ---------- Consultas (R7) ----------

    public List<Prestamo> listarPrestamosActivos() {
        List<Prestamo> activos = new ArrayList<>();
        for (Prestamo prestamo : prestamos) {
            if (prestamo.estaActivo()) {
                activos.add(prestamo);
            }
        }
        return activos;
    }

    public List<Prestamo> consultarPrestamosDeUsuario(Usuario usuario) {
        List<Prestamo> resultado = new ArrayList<>();
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getUsuario() == usuario) {
                resultado.add(prestamo);
            }
        }
        return resultado;
    }

    public List<Libro> getLibros() { return new ArrayList<>(libros); }
    public List<Usuario> getUsuarios() { return new ArrayList<>(usuarios); }
    public String getNombre() { return nombre; }
}
