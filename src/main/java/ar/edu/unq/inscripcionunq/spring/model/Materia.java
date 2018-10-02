package ar.edu.unq.inscripcionunq.spring.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import ar.edu.unq.inscripcionunq.spring.exception.ApellidoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.EmailInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.NombreInvalidoException;
import ar.edu.unq.inscripcionunq.spring.validacion.Validacion;

@Entity(name = "Materia")
public class Materia extends BaseEntity {
	@Column(unique = true)
	private String codigo;
	private String nombre;
	private Integer horas;
	@Enumerated(EnumType.STRING)
	private TypeStatus estado = TypeStatus.ENABLED;
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Carrera> carreras = new ArrayList<Carrera>();

	public Materia(String codigo, String nombre, Integer horas, List<Carrera> listaDeCarreras) {
		this.setCodigo(codigo);
		this.setNombre(nombre);
		this.setHoras(horas);
		this.carreras = listaDeCarreras;
	}

    public Materia(String codigo, String nombre, Integer horas, List<Carrera> listaDeCarreras, TypeStatus estado) {
		this.setCodigo(codigo);
		this.setNombre(nombre);
		this.setHoras(horas);
		this.carreras = listaDeCarreras;
        this.estado = estado; 
	}

	public Materia(String codigo, String nombre, Integer hours) {
		this.setCodigo(codigo);
		this.setNombre(nombre);
		this.setHoras(hours);
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

	public TypeStatus getEstado() {
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

	private void setEstado(TypeStatus estado) {
		this.estado = estado;
	}

	public void update(Materia materia) {
//			throws NombreInvalidoException, ApellidoInvalidoException, EmailInvalidoException {
//            Validacion.validarEstudiante(estudiante);
		this.codigo = materia.codigo;
		this.nombre = materia.nombre;
		this.horas = materia.horas;
		this.carreras = materia.carreras;
        this.estado = materia.estado;
	}
	public void deshabilitar() {
		setEstado(TypeStatus.DISABLED);
	}
}
