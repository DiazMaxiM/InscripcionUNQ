package ar.edu.unq.inscripcionunq.spring.exception;

public class ExisteEquivalenciaException extends ExceptionGeneric implements ExceptionSystem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExisteEquivalenciaException() {
		super(016, "Ya existe la equivalencia en la aplicación");
	}

}
