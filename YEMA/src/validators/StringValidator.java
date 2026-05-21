package validators;

import java.util.function.Predicate;

import exceptions.ValidatorException;

public class StringValidator {
	
	public static StringValidator from(String value) {
		return new StringValidator(value);
	}
	
	private String value;
	
	public StringValidator(String value) {
		this.value = value;
	}
	
	public StringValidator sValidate(Predicate<String> predicate, String errorMsg) throws ValidatorException {
		
		if(!predicate.test(this.value)) {
			throw new ValidatorException(errorMsg);
		}
		
		return this;
	}
	
	//Validadores
	public StringValidator minLengthValidate(int min) throws ValidatorException{
		return sValidate(s -> s.length() >= min, "La longitud debe superar los " + min + " caracteres");
		
	}

	public StringValidator maxLengthValidate(int max) throws ValidatorException{
		return sValidate(s -> s.length() <= max, "La longitud no debe superar los " + max + " caracteres");
		
	}
	
	public StringValidator lengthValidate(int min, int max) throws ValidatorException{
		if(this.value.length() < min) {
			throw new ValidatorException("La longitud debe superar los " + min + " caracteres");
		}
		
		if(this.value.length() > max) {
			throw new ValidatorException("La longitud no debe superar los " + max + " caracteres");
		}
		return this;
		
	}

	public StringValidator notBlankValidate() throws ValidatorException{
		return sValidate( s -> s != null && !s.isBlank(), "Es string no puede estar vacio");
		
	}
}
