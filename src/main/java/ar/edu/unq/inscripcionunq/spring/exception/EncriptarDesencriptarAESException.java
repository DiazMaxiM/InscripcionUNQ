package ar.edu.unq.inscripcionunq.spring.exception;

public class EncriptarDesencriptarAESException extends ExceptionGeneric implements ExceptionSystem {

	private static final long serialVersionUID = 1L;

	public EncriptarDesencriptarAESException() {
		super(026, "Ocurrió un error al codificar o decodificar la información");
	}
}