package ar.edu.unq.inscripcionunq.spring.controller.miniobject;

public class DatosMateriaWebServiceJson {
	public String nombre;
	public String codigo;
	public String siglas;

	public String getCodigoCerosAIzquierda() {
		return codigo.format("%05d", Integer.parseInt(codigo));
	}
}
