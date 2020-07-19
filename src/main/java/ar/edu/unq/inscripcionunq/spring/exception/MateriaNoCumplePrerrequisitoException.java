package ar.edu.unq.inscripcionunq.spring.exception;

import ar.edu.unq.inscripcionunq.spring.model.Materia;

public class MateriaNoCumplePrerrequisitoException extends ExceptionGeneric implements ExceptionSystem {

	private static final long serialVersionUID = 1L;

	public MateriaNoCumplePrerrequisitoException(Materia materia) {
		super(100, "La materia " + materia.getNombre() + " no cumple con los prerrequisitos");
	}
}