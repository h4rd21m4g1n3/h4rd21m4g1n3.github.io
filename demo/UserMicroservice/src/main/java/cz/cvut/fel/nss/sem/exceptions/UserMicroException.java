package cz.cvut.fel.nss.sem.exceptions;

public class UserMicroException extends RuntimeException{
    public UserMicroException(String message) {
        super(message);
    }

    public UserMicroException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserMicroException(Throwable cause) {
        super(cause);
    }
}
