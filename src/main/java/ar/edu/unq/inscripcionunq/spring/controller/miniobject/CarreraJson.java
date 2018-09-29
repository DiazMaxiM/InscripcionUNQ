package ar.edu.unq.inscripcionunq.spring.controller.miniobject;

public class CarreraJson {
	
	public  Long id;
	public String codigo;
	public String descripcion;
	public boolean habilitada;
	
	public CarreraJson(Long id, String codigo, String descripcion, boolean habilitada) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.habilitada = habilitada;
	}

	public CarreraJson() {
		super();
	}
   
	
	


}
