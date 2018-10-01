package ar.edu.unq.inscripcionunq.spring.validacion;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import ar.edu.unq.inscripcionunq.spring.exception.ApellidoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.CodigoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.DescripcionInvalidaException;
import ar.edu.unq.inscripcionunq.spring.exception.EmailInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.EstadoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.NombreInvalidoException;
import ar.edu.unq.inscripcionunq.spring.model.Carrera;
import ar.edu.unq.inscripcionunq.spring.model.Estudiante;
import ar.edu.unq.inscripcionunq.spring.model.OfertaAcademica;
import ar.edu.unq.inscripcionunq.spring.model.TypeStatus;

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

	public static void validarCarrera(Carrera carrera) throws DescripcionInvalidaException, CodigoInvalidoException, EstadoInvalidoException {
		descripcionValida(carrera.getDescripcion());
		codigoValido(carrera.getCodigo());
		estadoValido(carrera.getEstado());
	}

	private static void estadoValido(TypeStatus estado) throws EstadoInvalidoException {
		if(!TypeStatus.contains(estado.name())){
			throw new EstadoInvalidoException();
		}
		
	}

	private static void codigoValido(String codigo) throws CodigoInvalidoException {
		if(stringVacio(codigo)) {
			throw new CodigoInvalidoException();
		}
		
	}

	private static void descripcionValida(String descripcion) throws DescripcionInvalidaException {
		if (stringVacio(descripcion)) {
			throw new DescripcionInvalidaException();
		}
	
	}

	public static void validarOfertaAcademica(OfertaAcademica oferta) throws DescripcionInvalidaException, NombreInvalidoException, EstadoInvalidoException, CodigoInvalidoException {
		descripcionValida(oferta.getDescripcion());
		nombreValido(oferta.getNombre());
		estadoValido(oferta.getEstado());
		validarCarrera(oferta.getCarrera());
	}
}