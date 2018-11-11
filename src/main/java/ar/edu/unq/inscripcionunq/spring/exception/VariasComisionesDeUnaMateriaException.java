package ar.edu.unq.inscripcionunq.spring.exception;

public class VariasComisionesDeUnaMateriaException extends ExceptionGeneric implements ExceptionSystem {

	private static final long serialVersionUID = 1L;

	public VariasComisionesDeUnaMateriaException() {
		super(004, "No puede inscribirse a más de una comisión de una misma materia");
	}
}