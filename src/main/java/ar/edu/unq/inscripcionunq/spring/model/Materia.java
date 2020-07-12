package ar.edu.unq.inscripcionunq.spring.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import ar.edu.unq.inscripcionunq.spring.exception.CodigoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.DescripcionInvalidaException;
import ar.edu.unq.inscripcionunq.spring.exception.EstadoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.HorarioInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.NombreInvalidoException;
import ar.edu.unq.inscripcionunq.spring.validacion.Validacion;

@Entity(name = "Materia")
public class Materia extends BaseEntity {

	@Column(unique = true)
	private String codigo;
	private String nombre;
	private Integer horas;
	private Integer creditos;
	@Enumerated(EnumType.STRING)
	private TipoEstado estado = TipoEstado.ENABLED;
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	private List<Carrera> carreras = new ArrayList<>();
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	private List<Materia> prerrequisitos = new ArrayList<>();;

	public Materia(String codigo, String nombre, Integer horas, Integer creditos, List<Carrera> listaDeCarreras) {
		this.setCodigo(codigo);
		this.setNombre(nombre);
		this.setHoras(horas);
		this.carreras = listaDeCarreras;
		this.creditos = creditos;
	}

	public Materia(String codigo, String nombre, Integer horas, Integer creditos, List<Carrera> listaDeCarreras,
			TipoEstado estado) {
		this.setCodigo(codigo);
		this.setNombre(nombre);
		this.setHoras(horas);
		this.carreras = listaDeCarreras;
		this.estado = estado;
		this.creditos = creditos;
	}

	public Materia(String codigo, String nombre, Integer hours, Integer creditos) {
		this.setCodigo(codigo);
		this.setNombre(nombre);
		this.setHoras(hours);
		this.setCreditos(creditos);
	}

	public Materia() {

	}

	public void agregarCarrera(Carrera carrera) {
		carreras.add(carrera);
	}

	public List<Carrera> getCarreras() {
		return carreras;
	}

	public void eliminarCarrera(Carrera carrera) {
		carreras.remove(carrera);
	}

	public String getCodigo() {
		return codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public Integer getHoras() {
		return horas;
	}

	public TipoEstado getEstado() {
		return estado;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setHoras(Integer horas) {
		this.horas = horas;
	}

	private void setEstado(TipoEstado estado) {
		this.estado = estado;
	}

	public void actualizarMateria(Materia materia) throws CodigoInvalidoException, NombreInvalidoException,
			EstadoInvalidoException, DescripcionInvalidaException, HorarioInvalidoException {
		Validacion.validarMateria(materia);
		this.codigo = materia.codigo;
		this.nombre = materia.nombre;
		this.horas = materia.horas;
		this.carreras = materia.carreras;
		this.estado = materia.estado;
		this.creditos = materia.creditos;
	}

	public Integer getCreditos() {
		return creditos;
	}

	public void setCreditos(Integer creditos) {
		this.creditos = creditos;
	}

	public void deshabilitar() {
		setEstado(TipoEstado.DISABLED);
	}

	public void actualizarPrerrequisitos(List<Materia> prerrequisitos) {
		this.prerrequisitos = prerrequisitos;

	}

	public List<Materia> getPrerrequisitos() {
		return this.prerrequisitos;
	}

	public Boolean cumplePreRequisitos(List<Materia> materiasAprobadas) {
		return this.prerrequisitos.stream().filter(preRequisito -> !materiasAprobadas.contains(preRequisito))
				.collect(Collectors.toList()).isEmpty();
	}
}