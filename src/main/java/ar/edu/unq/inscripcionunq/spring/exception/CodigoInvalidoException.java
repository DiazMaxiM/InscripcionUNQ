package ar.edu.unq.inscripcionunq.spring.exception;

public class CodigoInvalidoException extends ExceptionGeneric implements ExceptionSystem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CodigoInvalidoException() {
		super(012, "Ingresar un código válido");
	}

}
