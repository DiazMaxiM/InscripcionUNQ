package ar.edu.unq.inscripcionunq.spring.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ExceptionJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.IdJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.PollJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.StudentJson;
import ar.edu.unq.inscripcionunq.spring.exception.CommissionNotExistenException;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.StudentNotExistenException;
import ar.edu.unq.inscripcionunq.spring.exception.UserInPollNotFoundException;
import ar.edu.unq.inscripcionunq.spring.exception.VariasComisionesDeUnaMateriaException;
import ar.edu.unq.inscripcionunq.spring.model.Poll;
import ar.edu.unq.inscripcionunq.spring.model.Student;
import ar.edu.unq.inscripcionunq.spring.service.PollService;
import ar.edu.unq.inscripcionunq.spring.service.StudentService;

@RestController
public class PollController {
	@Autowired
	private PollService pollServiceImp;
	@Autowired
	private StudentService studentServiceImp;

	@GetMapping("/poll/user/{dni}")
	public ResponseEntity<List<PollJson>> activePollsForAUser(@PathVariable String dni)
			throws UserInPollNotFoundException {
		List<Poll> polls = pollServiceImp.getAllPollsActiveForDni(dni);

		List<PollJson> minipolls = polls.stream().map(m -> new PollJson(m.getId(), m.getName(), m.getStartDate(),
				m.endDate(), pollServiceImp.puedeGenerarPDF(dni, m.getId()))

		).collect(Collectors.toList());
		return ResponseEntity.ok().body(minipolls);

	}

	@GetMapping("/poll/userData/{dni}/{idPoll}")
	public ResponseEntity dataUserForPoll(@PathVariable String dni, @PathVariable Long idPoll) {
		Student student;
		try {
			student = pollServiceImp.getUserDataForPoll(dni, idPoll);
		} catch (UserInPollNotFoundException exception) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(exception));
		}
		StudentJson studentJson = new StudentJson(student);
		return ResponseEntity.ok().body(studentJson);
	}

	@PostMapping("/poll/comisionesSeleccionada/{id}")
	public ResponseEntity updateUserData(@PathVariable String id, @RequestBody List<IdJson> idsJson) {
		try {
			pollServiceImp.setComisionesSeleccionadas(id, idsJson);
		} catch (IdNumberFormatException | StudentNotExistenException | CommissionNotExistenException
				| VariasComisionesDeUnaMateriaException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		try {
			try {
				studentServiceImp.sendCertificate(id);
			} catch (DocumentException | EmailException e) {
			}
		} catch (StudentNotExistenException | IdNumberFormatException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		return ResponseEntity.ok().body(null);
	}

}