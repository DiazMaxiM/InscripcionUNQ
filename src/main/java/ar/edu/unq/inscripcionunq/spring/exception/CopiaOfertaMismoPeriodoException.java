package ar.edu.unq.inscripcionunq.spring.exception;

public class CopiaOfertaMismoPeriodoException extends ExceptionGeneric implements ExceptionSystem {

	private static final long serialVersionUID = 1L;

	public CopiaOfertaMismoPeriodoException() {
		super(022, "No se puede clonar la oferta académica en el mismo período que la original");
	}
}