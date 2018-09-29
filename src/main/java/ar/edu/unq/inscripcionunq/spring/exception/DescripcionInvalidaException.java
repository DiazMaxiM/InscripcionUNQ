package ar.edu.unq.inscripcionunq.spring.exception;

public class DescripcionInvalidaException extends ExceptionGeneric implements ExceptionSystem{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DescripcionInvalidaException() {
		super(012, "Ingresar una descripción válida");
	}
	

}
