package ar.edu.unq.inscripcionunq.spring.exception;

public class EmailInvalidoException extends ExceptionGeneric implements ExceptionSystem {

	private static final long serialVersionUID = 1L;

	public EmailInvalidoException() {
		super(006, "Ingrese una dirección de e-mail válida");
	}
}