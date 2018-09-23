package ar.edu.unq.inscripcionunq.spring.exception;

public class IdNumberFormatException extends ExceptionGeneric implements ExceptionSystem {

	public IdNumberFormatException() {
		super(003, "Número de id inválido");
	}

	private static final long serialVersionUID = 1L;

}
