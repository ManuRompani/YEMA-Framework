package interfaces;

public interface ICommunicator {
	public abstract void send(String mensaje);

	public String receive();
	
}
