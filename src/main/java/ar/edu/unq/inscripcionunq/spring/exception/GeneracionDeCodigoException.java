package ar.edu.unq.inscripcionunq.spring.exception;

public class GeneracionDeCodigoException extends ExceptionGeneric implements ExceptionSystem {

	private static final long serialVersionUID = 1L;

	public GeneracionDeCodigoException() {
		super(022, "Los datos ingresados generan un código repetido. Por favor intente nuevamente");
	}
}