package ar.edu.unq.inscripcionunq.spring.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.PollJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.StudentJson;
import ar.edu.unq.inscripcionunq.spring.model.Poll;
import ar.edu.unq.inscripcionunq.spring.model.Student;
import ar.edu.unq.inscripcionunq.spring.service.PollService;

@RestController
public class PollController {
	@Autowired
	private PollService pollServiceImp;

	@GetMapping("/poll/user/{dni}")
	public ResponseEntity<List<PollJson>> activePollsForAUser(@PathVariable String dni) {
		List<Poll> polls = pollServiceImp.getAllPollsActiveForDni(dni);
		List<PollJson> minipolls = polls.stream()
				.map(m -> new PollJson(m.getId(), m.getName(), m.getStartDate(), m.endDate()))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(minipolls);

	}

	@GetMapping("/poll/userData/{dni}/{idPoll}")
	public ResponseEntity<StudentJson> dataUserForPoll(@PathVariable String dni, @PathVariable Long idPoll) {
		Student student = pollServiceImp.getUserDataForPoll(dni, idPoll);
		StudentJson studentJson = new StudentJson(student);
		return ResponseEntity.ok().body(studentJson);
	}
}