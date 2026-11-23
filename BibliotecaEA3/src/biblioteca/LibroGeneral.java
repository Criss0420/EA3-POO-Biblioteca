package biblioteca;

/** Libro de colección general (novelas, textos académicos, etc.). */
public class LibroGeneral extends Libro {

    private static final int DIAS_PRESTAMO = 15;
    private static final double MULTA_DIARIA = 1000;

    public LibroGeneral(String titulo, String autor, String genero) {
        super(titulo, autor, genero);
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
        return "Libro general";
    }
}
