package ar.edu.unq.inscripcionunq.spring.exception;

public class CupoInvalidoException extends ExceptionGeneric implements ExceptionSystem {

	private static final long serialVersionUID = 1L;

	public CupoInvalidoException() {
		super(025, "Cupo Inválido");
	}
}