package ar.edu.unq.inscripcionunq.spring.exception;

public class PerfilInvalidoException extends ExceptionGeneric implements ExceptionSystem {
	
	private static final long serialVersionUID = 1L;

	public PerfilInvalidoException() {
		super(030, "Debe seleccionar un tipo de perfil");
	}
}
