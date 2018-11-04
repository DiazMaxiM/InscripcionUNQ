package ar.edu.unq.inscripcionunq.spring.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity(name = "Horario")
public class Horario extends BaseEntity {
	@Enumerated(EnumType.STRING)
	private TypeDay dia;
	private LocalTime horaComienzo;
	private Float cantidadDeHoras;

	public Horario(TypeDay dia, LocalTime horaComienzo, Float cantidadDeHoras) {
		this.dia = dia;
		this.setHoraComienzo(horaComienzo);
		this.setCantidadDeHoras(cantidadDeHoras);
	}

	public Horario() {

	}

	public String toString() {
		return dia.toString() + " " + horaComienzo.format(DateTimeFormatter.ofPattern("HH:mm")).toString() + " a "
				+ horaComienzo.plusMinutes((long) (cantidadDeHoras * 60 - 1))
						.format(DateTimeFormatter.ofPattern("HH:mm")).toString()
				+ " ";
	}

	public TypeDay getDia() {
		return dia;
	}

	public LocalTime getHoraComienzo() {
		return horaComienzo;
	}

	private void setHoraComienzo(LocalTime horaComienzo) {
		this.horaComienzo = horaComienzo;
	}

	public Float getCantidadDeHoras() {
		return cantidadDeHoras;
	}

	private void setCantidadDeHoras(Float cantidadDeHoras) {
		this.cantidadDeHoras = cantidadDeHoras;
	}

	public Horario clonar() {
		return new Horario(this.dia, this.horaComienzo, this.cantidadDeHoras);
	}

}
