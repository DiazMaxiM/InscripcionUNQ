package ar.edu.unq.inscripcionunq.spring.exception;

public class UsuarioNoExisteException extends ExceptionGeneric implements ExceptionSystem {

	private static final long serialVersionUID = 1L;

	public UsuarioNoExisteException() {
		super(001, "No existe usuario en la aplicación");	
	}
}