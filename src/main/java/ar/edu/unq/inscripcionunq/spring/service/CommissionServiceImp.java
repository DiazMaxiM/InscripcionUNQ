package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.CommissionJson;
import ar.edu.unq.inscripcionunq.spring.dao.CommissionDao;
import ar.edu.unq.inscripcionunq.spring.model.Commission;

@Service
@Transactional

public class CommissionServiceImp extends GenericServiceImp<Commission> implements CommissionService {
	@Autowired
	private CommissionDao commissionDaoImp;

	@Override
	public List<CommissionJson> getCommissionForSubjectInPoll(String idSubject, String idPoll) {

		List<Commission> commissions = commissionDaoImp.getCommissionForSubjectInPoll(new Long(idSubject),
				new Long(idPoll));
		return commissions.stream().map(commission -> new CommissionJson(commission)).collect(Collectors.toList());
	}

	@Override
	public List<Commission> getAllCommissionsSubjectInPoll(String idPoll) {

		return commissionDaoImp.getAllCommissionsSubjectInPoll(new Long(idPoll));
	}

}
