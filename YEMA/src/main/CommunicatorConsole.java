package main;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import interfaces.ICommunicator;

public class CommunicatorConsole implements ICommunicator{

	private OutputStream os;
	private InputStream is;
	
		public CommunicatorConsole(OutputStream os, InputStream is) {
		super();
		this.os = os;
		this.is = is;
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
			while(true) {			
				
				try {
					int b = is.read(); //Recibo por consola un numero que representa cada caracter	
					char c = (char) b; //Lo transformo en un caracter
					
					if(c == '/') { //Si el caracter es igual a una / corto la lectura
						return respuesta; //Devuelvo lo recolectado
					}
					
					respuesta += c;
					
					
				} catch (IOException e) {
					throw new RuntimeException(e.getLocalizedMessage());
					
				}
				
			}
	}

}
