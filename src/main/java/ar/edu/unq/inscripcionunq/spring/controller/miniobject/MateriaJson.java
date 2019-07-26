package ar.edu.unq.inscripcionunq.spring.controller.miniobject;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unq.inscripcionunq.spring.model.Comision;
import ar.edu.unq.inscripcionunq.spring.model.Materia;

public class MateriaJson implements Comparable<MateriaJson> {

	public Long id;
	public String codigo;
	public String nombre;
	public Boolean aprobada;
	public List<ComisionJson> comisionesJson = new ArrayList<ComisionJson>();
	public ComisionJson comisionRegistrado;
	public Integer horas;
	public List<CarreraJson> carreras;

	public MateriaJson() {

	}

	public MateriaJson(Materia materia, List<CarreraJson> carreras, Boolean aprobada) {
		this.codigo = materia.getCodigo();
		this.nombre = materia.getNombre();
		this.aprobada = aprobada;
		this.id = materia.getId();
		this.horas = materia.getHoras();
		this.carreras = carreras;
	}

	public MateriaJson(Materia materia, boolean aprobada, List<Comision> collect) {
		this.codigo = materia.getCodigo();
		this.nombre = materia.getNombre();
		this.aprobada = aprobada;
		this.id = materia.getId();
		this.horas = materia.getHoras();
		if (!collect.isEmpty()) {
			this.setComisionInscripto(collect.get(0));
		}
	}

	public MateriaJson(Materia materia) {
		this.id = materia.getId();
	}

	private void setComisionInscripto(Comision comision) {
		this.comisionRegistrado = new ComisionJson(comision);
	}

	public MateriaJson agregarComisionJson(List<ComisionJson> comisionJson) {
		comisionesJson.addAll(comisionJson);

		return this;
	}

	@Override
	public int compareTo(MateriaJson materiaJson) {
		return this.nombre.compareTo(materiaJson.nombre);
	}
}