package biblioteca;

/** Usuario docente: 7 días adicionales y puede llevar libros de referencia. */
public class Docente extends Usuario {

    private static final int DIAS_ADICIONALES = 7;
    private final String dependencia;

    public Docente(String nombre, String apellido, String numeroIdentificacion, String dependencia) {
        super(nombre, apellido, numeroIdentificacion);
        this.dependencia = dependencia;
    }

    @Override
    public String getTipoUsuario() {
        return "Docente";
    }

    @Override
    public int getDiasAdicionales() {
        return DIAS_ADICIONALES;
    }

    @Override
    public boolean puedeLlevarRestringidos() {
        return true;
    }

    @Override
    public String mostrarInformacion() {
        return super.mostrarInformacion() + " | " + dependencia;
    }

    public String getDependencia() { return dependencia; }
}
