package ar.edu.unq.inscripcionunq.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.CommissionJson;
import ar.edu.unq.inscripcionunq.spring.service.CommissionService;

@RestController
public class CommissionController {

	@Autowired
	private CommissionService commissionServiceImp;

	@GetMapping("/commission/subject/poll/{idSubject}/{idPoll}")
	public ResponseEntity<List<CommissionJson>> commissionForSubjectInPoll(@PathVariable String idSubject,
			@PathVariable String idPoll) {
		List<CommissionJson> commissions = commissionServiceImp.getCommissionForSubjectInPoll(idSubject, idPoll);

		return ResponseEntity.ok().body(commissions);
	}
}