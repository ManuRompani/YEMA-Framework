package main;

import java.util.function.Predicate;

import exceptions.ValidatorException;

public class ValidatorString {
	
	public static ValidatorString from(String value) {
		return new ValidatorString(value);
	}
	
	private String value;
	
	public ValidatorString(String value) {
		this.value = value;
	}
	
	public ValidatorString validate(Predicate<String> predicate, String errorMsg) throws ValidatorException {
		
		if(!predicate.test(this.value)) {
			throw new ValidatorException(errorMsg);
		}
		
		return this;
	}
	
	//Validadores
	public ValidatorString minLengthValidate(int min) throws ValidatorException{
		return validate(v -> v.length() >= min, "La longitud debe superar los "+ min + " caracteres");
		
	}

	public ValidatorString maxLengthValidate(int max) throws ValidatorException{
		return validate(v -> v.length() <= max, "La longitud no debe superar los "+ max + " caracteres");
		
	}
	
	public ValidatorString lengthValidate(int min, int max) throws ValidatorException{
		if(this.value.length() < min) {
			throw new ValidatorException("La longitud debe superar los "+ min + " caracteres");
		}
		
		if(this.value.length() > max) {
			throw new ValidatorException("La longitud no debe superar los "+ max + " caracteres");
		}
		return this;
		
	}

	public ValidatorString notBlankValidate() throws ValidatorException{
		return validate( v -> v != null && !v.isBlank(), "Es string no puede estar vacio");
		
	}
}
