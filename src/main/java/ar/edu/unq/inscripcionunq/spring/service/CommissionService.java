package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.CommissionJson;
import ar.edu.unq.inscripcionunq.spring.model.Commission;

public interface CommissionService extends GenericService<Commission> {

	List<CommissionJson> getCommissionForSubjectInPoll(String idSubject, String idPoll);

}
