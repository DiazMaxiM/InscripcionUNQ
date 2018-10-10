package ar.edu.unq.inscripcionunq.spring.controller.miniobject;

import java.util.ArrayList;

import ar.edu.unq.inscripcionunq.spring.model.Equivalencia;

public class EquivalenciaJson {
	public Long id;
	public MateriaJson materiaOrigen;
	public MateriaJson materiaDestino;

	public EquivalenciaJson() {
	}

	public EquivalenciaJson(Equivalencia equivalencia) {
		this.id = equivalencia.getId();
		this.materiaOrigen = new MateriaJson(equivalencia.getMateriaOrigen(), false, new ArrayList<>());
		this.materiaDestino = new MateriaJson(equivalencia.getMateriaDestino(), false, new ArrayList<>());
	}
}
