package ar.edu.unq.inscripcionunq.spring.controller.miniobject;

import ar.edu.unq.inscripcionunq.spring.model.Carrera;
import ar.edu.unq.inscripcionunq.spring.model.TypeStatus;

public class CarreraJson {
	
	public  Long id;
	public String codigo;
	public String descripcion;
	public boolean habilitada;
	
	public CarreraJson(Carrera carrera) {
		super();
		this.id = carrera.getId();
		this.codigo = carrera.getCodigo();
		this.descripcion = carrera.getDescripcion();
		this.habilitada = TypeStatus.esEstadoHabiltado(carrera.getEstado());
	}

	public CarreraJson() {
		super();
	}
   
	
	


}
