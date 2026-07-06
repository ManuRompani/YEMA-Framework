package utils;

import java.util.HashMap;

import dtos.Command;
import exceptions.InvalidCommandException;

public class CommandParser{
	//  recurso/accion/clave1=valor1&clave2=valor2 puede venir vacio recurso/accion
	
	private String[] segments = null;
	
	public Command Parse(String parseString) throws InvalidCommandException {
		try {
			this.segments = parseString.split("/");

			return new Command(segments[0], segments[1], getParameters());
		}
		catch(Exception e){
			throw new InvalidCommandException("Comando " + parseString + " invalido");
		}
	}
	
	//Si trae parametros (this.segments.length > 3) los guarda en el diccionario y luego lo retorna
	private HashMap<String, String> getParameters(){
		HashMap<String, String> hParameters = new HashMap<String,String>(); 
		
		if(this.segments.length > 2) { 
			String[] parameters = this.segments[2].split("&");
			
			for (String item : parameters){
				String[] param = item.split("=");
				
				hParameters.put(param[0].toString(), param[1].toString());
				
			}
		}
		
		return hParameters;
	}
}
