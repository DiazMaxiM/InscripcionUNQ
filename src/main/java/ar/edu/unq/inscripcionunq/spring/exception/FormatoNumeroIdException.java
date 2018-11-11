package ar.edu.unq.inscripcionunq.spring.exception;

public class FormatoNumeroIdException extends ExceptionGeneric implements ExceptionSystem {

	private static final long serialVersionUID = 1L;

	public FormatoNumeroIdException() {
		super(003, "Número de id inválido");
	}
}
