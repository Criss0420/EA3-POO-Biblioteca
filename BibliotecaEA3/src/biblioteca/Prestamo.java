package biblioteca;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

/**
 * Registro de un préstamo (R7). El plazo y la multa se calculan con llamadas
 * polimórficas: el préstamo no sabe qué tipo de libro o de usuario tiene,
 * simplemente les pregunta y cada objeto responde según su clase.
 */
public class Prestamo {

    private static int contadorIds = 1;

    private final int idPrestamo;
    private final Libro libro;
    private final Usuario usuario;
    private final LocalDate fechaPrestamo;
    private final LocalDate fechaLimite;
    private LocalDate fechaDevolucion;
    private boolean activo;
    private double multa;

    Prestamo(Libro libro, Usuario usuario, LocalDate fechaPrestamo) {
        this.idPrestamo = contadorIds++;
        this.libro = libro;
        this.usuario = usuario;
        this.fechaPrestamo = fechaPrestamo;
        // Polimorfismo: días según el tipo de libro + días extra según el tipo de usuario
        this.fechaLimite = fechaPrestamo.plusDays(libro.calcularDiasPrestamo() + usuario.getDiasAdicionales());
        this.activo = true;
    }

    void registrarDevolucion(LocalDate fecha) {
        this.fechaDevolucion = fecha;
        this.multa = calcularMulta(fecha);
        this.activo = false;
    }

    public long diasRetraso(LocalDate fecha) {
        return Math.max(0, ChronoUnit.DAYS.between(fechaLimite, fecha));
    }

    // ---------- Sobrecarga de calcularMulta ----------

    /** Multa a hoy si sigue activo, o la multa definitiva si ya se devolvió. */
    public double calcularMulta() {
        return activo ? calcularMulta(LocalDate.now()) : multa;
    }

    /** Multa a una fecha dada: días de retraso x valor diario del tipo de libro. */
    public double calcularMulta(LocalDate fecha) {
        return diasRetraso(fecha) * libro.getValorMultaDiaria();
    }

    public boolean estaActivo() {
        return activo;
    }

    public String obtenerResumen() {
        String estado = activo
                ? "ACTIVO, vence " + fechaLimite
                : "DEVUELTO " + fechaDevolucion + ", retraso " + diasRetraso(fechaDevolucion)
                        + " días, multa $" + String.format(Locale.forLanguageTag("es-CO"), "%,.0f", multa);
        return String.format("Préstamo #%d: \"%s\" -> %s (%s) | %s",
                idPrestamo, libro.getTitulo(), usuario.nombreCompleto(), fechaPrestamo, estado);
    }

    @Override
    public String toString() {
        return obtenerResumen();
    }

    public int getIdPrestamo() { return idPrestamo; }
    public Libro getLibro() { return libro; }
    public Usuario getUsuario() { return usuario; }
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public LocalDate getFechaLimite() { return fechaLimite; }
    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
}
