package biblioteca;

import java.util.Locale;

/**
 * Superclase abstracta de todos los libros de la biblioteca.
 * Contiene lo que comparten todos los tipos de libro (R1: título, autor, género y
 * disponibilidad) y declara los métodos que cada subclase debe resolver a su manera.
 */
public abstract class Libro {

    private static int contadorIds = 1;   // variable de clase: genera identificadores únicos

    private final int idLibro;
    private final String titulo;
    private final String autor;
    private final String genero;
    private boolean disponible;

    protected Libro(String titulo, String autor, String genero) {
        this.idLibro = contadorIds++;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.disponible = true;
    }

    // ---------- Métodos abstractos: cada subclase DEBE sobrescribirlos ----------

    /** Días que dura el préstamo según el tipo de libro. */
    public abstract int calcularDiasPrestamo();

    /** Valor de la multa por cada día de retraso (en pesos). */
    public abstract double getValorMultaDiaria();

    /** Nombre legible del tipo de libro. */
    public abstract String getTipo();

    // ---------- Métodos con comportamiento por defecto (se pueden sobrescribir) ----------

    public boolean estaDisponible() {
        return disponible;
    }

    void marcarPrestado() {            // visibilidad de paquete: solo Biblioteca lo usa
        disponible = false;
    }

    void marcarDisponible() {
        disponible = true;
    }

    /** Indica si el libro solo lo pueden llevar usuarios autorizados. */
    public boolean esRestringido() {
        return false;
    }

    public String mostrarInformacion() {
        return String.format(Locale.forLanguageTag("es-CO"), "[%d] %-18s \"%s\" - %s (%s) | %s | %d días, multa $%,.0f/día",
                idLibro, getTipo(), titulo, autor, genero,
                estaDisponible() ? "Disponible" : "Prestado",
                calcularDiasPrestamo(), getValorMultaDiaria());
    }

    @Override
    public String toString() {
        return mostrarInformacion();
    }

    // ---------- Getters (no hay setters: los datos del libro no cambian) ----------

    public int getIdLibro() { return idLibro; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public String getGenero() { return genero; }
}
