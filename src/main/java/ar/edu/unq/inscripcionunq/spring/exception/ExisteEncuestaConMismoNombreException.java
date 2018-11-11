package ar.edu.unq.inscripcionunq.spring.exception;

public class ExisteEncuestaConMismoNombreException extends ExceptionGeneric implements ExceptionSystem  {

	private static final long serialVersionUID = 1L;

	public ExisteEncuestaConMismoNombreException() {
		super(016, "El nombre que quiere utilizar ya está siendo utilizado en otra encuesta");
	}
}