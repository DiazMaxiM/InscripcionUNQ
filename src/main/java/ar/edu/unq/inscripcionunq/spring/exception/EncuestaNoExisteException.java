package ar.edu.unq.inscripcionunq.spring.exception;

public class EncuestaNoExisteException extends ExceptionGeneric implements ExceptionSystem  {

	private static final long serialVersionUID = 1L;

	public EncuestaNoExisteException() {
		super(001, "Carrera inexistente");
	}
}