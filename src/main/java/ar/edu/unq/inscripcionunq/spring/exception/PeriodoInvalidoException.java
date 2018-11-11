package ar.edu.unq.inscripcionunq.spring.exception;

public class PeriodoInvalidoException extends ExceptionGeneric implements ExceptionSystem{

	private static final long serialVersionUID = 1L;

	public PeriodoInvalidoException() {
		super(020, "Tipo de período inválido");
	}
}