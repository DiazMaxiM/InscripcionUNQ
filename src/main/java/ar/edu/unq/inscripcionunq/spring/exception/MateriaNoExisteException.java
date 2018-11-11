package ar.edu.unq.inscripcionunq.spring.exception;

public class MateriaNoExisteException  extends ExceptionGeneric implements ExceptionSystem  {

	private static final long serialVersionUID = 1L;

	public MateriaNoExisteException() {
		super(011, "Materia inexistente");
	}
}