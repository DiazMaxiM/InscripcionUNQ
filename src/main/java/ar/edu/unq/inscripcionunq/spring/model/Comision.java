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
import javax.persistence.OrderBy;

@Entity(name = "Comision")
public class Comision extends BaseEntity {
	private String nombre;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderBy("dia ASC")
	private List<Horario> horarios = new ArrayList<>();
	private Integer cupo;
	@ManyToOne(fetch = FetchType.LAZY)
	private Materia materia;
	@Enumerated(EnumType.STRING)
	private TypeStatus estado = TypeStatus.ENABLED;
	@ManyToOne(fetch = FetchType.LAZY)
	private Periodo periodo;

	public Comision(String nombre, Materia materia, Integer cupo, Periodo periodo) {
		this.nombre = nombre;
		this.materia = materia;
		this.cupo = cupo;
		this.periodo = periodo;
	}

	public Materia getMateria() {
		return materia;
	}

	public void agregarHorarios(TypeDay dia, LocalTime horaComienzo, Float cantidadDeHoras) {
		List<Horario> horariosR = horarios.stream().filter(d -> d.getDia().equals(dia)).collect(Collectors.toList());
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

	public Integer getCupo() {
		return cupo;
	}

	public void setCupo(Integer cupo) {
		this.cupo = cupo;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public void modificarDatos(Comision comisionEditada) {
		this.nombre = comisionEditada.getNombre();
		this.horarios = comisionEditada.getHorarios();
		this.cupo = comisionEditada.getCupo();
		this.materia = comisionEditada.getMateria();
		this.periodo = comisionEditada.getPeriodo();

	}

	public Comision clonar() {
		Comision comisionClonada = new Comision();
		comisionClonada.nombre = this.nombre.concat(" COPIA");
		comisionClonada.cupo = this.cupo;

		List<Horario> horariosClonados = new ArrayList<Horario>();
		for (Horario horario : horarios) {
			horariosClonados.add(horario.clonar());
		}

		comisionClonada.horarios = horariosClonados;
		comisionClonada.materia = this.materia;
		comisionClonada.estado = this.estado;
		comisionClonada.periodo = this.periodo;
		return comisionClonada;
	}

	public Comision clonar(Periodo periodo) {
		Comision comision = this.clonar();
		comision.periodo = periodo;
		comision.nombre = this.nombre;
		return comision;
	}
}
