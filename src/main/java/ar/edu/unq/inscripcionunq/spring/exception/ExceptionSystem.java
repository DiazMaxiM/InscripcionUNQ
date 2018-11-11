package ar.edu.unq.inscripcionunq.spring.exception;

public interface ExceptionSystem {

	public Integer getCode();

	public String getMsg();

	public void setCode(Integer code);

	public void setMsg(String msg);
}