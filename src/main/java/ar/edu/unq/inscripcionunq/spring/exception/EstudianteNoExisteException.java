package ar.edu.unq.inscripcionunq.spring.exception;

public class EstudianteNoExisteException extends ExceptionGeneric implements ExceptionSystem {

	private static final long serialVersionUID = 1L;

	public EstudianteNoExisteException() {
		super(001, "No hay estudiantes asociados a la encuesta seleccionada");
	}
}