package ar.edu.unq.inscripcionunq.spring.controller.miniobject;

import ar.edu.unq.inscripcionunq.spring.exception.ExceptionSystem;

public class ExceptionJson {
	public Integer code;
	public String msg;

	public ExceptionJson() {

	}

	public ExceptionJson(ExceptionSystem exception) {
		this.code = exception.getCode();
		this.msg = exception.getMsg();
	}

}
