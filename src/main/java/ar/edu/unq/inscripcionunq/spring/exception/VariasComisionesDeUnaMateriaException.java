package ar.edu.unq.inscripcionunq.spring.exception;

public class VariasComisionesDeUnaMateriaException extends ExceptionGeneric implements ExceptionSystem {

	private static final long serialVersionUID = 1L;

	public VariasComisionesDeUnaMateriaException() {
		super(004, "No puede inscribirse en varias comisiones de la misma materia");
	}

}
