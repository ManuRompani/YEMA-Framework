package validators;

import java.util.function.Predicate;

import exceptions.ValidatorException;

public class IntValidator {
	
	public static IntValidator from(int value) {
		return new IntValidator(value);
	}
	
	private int value;

	public IntValidator(int value) {
		this.value = value;
	}
	
	public IntValidator iValidate(Predicate<Integer> predicate, String errorMsg) throws ValidatorException{
		
		if(!predicate.test(this.value)) {
			throw new ValidatorException(errorMsg);
		}
		
		return this;
	}
	
	
	public IntValidator notNull() throws ValidatorException{
		return iValidate( d -> d != null, "El double no puede ser nulo");
	}
	
	public IntValidator minValue(int min) throws ValidatorException{
		return iValidate( d -> d >= min, "El valor debe ser mayor o igual a " + min);
	}
	
	public IntValidator maxValue(int max) throws ValidatorException{
		return iValidate( d -> d <= max, "El valor debe ser menor o igual a " + max);
	}
	
	public IntValidator between(int min, int max) throws ValidatorException{
		if(this.value < min) {
			throw new ValidatorException("El valor debe superar " + min);
		}
		
		if(this.value > max) {
			throw new ValidatorException("El valor no debe superar " + max);
		}
		return this;
	}
}
