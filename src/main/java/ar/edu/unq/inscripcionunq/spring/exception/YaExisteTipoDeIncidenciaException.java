package ar.edu.unq.inscripcionunq.spring.exception;

public class YaExisteTipoDeIncidenciaException extends ExceptionGeneric implements ExceptionSystem {

	public YaExisteTipoDeIncidenciaException() {
		super(004, "Ya existe el tipo de incidencia que quiere crear");
	}

}
