package ar.edu.unq.inscripcionunq.spring.exception;

public class DniInvalidoException extends ExceptionGeneric implements ExceptionSystem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DniInvalidoException() {
		super(006, "Ingrese un dni válido");
	}

}
