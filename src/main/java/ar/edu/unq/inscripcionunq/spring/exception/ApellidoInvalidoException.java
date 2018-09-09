package ar.edu.unq.inscripcionunq.spring.exception;

public class ApellidoInvalidoException extends ExceptionGeneric implements ExceptionSystem {

	private static final long serialVersionUID = 1L;

	public ApellidoInvalidoException() {
		super(005, "Ingresar un apellido válido");
	}

}
