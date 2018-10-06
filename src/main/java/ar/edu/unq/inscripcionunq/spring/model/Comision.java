package ar.edu.unq.inscripcionunq.spring.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name = "Comision")
public class Comision extends BaseEntity {
	private String nombre;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Horario> horarios = new ArrayList<Horario>();
	private Integer cupo;
	public Integer getCupo() {
		return cupo;
	}

	public void setCupo(Integer cupo) {
		this.cupo = cupo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	private Materia materia;
	@Enumerated(EnumType.STRING)
	private TypeStatus estado = TypeStatus.ENABLED;

	public Comision(String nombre, Materia materia, Integer cupo) {
		this.nombre = nombre;
		this.materia = materia;
		this.cupo = cupo;
	}

	public Materia getMateria() {
		return materia;
	}

	public void agregarHorarios(TypeDay dia, LocalTime horaComienzo, Float cantidadDeHoras) {
		List<Horario> horariosR = horarios.stream().filter(d -> d.getDia().equals(dia))
				.collect(Collectors.toList());
		if (horariosR.isEmpty()) {
			horarios.add(new Horario(dia, horaComienzo, cantidadDeHoras));
		}
	}

	public Comision() {
	}

	public void dehabilitar() {
		this.estado = TypeStatus.DISABLED;
	}

	public List<Horario> getHorarios() {
		return horarios;
	}

	public String getNombre() {
		return nombre;
	}
	
	public TypeStatus getEstado() {
		return estado;
	}

	public String getHorariosString() {
		String textoHorarios = "";
		for (Horario horario : this.horarios) {
			textoHorarios = textoHorarios.concat(horario.toString());
		}
		return textoHorarios;
	}
}
