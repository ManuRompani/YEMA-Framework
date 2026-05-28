package exceptions;

public class ServiceNotImplementedException extends Exception {
	public ServiceNotImplementedException() {
        super("The service has not been implemented");
    }
}
