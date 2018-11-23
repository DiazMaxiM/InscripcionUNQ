package ar.edu.unq.inscripcionunq.spring.exception;

public class EnvioMailException extends ExceptionGeneric implements ExceptionSystem {

	private static final long serialVersionUID = 1L;

	public EnvioMailException() {
		super(006, "No se pudo enviar el mail");
	}
}