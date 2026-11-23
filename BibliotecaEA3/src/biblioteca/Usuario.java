package biblioteca;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Superclase abstracta de los usuarios. Guarda los datos del requisito R2 y la regla
 * de máximo tres libros (R3). Las subclases definen qué beneficios tiene cada tipo.
 */
public abstract class Usuario {

    public static final int MAX_PRESTAMOS = 3;

    private final String nombre;
    private final String apellido;
    private final String numeroIdentificacion;
    private final List<Libro> librosPrestados = new ArrayList<>();

    protected Usuario(String nombre, String apellido, String numeroIdentificacion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroIdentificacion = numeroIdentificacion;
    }

    // ---------- Métodos abstractos ----------

    public abstract String getTipoUsuario();

    /** Días extra que se suman al plazo del préstamo. */
    public abstract int getDiasAdicionales();

    /** Si el usuario puede llevarse libros restringidos (de referencia). */
    public abstract boolean puedeLlevarRestringidos();

    // ---------- Sobrecarga de puedePrestar ----------

    /** R3: verifica solo el límite de tres libros. */
    public boolean puedePrestar() {
        return cantidadPrestados() < MAX_PRESTAMOS;
    }

    /** Sobrecarga: además del límite, revisa si el usuario puede llevar ese libro en particular. */
    public boolean puedePrestar(Libro libro) {
        return puedePrestar() && (!libro.esRestringido() || puedeLlevarRestringidos());
    }

    // ---------- Manejo de la lista (solo desde el paquete, es decir, desde Biblioteca) ----------

    void agregarLibro(Libro libro) {
        librosPrestados.add(libro);
    }

    void quitarLibro(Libro libro) {
        librosPrestados.remove(libro);
    }

    public boolean tieneLibro(Libro libro) {
        return librosPrestados.contains(libro);
    }

    public int cantidadPrestados() {
        return librosPrestados.size();
    }

    public String nombreCompleto() {
        return nombre + " " + apellido;
    }

    public String mostrarInformacion() {
        return String.format("%-10s %-20s CC %-11s | %d/%d libros | +%d días",
                getTipoUsuario(), nombreCompleto(), numeroIdentificacion,
                cantidadPrestados(), MAX_PRESTAMOS, getDiasAdicionales());
    }

    @Override
    public String toString() {
        return mostrarInformacion();
    }

    public String getNumeroIdentificacion() { return numeroIdentificacion; }

    /** Se entrega una copia de solo lectura para que nadie modifique la lista desde afuera. */
    public List<Libro> getLibrosPrestados() {
        return Collections.unmodifiableList(librosPrestados);
    }
}
