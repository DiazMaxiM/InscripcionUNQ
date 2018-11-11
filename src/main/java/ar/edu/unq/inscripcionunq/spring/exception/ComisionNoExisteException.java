package ar.edu.unq.inscripcionunq.spring.exception;

public class ComisionNoExisteException extends ExceptionGeneric implements ExceptionSystem {

	private static final long serialVersionUID = 1L;

	public ComisionNoExisteException() {
		super(010, "Comisión inexistente");
	}
}