package exceptions;

public class InvalidCommandException extends Exception {
    public InvalidCommandException(String msg) {
        super("Error while trying to get the command" + msg);
    }
}
