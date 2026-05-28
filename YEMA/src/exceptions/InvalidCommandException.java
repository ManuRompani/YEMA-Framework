package exceptions;

public class InvalidCommandException extends Exception {
    public InvalidCommandException() {
        super("Error while trying to get the command");
    }
}
