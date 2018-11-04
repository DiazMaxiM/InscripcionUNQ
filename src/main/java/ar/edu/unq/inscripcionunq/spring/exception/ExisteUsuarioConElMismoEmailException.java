package ar.edu.unq.inscripcionunq.spring.exception;

public class ExisteUsuarioConElMismoEmailException extends ExceptionGeneric implements ExceptionSystem {

	public ExisteUsuarioConElMismoEmailException() {
		super(025, "El email que quiere usar ya está siendo utilizado por otro usuario");
	}

}
