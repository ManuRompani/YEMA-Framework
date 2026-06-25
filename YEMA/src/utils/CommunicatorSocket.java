package utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import interfaces.ICommunicator;

public class CommunicatorSocket implements ICommunicator{

	private Socket socket;
	private OutputStream os;
	private InputStream is;
	private BufferedReader br;
	
	
	
	
	
	public CommunicatorSocket(Socket socket) throws IOException {
		this.socket = socket;
		this.os = socket.getOutputStream();
		this.is = socket.getInputStream();
		this.br = new BufferedReader(new InputStreamReader(is));
	}

	@Override
	public void send(String mensaje) {		
		try {
			os.write(mensaje.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());			
		}
	}

	@Override
	public String receive() {
		String respuesta = "";
		try {
			String command = br.readLine();
			respuesta = command;
			return respuesta;
		}catch(IOException e) {
			throw new RuntimeException("Error al recibir el mensaje" + e);
		}
	}

}
