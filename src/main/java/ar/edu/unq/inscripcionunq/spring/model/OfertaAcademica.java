package ar.edu.unq.inscripcionunq.spring.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ar.edu.unq.inscripcionunq.spring.exception.CodigoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.DescripcionInvalidaException;
import ar.edu.unq.inscripcionunq.spring.exception.GeneracionDeCodigoException;
import ar.edu.unq.inscripcionunq.spring.exception.EstadoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.NombreInvalidoException;
import ar.edu.unq.inscripcionunq.spring.validacion.Validacion;

@Entity(name = "OfertaAcademica")
public class OfertaAcademica extends BaseEntity {
	
	@Column(unique = true)
	private String nombre;
	private String descripcion;
	@Enumerated(EnumType.STRING)
	private TipoEstado estado = TipoEstado.ENABLED;
	@ManyToMany(fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.EXTRA)
	private List<Comision> comisiones = new ArrayList<>();
	@ManyToOne(fetch = FetchType.LAZY)
	private Carrera carrera;
	@ManyToOne(fetch = FetchType.LAZY)
	private Periodo periodo;

	public OfertaAcademica(String descripcion, Carrera carrera, Periodo periodo) {
		this.nombre = this.armarNombreDeOferta(carrera, periodo);
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
	public List<Comision> getComisiones() {
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

	public TipoEstado getEstado() {
		return estado;
	}

	public void setEstado(TipoEstado estado) {
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

	public void actualizarInformacion(OfertaAcademica ofertaRecibida) throws DescripcionInvalidaException,
			NombreInvalidoException, EstadoInvalidoException, CodigoInvalidoException, GeneracionDeCodigoException {
		Validacion.validarOfertaAcademica(ofertaRecibida);
		setNombre(this.armarNombreDeOferta(ofertaRecibida.getCarrera(), ofertaRecibida.getPeriodo()));
		setDescripcion(ofertaRecibida.getDescripcion());
		setCarrera(ofertaRecibida.getCarrera());
		setEstado(ofertaRecibida.getEstado());
		setPeriodo(ofertaRecibida.getPeriodo());
	}

	public String armarNombreDeOferta(Carrera carrera, Periodo periodo) {
		return "OA" + "-" + carrera.getCodigo() + "-" + periodo.getCodigo();
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