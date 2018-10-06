package ar.edu.unq.inscripcionunq.spring.model;

import javax.persistence.Entity;

@Entity(name = "TipoIncidencia")
public class TipoIncidencia extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String descripcion;
	private TypeStatus estado = TypeStatus.ENABLED;

	public TipoIncidencia() {

	}

	public TipoIncidencia(String descripcion) {
		this.descripcion = descripcion;
	}

	public void desactivar() {
		this.estado = TypeStatus.DISABLED;
	}

	public void activar() {
		this.estado = TypeStatus.ENABLED;
	}

	public String getDescripcion() {

		return this.descripcion;
	}

	public TypeStatus getEstado() {

		return estado;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setEstado(TypeStatus estado) {
		this.estado = estado;
	}
}
