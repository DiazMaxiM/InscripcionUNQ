package ar.edu.unq.inscripcionunq.spring.exception;

public abstract class ExceptionGeneric extends Exception {

	private static final long serialVersionUID = 7766681825318222454L;
	private Integer code;
	private String msg;

	public ExceptionGeneric(Integer code, String msg) {
		super();
		this.setCode(code);
		this.setMsg(msg);
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public Integer getCode() {
		return code;
	}
}