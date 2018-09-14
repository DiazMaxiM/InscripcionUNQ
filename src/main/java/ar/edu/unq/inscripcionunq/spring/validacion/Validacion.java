package ar.edu.unq.inscripcionunq.spring.validacion;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

public class Validacion {
	
	protected boolean stringVacio(String string) {
		return StringUtils.isEmpty(string);
	}
	
	protected boolean esEmailValido(String email) {
		EmailValidator validator = EmailValidator.getInstance();
		return validator.isValid(email);
	}
}