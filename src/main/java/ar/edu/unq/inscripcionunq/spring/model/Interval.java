package ar.edu.unq.inscripcionunq.spring.model;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity(name = "Intervalo")
public class Interval extends BaseEntity {

	@Enumerated(EnumType.STRING)
	private TypeDay day;
	private LocalTime start;
	private Float countHours;

	public Interval(TypeDay day, LocalTime startHour, Float countHours) {
		this.day = day;
		this.setStart(startHour);
		this.setCountHours(countHours);
	}

	public Interval() {

	}

	public TypeDay getDay() {
		return day;
	}

	public LocalTime getStart() {
		return start;
	}

	private void setStart(LocalTime start) {
		this.start = start;
	}

	public Float getCountHours() {
		return countHours;
	}

	private void setCountHours(Float countHours) {
		this.countHours = countHours;
	}

}
