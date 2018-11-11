package ar.edu.unq.inscripcionunq.spring.exception;

public class ConexionWebServiceException extends ExceptionGeneric implements ExceptionSystem {

	private static final long serialVersionUID = 1L;

	public ConexionWebServiceException() {
		super(022, "No se pudieron cargar los estudiantes para la encuesta");	
	}
}