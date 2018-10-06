
package ar.edu.unq.inscripcionunq.spring.exception;

public class NumeroInvalidoException extends ExceptionGeneric implements ExceptionSystem{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NumeroInvalidoException() {
		super(021, "Número inválido");
		
	}

}
