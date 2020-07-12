package ar.edu.unq.inscripcionunq.spring.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity(name = "Encuesta")
public class Encuesta extends BaseEntity {
	
	@Column(unique = true)
	private String nombre;
	private LocalDateTime horaComienzo;
	private LocalDateTime horaFin;
	@ManyToMany(fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.EXTRA)
	private List<OfertaAcademica> ofertasAcademicas = new ArrayList<>();
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.EXTRA)
	private List<Estudiante> estudiantes = new ArrayList<>();
	@Enumerated(EnumType.STRING)
	private TipoEstado estado = TipoEstado.ENABLED;
	@ManyToOne(fetch = FetchType.LAZY)
	private Periodo periodo;
	private int limilteMaxMaterias; 
	private boolean solicitaPrerrequisitos; 

	public Encuesta() {
	
	}

	public Encuesta(String nombre, LocalDateTime horaComienzo, LocalDateTime horaFin, Periodo periodo, int limiteMaxMaterias, boolean solicitaPrerrequisito) {
		this.nombre = nombre;
		this.horaComienzo = horaComienzo;
		this.horaFin = horaFin;
		this.periodo = periodo;
		this.limilteMaxMaterias= limiteMaxMaterias;
		this.solicitaPrerrequisitos = solicitaPrerrequisito;
	}

	public void agregarOfertaAcademica(OfertaAcademica ofertaAcademica) {
		this.ofertasAcademicas.add(ofertaAcademica);
	}

	public void deshabilitar() {
		this.estado = TipoEstado.DISABLED;
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

	public void setOfertasAcademicas(List<OfertaAcademica> ofertasAcademicas) {
		this.ofertasAcademicas = ofertasAcademicas;
	}

	public TipoEstado getEstado() {
		return estado;
	}

	public List<OfertaAcademica> getOfertasAcademicas() {
		return ofertasAcademicas;
	}

	public List<Estudiante> getEstudiantes() {
		return estudiantes;
	}
	
	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}
	
	public Periodo getPeriodo() {
		return periodo;
	}

	public void actualizarDatos(Encuesta encuesta) {
		this.nombre = encuesta.getNombre();
		this.horaComienzo = encuesta.getHoraComienzo();
		this.horaFin = encuesta.getHoraFin();	
		this.limilteMaxMaterias = encuesta.getLimilteMaxMaterias();
		this.solicitaPrerrequisitos = encuesta.isSolicitaPrerrequisitos();
	}
	
	
	public int getLimilteMaxMaterias() {
		return limilteMaxMaterias;
	}

	public boolean isSolicitaPrerrequisitos() {
		return solicitaPrerrequisitos;
	}

}