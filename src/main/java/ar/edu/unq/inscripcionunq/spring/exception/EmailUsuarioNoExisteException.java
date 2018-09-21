package ar.edu.unq.inscripcionunq.spring.exception;

public class EmailUsuarioNoExisteException extends ExceptionGeneric implements ExceptionSystem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmailUsuarioNoExisteException() {
		super(001, "No se encuentra usuario con el email ingresado ");
		
	}

}
