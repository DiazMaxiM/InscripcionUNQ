package ar.edu.unq.inscripcionunq.spring.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "OfertaAcademica")
public class OfertaAcademica extends BaseEntity {
	
	private String nombre;
	private String descripcion;
	@Enumerated(EnumType.STRING)
	private TypeStatus estado = TypeStatus.ENABLED;
	@ManyToMany(fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.EXTRA)
	private List<Comision> comisiones = new ArrayList<>();
	@ManyToOne(fetch = FetchType.LAZY)
	private Carrera carrera;
	@ManyToOne(fetch = FetchType.LAZY)
	private Periodo periodo;

	public OfertaAcademica(String nombre, String descripcion, Carrera carrera, Periodo periodo) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.carrera = carrera;
		this.periodo = periodo;
	}

	public OfertaAcademica() {
	}

	public void agregarComision(Comision comision) {
		this.comisiones.add(comision);
	}
	
	@JsonIgnore
	public List<Comision> getComisiones(){
		return comisiones;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public TypeStatus getEstado() {
		return estado;
	}

	public void setEstado(TypeStatus estado) {
		this.estado = estado;
	}

	public void setComisiones(List<Comision> comisiones) {
		this.comisiones = comisiones;
	}
    
	@JsonIgnore
	public Carrera getCarrera() {
		return carrera;
	}

	public void setCarrera(Carrera carrera) {
		this.carrera = carrera;
	}

	public OfertaAcademica clonar() {
		OfertaAcademica ofertaClonada = new OfertaAcademica();
		ofertaClonada.setNombre(nombre);
		ofertaClonada.setDescripcion(descripcion);
		ofertaClonada.setEstado(estado);
		ofertaClonada.setComisiones(comisiones);
		ofertaClonada.setCarrera(carrera);
		return ofertaClonada;
	}

	public void actualizarInformacion(OfertaAcademica ofertaRecibida) {
		setNombre(ofertaRecibida.getNombre());
		setDescripcion(ofertaRecibida.getDescripcion());
		setCarrera(ofertaRecibida.getCarrera());
		setEstado(ofertaRecibida.getEstado());
	}
	
	public void eliminarComision(Comision comision) {
		this.getComisiones().remove(comision);
	}
	
	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

}
