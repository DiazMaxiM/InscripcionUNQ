package ar.edu.unq.inscripcionunq.spring.exception;

public class EstadoInvalidoException extends ExceptionGeneric implements ExceptionSystem {

	private static final long serialVersionUID = 1L;

	public EstadoInvalidoException() {
		super(013, "Ingrese un estado válido");
	}
}