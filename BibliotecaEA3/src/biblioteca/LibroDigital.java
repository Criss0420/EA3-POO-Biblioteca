package biblioteca;

/**
 * Libro electrónico. No tiene un único ejemplar físico sino un número de licencias,
 * por eso sobrescribe la forma de controlar la disponibilidad. No genera multa porque
 * el acceso se cierra automáticamente al vencer el plazo.
 */
public class LibroDigital extends Libro {

    private static final int DIAS_PRESTAMO = 7;

    private final String formato;          // PDF, EPUB
    private final int licenciasTotales;
    private int licenciasEnUso;

    public LibroDigital(String titulo, String autor, String genero, String formato, int licenciasTotales) {
        super(titulo, autor, genero);
        this.formato = formato;
        this.licenciasTotales = licenciasTotales;
        this.licenciasEnUso = 0;
    }

    @Override
    public boolean estaDisponible() {
        return licenciasEnUso < licenciasTotales;
    }

    @Override
    void marcarPrestado() {
        if (!estaDisponible()) {
            throw new IllegalStateException("No quedan licencias libres");
        }
        licenciasEnUso++;
    }

    @Override
    void marcarDisponible() {
        if (licenciasEnUso > 0) {
            licenciasEnUso--;
        }
    }

    @Override
    public int calcularDiasPrestamo() {
        return DIAS_PRESTAMO;
    }

    @Override
    public double getValorMultaDiaria() {
        return 0;
    }

    @Override
    public String getTipo() {
        return "Digital (" + formato + ")";
    }

    @Override
    public String mostrarInformacion() {
        return super.mostrarInformacion()
                + " | licencias en uso " + licenciasEnUso + "/" + licenciasTotales;
    }

    public int getLicenciasDisponibles() { return licenciasTotales - licenciasEnUso; }
}
