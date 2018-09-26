package ar.edu.unq.inscripcionunq.spring.exception;

public class CertificadoException extends ExceptionGeneric implements ExceptionSystem {

	private static final long serialVersionUID = 1L;

	public CertificadoException() {
		super(011, "Error al generar el Certificado");
	}
}