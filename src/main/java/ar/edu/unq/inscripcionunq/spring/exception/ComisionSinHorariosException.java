package ar.edu.unq.inscripcionunq.spring.exception;

public class ComisionSinHorariosException extends ExceptionGeneric implements ExceptionSystem {
	
	private static final long serialVersionUID = 1L;

	public ComisionSinHorariosException() {
		super(026, "La comisión debe tener, al menos, un horario");	
	}
}