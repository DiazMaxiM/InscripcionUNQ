package ar.edu.unq.inscripcionunq.spring.controller.miniobject;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.model.Materia;

public class MateriaSistemaJson {
	public Long id;
	public String codigo;
	public String nombre;
    public Integer horas;
	public boolean estado;
    public List<CarreraJson> carreras;

	public MateriaSistemaJson() {

	}

	public MateriaSistemaJson(Materia materia, boolean estado) {
		this.id = materia.getId();
        this.codigo = materia.getCodigo();
		this.nombre = materia.getNombre();
		this.horas = materia.getHoras();
//		this.carreras = materia.getCarreras();
        this.estado = estado;
	}

     public MateriaSistemaJson(Long id, String codigo, String nombre, Integer horas, List<CarreraJson> carreras, boolean estado) {
		this.id = id;
        this.codigo = codigo;
		this.nombre = nombre;
		this.horas = horas;
		this.carreras = carreras;
        this.estado = estado;
	}

	
}
