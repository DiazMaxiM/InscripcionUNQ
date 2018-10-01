package ar.edu.unq.inscripcionunq.spring.exception;

public class OfertaNoExisteException extends ExceptionGeneric implements ExceptionSystem  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OfertaNoExisteException() {
		super(015, "Oferta Académica inexistente");
	}

}
