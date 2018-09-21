package ar.edu.unq.inscripcionunq.spring.exception;

public class PasswordInvalidoException extends ExceptionGeneric implements ExceptionSystem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PasswordInvalidoException() {
		super(002, "La contraseña es incorrecta. Vuelve a intentarlo");
	}
	

}
