package ar.edu.unq.inscripcionunq.spring.exception;

public class ConexionWebServiceException extends ExceptionGeneric implements ExceptionSystem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConexionWebServiceException() {
		super(022, "No se pudo generar el codigo para el período, Por favor vuelva a gererarlo");
	
	}

}