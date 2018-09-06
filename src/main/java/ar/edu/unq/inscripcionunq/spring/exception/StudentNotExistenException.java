package ar.edu.unq.inscripcionunq.spring.exception;

public class StudentNotExistenException extends ExceptionGeneric implements ExceptionSystem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StudentNotExistenException() {
		super(001, "No hay estudiantes asociados a la encuesta seleccionada");
	}

}
