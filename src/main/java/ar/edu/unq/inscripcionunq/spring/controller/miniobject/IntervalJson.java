package ar.edu.unq.inscripcionunq.spring.controller.miniobject;

import java.time.LocalTime;

import ar.edu.unq.inscripcionunq.spring.model.Interval;
import ar.edu.unq.inscripcionunq.spring.model.TypeDay;

public class IntervalJson {

	public TypeDay day;
	public LocalTime startDate;
	public LocalTime endDate;

	public IntervalJson(Interval interval) {
		this.day = interval.getDay();
		startDate = interval.getStart();
		endDate = startDate.plusMinutes((long) (interval.getCountHours() * 60));
	}

	public IntervalJson() {

	}
}
