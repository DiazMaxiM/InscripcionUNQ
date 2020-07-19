package ar.edu.unq.inscripcionunq.spring.exception;

public class CantidadMateriasInscripcionSuperadaException extends ExceptionGeneric implements ExceptionSystem {

	private static final long serialVersionUID = 1L;

	public CantidadMateriasInscripcionSuperadaException(int size, int limilteMaxMaterias) {
		super(101, "La materia a inscribirse (" + size + ") supera el máximo de materias (" + limilteMaxMaterias
				+ ") de materias para esta encuesta");
	}
}