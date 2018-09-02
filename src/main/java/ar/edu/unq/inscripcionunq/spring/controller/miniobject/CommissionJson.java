package ar.edu.unq.inscripcionunq.spring.controller.miniobject;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unq.inscripcionunq.spring.model.Commission;
import ar.edu.unq.inscripcionunq.spring.model.Interval;

public class CommissionJson {

	public Long id;
	public List<IntervalJson> intervalJson = new ArrayList<IntervalJson>();

	public CommissionJson() {

	}

	public CommissionJson(Commission commission) {
		this.id = commission.getId();
		List<Interval> intervals = commission.getIntervals();

		for (Interval interval : intervals) {
			intervalJson.add(new IntervalJson(interval));
		}

	}

}
