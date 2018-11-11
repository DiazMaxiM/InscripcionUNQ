package ar.edu.unq.inscripcionunq.spring.controller.miniobject;

import ar.edu.unq.inscripcionunq.spring.model.OfertaAcademica;
import ar.edu.unq.inscripcionunq.spring.model.TipoEstado;

public class OfertaAcademicaJson {
	
	public  Long id;
	public String nombre;
	public String descripcion;
	public boolean habilitada;
	public CarreraJson carrera;
	public Long nroComisionesCreadas;
	public PeriodoJson periodo;

	public OfertaAcademicaJson(OfertaAcademica oferta) {
		super();
		this.id = oferta.getId();
		this.nombre = oferta.getNombre();
		this.descripcion = oferta.getDescripcion();
		this.habilitada = TipoEstado.esEstadoHabiltado(oferta.getEstado());
		this.carrera = new CarreraJson(oferta.getCarrera());
		this.periodo = new PeriodoJson(oferta.getPeriodo());
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