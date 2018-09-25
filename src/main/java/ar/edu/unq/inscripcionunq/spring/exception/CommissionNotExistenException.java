package ar.edu.unq.inscripcionunq.spring.exception;

public class CommissionNotExistenException extends ExceptionGeneric implements ExceptionSystem {

	private static final long serialVersionUID = 1L;

	public CommissionNotExistenException() {
		super(010, "Comisión inexistente");
	}

}
