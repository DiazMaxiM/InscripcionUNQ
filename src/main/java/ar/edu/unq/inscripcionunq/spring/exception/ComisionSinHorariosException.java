package ar.edu.unq.inscripcionunq.spring.exception;

public class ComisionSinHorariosException extends ExceptionGeneric implements ExceptionSystem {

	public ComisionSinHorariosException() {
		super(026, "La comisión debe tener al menos un horario");
		
	}

}
