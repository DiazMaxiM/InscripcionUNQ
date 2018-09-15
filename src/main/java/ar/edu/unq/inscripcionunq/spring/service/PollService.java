package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.IdJson;
import ar.edu.unq.inscripcionunq.spring.exception.CommissionNotExistenException;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.StudentNotExistenException;
import ar.edu.unq.inscripcionunq.spring.exception.UserInPollNotFoundException;
import ar.edu.unq.inscripcionunq.spring.exception.VariasComisionesDeUnaMateriaException;
import ar.edu.unq.inscripcionunq.spring.model.Poll;
import ar.edu.unq.inscripcionunq.spring.model.Student;

public interface PollService extends GenericService<Poll> {

	public List<Poll> getAllPollsActiveForDni(String dni);

	public Student getUserDataForPoll(String dni, Long idPoll) throws UserInPollNotFoundException;

	public void setComisionesSeleccionadas(String id, List<IdJson> idsJson) throws IdNumberFormatException,
			StudentNotExistenException, CommissionNotExistenException, VariasComisionesDeUnaMateriaException;

	public Boolean puedeGenerarPDF(String dni, Long id);
}
