package ar.edu.unq.inscripcionunq.spring.exception;

public class ErrorAlGenerarCodigoException extends ExceptionGeneric implements ExceptionSystem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ErrorAlGenerarCodigoException() {
		super(022, "Los datos ingresados generan un código repetido, Por favor intente nuevamente");
	}

}
