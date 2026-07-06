package utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import exceptions.CommunicatorException;
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

	        this.br = new BufferedReader(
	            new InputStreamReader(this.is, StandardCharsets.UTF_8)
	        );
	    }

	    @Override
	    public void send(String mensaje) {
	        try {
	            if (mensaje == null) {
	                mensaje = "";
	            }

	            mensaje = limpiarMensaje(mensaje);

	            os.write((mensaje + "\n").getBytes(StandardCharsets.UTF_8));
	            os.flush();

	        } catch (IOException e) {
	            throw new CommunicatorException(e.getMessage());
	        }
	    }

	    @Override
	    public String receive() {
	        try {
	            String command = br.readLine();

	            if (command == null) {
	                return null;
	            }

	            command = limpiarMensaje(command);

	            // Decodifica los caracteres enviados desde C#:
	            // %7C -> |
	            // %20 -> espacio
	            // %25 -> %
	            command = URLDecoder.decode(command, StandardCharsets.UTF_8);

	            return command;

	        } catch (IOException e) {
	            throw new CommunicatorException("Error al recibir el mensaje: " + e.getMessage());
	        }
	    }

	    private String limpiarMensaje(String mensaje) {
	        return mensaje
	                .replace("\uFEFF", "")
	                .replace("\u0000", "")
	                .trim();
	    }
}
