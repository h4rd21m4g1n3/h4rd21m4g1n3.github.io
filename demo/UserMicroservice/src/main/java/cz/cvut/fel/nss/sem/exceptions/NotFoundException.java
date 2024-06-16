package cz.cvut.fel.nss.sem.exceptions;

public class NotFoundException extends UserMicroException{
    public NotFoundException(String message) {
        super(message);
    }

    public static NotFoundException create(String resourceName, Object identifier) {
        return new NotFoundException(resourceName + " identified by " + identifier + " not found.");
    }
}
