package ar.edu.unq.inscripcionunq.spring.exception;

public class NoSePudoGenerarCodigoException extends ExceptionGeneric implements ExceptionSystem {

	private static final long serialVersionUID = 1L;

	public NoSePudoGenerarCodigoException() {
		super(022, "No se pudo generar el código para el período. Por favor, vuelva a generarlo");
	}
}