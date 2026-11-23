package biblioteca;

/**
 * Excepción propia del sistema. Hereda de Exception (herencia de una clase de Java)
 * y se lanza cuando una regla de negocio impide prestar o devolver un libro.
 */
public class PrestamoException extends Exception {

    private static final long serialVersionUID = 1L;

    public PrestamoException(String mensaje) {
        super(mensaje);
    }
}
