package ar.edu.unq.inscripcionunq.spring.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity(name = "Encuesta")
public class Encuesta extends BaseEntity {
	private String nombre;
	private LocalDateTime horaComienzo;
	private LocalDateTime horaFin;
	@ManyToMany(fetch = FetchType.LAZY)
	private List<OfertaAcademica> ofertasAcademicas = new ArrayList<OfertaAcademica>();
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Estudiante> estudiantes = new ArrayList<Estudiante>();
	@Enumerated(EnumType.STRING)
	private TypeStatus estado = TypeStatus.ENABLED;

	public Encuesta() {
	}

	public Encuesta(String nombre, LocalDateTime horaComienzo, LocalDateTime horaFin) {
		this.nombre = nombre;
		this.horaComienzo = horaComienzo;
		this.horaFin = horaFin;
	}

	public void agregarOfertaAcademica(OfertaAcademica ofertaAcademica) {
		this.ofertasAcademicas.add(ofertaAcademica);
	}

	public void deshabilitar() {
		this.estado = TypeStatus.DISABLED;
	}

	public void agregarEstudiante(Estudiante estudiante) {
		estudiantes.add(estudiante);
		estudiante.setEncuesta(this);
	}

	public String getNombre() {
		return nombre;
	}

	public LocalDateTime getHoraComienzo() {
		return horaComienzo;
	}

	public LocalDateTime getHoraFin() {
		return horaFin;
	}
	
	public TypeStatus getEstado() {
		return estado;
	}
	
	public List<OfertaAcademica> getOfertasAcademicas() {
		return ofertasAcademicas;
	}
	
	public List<Estudiante> getEstudiantes(){
		return estudiantes;
	}

}
