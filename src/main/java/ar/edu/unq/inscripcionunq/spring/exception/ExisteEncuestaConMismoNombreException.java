package ar.edu.unq.inscripcionunq.spring.exception;

public class ExisteEncuestaConMismoNombreException extends ExceptionGeneric implements ExceptionSystem  {

	public ExisteEncuestaConMismoNombreException() {
		super(016, "El nombre que quiere utilizar ya está siendo usado en otra encuesta");
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
