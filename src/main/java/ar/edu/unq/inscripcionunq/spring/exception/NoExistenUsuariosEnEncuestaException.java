package ar.edu.unq.inscripcionunq.spring.exception;

public class NoExistenUsuariosEnEncuestaException extends ExceptionGeneric implements ExceptionSystem {

	private static final long serialVersionUID = 1L;

	public NoExistenUsuariosEnEncuestaException() {
		super(002, "No hay estudiantes asociados a la encuesta seleccionada");
	}
}
