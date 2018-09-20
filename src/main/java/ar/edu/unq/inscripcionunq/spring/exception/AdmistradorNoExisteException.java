package ar.edu.unq.inscripcionunq.spring.exception;

public class AdmistradorNoExisteException extends ExceptionGeneric implements ExceptionSystem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AdmistradorNoExisteException() {
		super(001, "No existe usuario administrador en el sistema ");
		
	}

}
