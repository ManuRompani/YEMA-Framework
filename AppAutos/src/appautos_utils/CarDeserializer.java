package appautos_utils;
import java.lang.reflect.Field;
import services.Deserializer;

public class CarDeserializer implements Deserializer {

	public CarDeserializer() {
		
	}

	@Override
	public <T> T deserealize(String t, Class<T> c) {
		String[] values = t.split("\\|");
		Field[] atributes = c.getDeclaredFields();

	/*
	 * Es el equivalente a hacer new Car() pero de forma genérica. Como 
	 * no podés escribir new T() en Java porque T no existe en tiempo 
	 * de ejecución, usás getDeclaredConstructor().newInstance() 
	 * sobre el Class<T> para que Java
	 * construya el objeto por vos.
	*/
		try {
			T newObject = c.getDeclaredConstructor().newInstance();

			for (int i = 0; i < atributes.length; i++) {
				atributes[i].setAccessible(true);
				
				if(atributes[i].getType() == double.class) {
					Double value = Double.parseDouble(values[i]);
					atributes[i].set(newObject, value);
				}else {
					
					atributes[i].set(newObject, values[i]);
				}

			}

			return newObject;
			
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}

}
