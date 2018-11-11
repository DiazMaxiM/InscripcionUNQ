package ar.edu.unq.inscripcionunq.spring.exception;

public class AnhoInvalidoException extends ExceptionGeneric implements ExceptionSystem {

	private static final long serialVersionUID = 1L;

	public AnhoInvalidoException() {
		super(022, "Año inválido");
	}
}