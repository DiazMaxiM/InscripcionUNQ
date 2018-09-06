package ar.edu.unq.inscripcionunq.spring.exception;

public class UserInPollNotFoundException extends ExceptionGeneric implements ExceptionSystem {
	public UserInPollNotFoundException() {
		super(002, "No hay estudiantes asociados a la encuesta seleccionada");
	}
}
