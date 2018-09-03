package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.model.Commission;

public interface CommissionDao extends GenericDao<Commission> {

	List<Commission> getCommissionForSubjectInPoll(Long idSubject, Long idPoll);

	List<Commission> getAllCommissionsSubjectInPoll(Long idPoll);

}
