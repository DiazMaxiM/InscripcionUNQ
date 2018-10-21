package ar.edu.unq.inscripcionunq.spring.exception;

public class EncryptionDecryptionAESException extends ExceptionGeneric implements ExceptionSystem {

	public EncryptionDecryptionAESException() {
		super(026, "Ocurrió un error al codificar o decodificar la información");
	}

	private static final long serialVersionUID = 1L;

}
