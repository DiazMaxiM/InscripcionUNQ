package ar.edu.unq.inscripcionunq.spring.exception;

public class ExisteMateriaConElMismoCodigoException extends ExceptionGeneric implements ExceptionSystem  {

	private static final long serialVersionUID = 1L;

	public ExisteMateriaConElMismoCodigoException() {
		super(016, "El código que quiere ingresar ya está siendo utilizado por otra materia");
	}

}