package ar.edu.unq.inscripcionunq.spring.exception;

public class NombreInvalidoException extends ExceptionGeneric implements ExceptionSystem {

	private static final long serialVersionUID = 1L;

	public NombreInvalidoException() {
		super(004, "Ingresar un nombre válido");
	}
}