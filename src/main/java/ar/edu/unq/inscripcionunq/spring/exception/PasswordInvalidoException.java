package ar.edu.unq.inscripcionunq.spring.exception;

public class PasswordInvalidoException extends ExceptionGeneric implements ExceptionSystem {

	public PasswordInvalidoException() {
		super(002, "Ingresar contraseña válida");
	}
	

}
