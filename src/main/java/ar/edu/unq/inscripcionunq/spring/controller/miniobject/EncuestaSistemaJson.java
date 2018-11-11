package ar.edu.unq.inscripcionunq.spring.controller.miniobject;

import java.util.List;
import java.util.stream.Collectors;

import ar.edu.unq.inscripcionunq.spring.model.Encuesta;
import ar.edu.unq.inscripcionunq.spring.model.OfertaAcademica;

public class EncuestaSistemaJson {
	
	public String nombre;
	public FechaJson fechaComienzo;
    public FechaJson fechaFin;
	public PeriodoJson periodo;
	public List<OfertaAcademicaJson> ofertasAcademicas;
	public Long id;
	public int nroEstudiantes;
	public int nroOfertas;
	public Number nroDeAlumnosQueCompletaronEncuesta;

    public EncuestaSistemaJson(Encuesta encuesta) {
		this.id = encuesta.getId();
		this.nombre = encuesta.getNombre();
		this.fechaComienzo = new FechaJson(encuesta.getHoraComienzo());
		this.fechaFin = new FechaJson(encuesta.getHoraFin());
		this.periodo = new PeriodoJson(encuesta.getPeriodo());
		this.ofertasAcademicas = this.armarOfertasJson(encuesta.getOfertasAcademicas());
		this.nroEstudiantes = encuesta.getEstudiantes().size();
		this.nroOfertas = encuesta.getOfertasAcademicas().size();
	}
    
    private List<OfertaAcademicaJson> armarOfertasJson(List<OfertaAcademica> ofertas) {
		return ofertas.stream().map(o -> new OfertaAcademicaJson(o)).collect(Collectors.toList());
	}

	public EncuestaSistemaJson() {
    	super();
	}
}