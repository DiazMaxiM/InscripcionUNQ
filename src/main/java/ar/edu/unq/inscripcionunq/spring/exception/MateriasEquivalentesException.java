package ar.edu.unq.inscripcionunq.spring.exception;

public class MateriasEquivalentesException extends ExceptionGeneric implements ExceptionSystem  {

	public MateriasEquivalentesException() {
		super(011, "No se puede crear una equivalencia sobre la misma materia");
	}

}
