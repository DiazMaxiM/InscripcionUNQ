package ar.edu.unq.inscripcionunq.spring.exception;

public class ExisteUsuarioConElMismoEmailException extends ExceptionGeneric implements ExceptionSystem {
	
	private static final long serialVersionUID = 1L;

	public ExisteUsuarioConElMismoEmailException() {
		super(025, "La dirección de e-mail que quiere ingresar ya está siendo utilizada por otro usuario");
	}
}