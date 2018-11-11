package ar.edu.unq.inscripcionunq.spring.exception;

public class ObjectoNoEncontradoEnBDException extends ExceptionGeneric implements ExceptionSystem {

	private static final long serialVersionUID = 1L;

	public ObjectoNoEncontradoEnBDException() {
		super(0, "");
	}
}