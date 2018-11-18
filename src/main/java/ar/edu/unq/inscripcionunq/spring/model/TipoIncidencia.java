package ar.edu.unq.inscripcionunq.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "TipoIncidencia")
public class TipoIncidencia extends BaseEntity {

	private static final long serialVersionUID = 1L;
	@Column(unique = true)
	private String descripcion;
	private TipoEstado estado = TipoEstado.ENABLED;

	public TipoIncidencia() {

	}

	public TipoIncidencia(String descripcion) {
		this.descripcion = descripcion;
	}

	public void desactivar() {
		this.estado = TipoEstado.DISABLED;
	}

	public void activar() {
		this.estado = TipoEstado.ENABLED;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public TipoEstado getEstado() {
		return estado;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setEstado(TipoEstado estado) {
		this.estado = estado;
	}
}