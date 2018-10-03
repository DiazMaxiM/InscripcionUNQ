package ar.edu.unq.inscripcionunq.spring.exception;

public class HorarioInvalidoException extends ExceptionGeneric implements ExceptionSystem  {

	private static final long serialVersionUID = 1L;

	public HorarioInvalidoException() {
		super(017, "Ingrese un horario válido");
	}
}