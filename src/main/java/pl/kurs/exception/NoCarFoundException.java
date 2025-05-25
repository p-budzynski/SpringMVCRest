package pl.kurs.exception;

public class NoCarFoundException extends RuntimeException {
    public NoCarFoundException(String message) {
        super(message);
    }
}
