package ar.edu.unq.inscripcionunq.spring.exception;

public class CupoInvalidoException extends ExceptionGeneric implements ExceptionSystem {

	public CupoInvalidoException() {
		super(025, "Cupo Inválido");
	}

}
