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

@Entity(name = "Commission")
public class Commission extends BaseEntity {

	private String name;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Interval> intervals = new ArrayList<Interval>();
	private Integer quota;
	@ManyToOne(fetch = FetchType.LAZY)
	private Subject subject;
	@Enumerated(EnumType.STRING)
	private TypeStatus status = TypeStatus.ENABLED;

	public Commission(String name, Subject subject, Integer quota) {
		this.name = name;
		this.subject = subject;
		this.quota = quota;
	}

	public Subject getSubject() {
		return subject;
	}

	public void addHours(TypeDay day, LocalTime startHour, Float countHours) {
		List<Interval> intervalsResults = intervals.stream().filter(d -> d.getDay().equals(day))
				.collect(Collectors.toList());
		if (intervalsResults.isEmpty()) {
			intervals.add(new Interval(day, startHour, countHours));
		}
	}

	public Commission() {
	}

	public void disabled() {
		this.status = TypeStatus.DISABLED;
	}

	public List<Interval> getIntervals() {
		return intervals;
	}

	public String getName() {
		return name;
	}

	public TypeStatus getStatus() {
		return status;
	}

	public String getIntervalosString() {
		String textoIntervalos = "";
		for (Interval intervalo : intervals) {
			textoIntervalos = textoIntervalos.concat(intervalo.toString());
		}
		return textoIntervalos;
	}
}
