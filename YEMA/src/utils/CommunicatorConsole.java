/* ACTUALIZACION BufferedReader:
					 * Dado que en la implementacion anterior el corte arrastraba como residuo \n osea "enter"
					 * cambio por esta clase que tiene como metodos read(), readLine() que nos sirven para 
					 * leer flujos de entrada y archivos.-
					 * Almacena en bufer.
					 * Por lo general el tamaño predeterminado que recibe alcanza para todo, pero se le puede especificar
					 * cuanto va a recibir.
					 * 
					 * readLine() : Corta la lectura automaticamente cuando haces \r <- osea enter.
					 * read() : recibe numeros, pero guarda en buffer \r y \n, entonces en ingresos
					 * de numeros hay que parsear en controlador para que se guarde como tal
					 * 
					 */

package utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

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
			throw new RuntimeException(e.getMessage());			
		}
		
	}

	@Override
	public String receive() {
				
			String respuesta = "";
			
				try {
					
					
					String command = br.readLine();
					
					respuesta = command;
					
					/*
					 * int b = is.read(); //Recibo por consola un numero que representa cada caracter	
					 * char c = (char) b; //Lo transformo en un caracter
					 *
					 * if(c == '\r') { //Si el caracter es igual a un salto de linea corto la lectura
					 *	return respuesta; //Devuelvo lo recolectado
					 * }
					 *
					 * respuesta += c;
					 */
					 return respuesta;
					
				} catch (IOException e) {
					throw new RuntimeException(e.getLocalizedMessage());
					
				}
				
			
	}

}
