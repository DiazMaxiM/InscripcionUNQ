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
	private Integer countHours;

	public Interval(TypeDay day, LocalTime startHour, Integer countHours) {
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

	public Integer getCountHours() {
		return countHours;
	}

	private void setCountHours(Integer countHours) {
		this.countHours = countHours;
	}

}
