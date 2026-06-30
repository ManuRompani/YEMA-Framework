package utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import exceptions.CommunicatorException;
import interfaces.ICommunicator;

public class CommunicatorConsole implements ICommunicator{

	private OutputStream os;
	private InputStream is;	
	private BufferedReader br;
	
	
		public CommunicatorConsole(OutputStream os, InputStream is) {
		super();
		this.os = os;
		this.is = is;
		this.br = new BufferedReader(new InputStreamReader(is));
	}
		

	
		
	@Override
	public void send(String mensaje) {
		try {
			os.write(mensaje.getBytes());
		} catch (IOException e) {
			throw new CommunicatorException(e.getMessage());			
		}
		
	}

	@Override
	public String receive() {
				
			String respuesta = "";
			
				try {		
					String command = br.readLine();					
					respuesta = command;				
					 return respuesta;					
				} catch (IOException e) {
					throw new CommunicatorException(e.getLocalizedMessage());
					
				}
				
			
	}

}
