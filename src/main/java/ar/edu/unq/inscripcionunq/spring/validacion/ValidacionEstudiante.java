package ar.edu.unq.inscripcionunq.spring.validacion;

import ar.edu.unq.inscripcionunq.spring.exception.ApellidoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.EmailInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.NombreInvalidoException;
import ar.edu.unq.inscripcionunq.spring.model.Estudiante;

public class ValidacionEstudiante extends Validacion{
	
	public boolean estudianteValido(Estudiante estudiante) throws NombreInvalidoException, ApellidoInvalidoException, EmailInvalidoException {
		return nombreValido(estudiante.getNombre()) && apellidoValido(estudiante.getApellido()) &&
				emailValido(estudiante.getEmail());
	}
	
	private boolean nombreValido(String nombre) throws NombreInvalidoException {
		if(stringVacio(nombre)){
			throw new NombreInvalidoException();
		}
		return true;
	}
	
	private boolean apellidoValido(String apellido) throws ApellidoInvalidoException {
		if(stringVacio(apellido)){
			throw new ApellidoInvalidoException();
		}
		return true;
	}
	
	public boolean emailValido(String email) throws EmailInvalidoException{
		if(!esEmailValido(email)){
			throw new EmailInvalidoException();
		}
		return true;
	}
}