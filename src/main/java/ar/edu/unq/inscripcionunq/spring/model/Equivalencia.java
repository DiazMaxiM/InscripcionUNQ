package ar.edu.unq.inscripcionunq.spring.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "Equivalencia")
public class Equivalencia extends BaseEntity {

	private static final long serialVersionUID = 1L;
	@ManyToOne
	private Materia materiaOrigen;
	@ManyToOne
	private Materia materiaDestino;

	public Equivalencia() {
	}

	public Equivalencia(Materia materiaOrigen, Materia materiaDestino) {
		this.materiaOrigen = materiaOrigen;
		this.materiaDestino = materiaDestino;
	}

	public Boolean isEquivalencia(Materia materia) {
		return materiaOrigen.equals(materia) || materiaDestino.equals(materia);
	}

	public Materia getEquivalencia(Materia materia) {
		return materia.equals(materiaOrigen) ? materiaDestino : materiaOrigen;
	}

	public Materia getMateriaOrigen() {
		return materiaOrigen;
	}

	public Materia getMateriaDestino() {
		return materiaDestino;
	}

	public void setMateriaOrigen(Materia materia) {
		this.materiaOrigen = materia;
	}

	public void setMateriaDestino(Materia materia) {
		this.materiaDestino = materia;
	}
}
