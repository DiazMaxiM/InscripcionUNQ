package ar.edu.unq.inscripcionunq.spring.controller.miniobject;

public class OfertaAcademicaJson {
	
	public  Long id;
	public String nombre;
	public String descripcion;
	public boolean habilitada;
	public CarreraJson carrera;
	public Long nroComisionesCreadas;
    
	

	public OfertaAcademicaJson(Long id, String nombre, String descripcion, boolean habilitada, CarreraJson carreraJson) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.habilitada = habilitada;
		this.carrera = carreraJson;
	}

	public OfertaAcademicaJson() {
		super();
	}

	public Long getNroComisionesCreadas() {
		return nroComisionesCreadas;
	}

	public void setNroComisionesCreadas(Long nroComisionesCreadas) {
		this.nroComisionesCreadas = nroComisionesCreadas;
	}
	
	
   

}
