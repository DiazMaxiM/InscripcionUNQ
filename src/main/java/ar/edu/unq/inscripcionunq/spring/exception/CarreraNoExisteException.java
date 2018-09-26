package ar.edu.unq.inscripcionunq.spring.exception;

public class CarreraNoExisteException extends ExceptionGeneric implements ExceptionSystem  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CarreraNoExisteException() {
		super(001, "Carrera inexistente");
	}


}
