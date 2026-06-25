package validators;

import java.util.function.Predicate;

import exceptions.ValidatorException;

public class DoubleValidator {

	public static DoubleValidator from(double value) {
		return new DoubleValidator(value);
	}
	
	private double value;

	public DoubleValidator(double value) {
		this.value = value;
	}
	
	public DoubleValidator dValidate(Predicate<Double> predicate, String errorMsg) throws ValidatorException{
		
		if(!predicate.test(this.value)) {
			throw new ValidatorException(errorMsg);
		}
		
		return this;
	}
	
	
	public DoubleValidator notNull() throws ValidatorException{
		return dValidate( d -> d != null, "El double no puede ser nulo");
	}
	
	public DoubleValidator minValue(double min) throws ValidatorException{
		return dValidate( d -> d >= min, "El valor debe ser mayor o igual a " + min);
	}
	
	public DoubleValidator maxValue(double max) throws ValidatorException{
		return dValidate( d -> d <= max, "El valor debe ser menor o igual a " + max);
	}
	
	public DoubleValidator between(double min, double max) throws ValidatorException{
		if(this.value < min) {
			throw new ValidatorException("El valor debe superar " + min);
		}
		
		if(this.value > max) {
			throw new ValidatorException("El valor no debe superar " + max);
		}
		return this;
	}
}
