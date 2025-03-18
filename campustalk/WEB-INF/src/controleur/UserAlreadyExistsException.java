package controleur;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException() {
        super("L'utilisateur existe déjà.");
    }
}
