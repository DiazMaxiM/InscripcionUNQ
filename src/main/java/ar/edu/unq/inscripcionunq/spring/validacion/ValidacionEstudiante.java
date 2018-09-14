package ar.edu.unq.inscripcionunq.spring.validacion;

import ar.edu.unq.inscripcionunq.spring.exception.ApellidoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.EmailInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.NombreInvalidoException;
import ar.edu.unq.inscripcionunq.spring.model.Student;

public class ValidacionEstudiante extends Validacion{
	
	public boolean estudianteValido(Student estudiante) throws NombreInvalidoException, ApellidoInvalidoException, EmailInvalidoException {
		return nombreValido(estudiante.getName()) && apellidoValido(estudiante.getLastName()) &&
				emailValido(estudiante.getMail());
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