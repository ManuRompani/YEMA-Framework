package exceptions;

public class ServiceNotImplementedException extends Exception {
	public ServiceNotImplementedException(String msg) {
        super("The service has not been implemented" + msg);
    }
}
