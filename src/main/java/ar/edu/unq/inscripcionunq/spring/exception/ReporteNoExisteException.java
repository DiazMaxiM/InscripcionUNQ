package ar.edu.unq.inscripcionunq.spring.exception;

public class ReporteNoExisteException extends ExceptionGeneric implements ExceptionSystem {
	
	private static final long serialVersionUID = 1L;
	
 	public ReporteNoExisteException() {
		super(031, "Debe seleccionar un tipo de reporte válido");
	}
}