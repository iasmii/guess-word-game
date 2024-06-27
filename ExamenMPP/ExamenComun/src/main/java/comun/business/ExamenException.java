package comun.business;

public class ExamenException extends Exception{
    public ExamenException() {
    }

    public ExamenException(String message) {
        super(message);
    }

    public ExamenException(String message, Throwable cause) {
        super(message, cause);
    }
}
