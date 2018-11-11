package ar.edu.unq.inscripcionunq.spring.exception;

public class ExisteComisionConMismoNombreParaElMismoPeriodoException extends ExceptionGeneric implements ExceptionSystem   {

	private static final long serialVersionUID = 1L;

	public ExisteComisionConMismoNombreParaElMismoPeriodoException() {
		super(014, "El nombre que quiere utilizar ya existe en otra comisión para este período");
	}
}