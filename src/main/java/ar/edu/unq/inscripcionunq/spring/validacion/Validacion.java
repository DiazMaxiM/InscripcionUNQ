package ar.edu.unq.inscripcionunq.spring.validacion;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import ar.edu.unq.inscripcionunq.spring.exception.ApellidoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.EmailInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.NombreInvalidoException;
import ar.edu.unq.inscripcionunq.spring.model.Estudiante;

public class Validacion {
	
	  private Validacion() {
		    throw new IllegalStateException("Vakidacion class");
		  }
	
	public static boolean stringVacio(String string) {
		return StringUtils.isEmpty(string);
	}
	
	public static boolean esEmailValido(String email) {
		EmailValidator validator = EmailValidator.getInstance();
		return validator.isValid(email);
	}
	
	
	private static void nombreValido(String nombre) throws NombreInvalidoException {
		if(stringVacio(nombre)){
			throw new NombreInvalidoException();
		}
	}
	
	private static boolean apellidoValido(String apellido) throws ApellidoInvalidoException {
		if(stringVacio(apellido)){
			throw new ApellidoInvalidoException();
		}
		return true;
	}
	
	private static void emailValido(String email) throws EmailInvalidoException{
		if(!esEmailValido(email)){
			throw new EmailInvalidoException();
		}
	}

	public static void validarEstudiante(Estudiante estudiante) throws NombreInvalidoException, EmailInvalidoException, ApellidoInvalidoException {
		nombreValido(estudiante.getNombre());
		apellidoValido(estudiante.getApellido());
		emailValido(estudiante.getEmail());
	}
}