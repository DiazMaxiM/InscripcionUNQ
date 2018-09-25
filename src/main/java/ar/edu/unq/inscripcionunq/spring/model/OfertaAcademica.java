package ar.edu.unq.inscripcionunq.spring.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity(name = "OfertaAcademica")
public class OfertaAcademica extends BaseEntity {
	private String nombre;
	private String descripcion;
	@Enumerated(EnumType.STRING)
	private TypeStatus estado = TypeStatus.ENABLED;
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Comision> comisiones = new ArrayList<Comision>();
	@ManyToOne(fetch = FetchType.LAZY)
	private Carrera carrera;

	public OfertaAcademica(String nombre, String descripcion, Carrera carrera) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.carrera = carrera;
	}

	public OfertaAcademica() {
	}

	public void agregarComision(Comision comision) {
		this.comisiones.add(comision);
	}
	
	public List<Comision> getCommissions(){
		return comisiones;
	}

}
