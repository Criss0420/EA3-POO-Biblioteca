package biblioteca;

/** Usuario estudiante: plazo normal y sin acceso a libros de referencia. */
public class Estudiante extends Usuario {

    private final String programa;

    // Sobrecarga de constructores
    public Estudiante(String nombre, String apellido, String numeroIdentificacion) {
        this(nombre, apellido, numeroIdentificacion, "Sin programa registrado");
    }

    public Estudiante(String nombre, String apellido, String numeroIdentificacion, String programa) {
        super(nombre, apellido, numeroIdentificacion);
        this.programa = programa;
    }

    @Override
    public String getTipoUsuario() {
        return "Estudiante";
    }

    @Override
    public int getDiasAdicionales() {
        return 0;
    }

    @Override
    public boolean puedeLlevarRestringidos() {
        return false;
    }

    @Override
    public String mostrarInformacion() {
        return super.mostrarInformacion() + " | " + programa;
    }

    public String getPrograma() { return programa; }
}
