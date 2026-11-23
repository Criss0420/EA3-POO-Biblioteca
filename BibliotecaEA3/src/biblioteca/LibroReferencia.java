package biblioteca;

/**
 * Obras de consulta (diccionarios, enciclopedias). Son muy solicitadas, por eso
 * el préstamo es corto, la multa es mayor y solo los docentes pueden llevarlas.
 */
public class LibroReferencia extends Libro {

    private static final int DIAS_PRESTAMO = 3;
    private static final double MULTA_DIARIA = 3000;

    private final String tipoObra;   // Diccionario, Enciclopedia, Atlas...

    public LibroReferencia(String titulo, String autor, String tipoObra) {
        super(titulo, autor, "Referencia");
        this.tipoObra = tipoObra;
    }

    @Override
    public int calcularDiasPrestamo() {
        return DIAS_PRESTAMO;
    }

    @Override
    public double getValorMultaDiaria() {
        return MULTA_DIARIA;
    }

    @Override
    public String getTipo() {
        return "Referencia";
    }

    @Override
    public boolean esRestringido() {
        return true;
    }

    @Override
    public String mostrarInformacion() {
        return super.mostrarInformacion() + " | " + tipoObra + ", solo docentes";
    }

    public String getTipoObra() { return tipoObra; }
}
