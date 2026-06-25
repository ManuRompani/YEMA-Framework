package services;


public interface Deserializer {
	
	//que T sea generico pero solo para el metodo
	//en el video de la clase 4/6 parte 1:33:00 menciona sto
	public <T> T deserealize(String t, Class<T> c);
}
