package ar.edu.unq.inscripcionunq.spring.exception;

public class ExisteCarreraConElMismoCodigoException extends ExceptionGeneric implements ExceptionSystem  {

	private static final long serialVersionUID = 1L;

	public ExisteCarreraConElMismoCodigoException() {
		super(014, "Actualmente existe otra carrera con el mismo código que quiere utilizar");
	}
}