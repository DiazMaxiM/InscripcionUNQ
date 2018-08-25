package ar.edu.unq.inscripcionunq.spring.model;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "Intervalo")
public class Interval {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	private TypeDay day;
	private LocalTime start;
	private Integer countHours;

	public Interval(TypeDay day, LocalTime startHour, Integer countHours) {
		this.day = day;
		this.start = startHour;
		this.countHours = countHours;
	}

	public Interval() {

	}

	public long getId() {
		return id;
	}

	public TypeDay getDay() {
		return day;
	}

}
