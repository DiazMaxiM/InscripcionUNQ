package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.IdJson;
import ar.edu.unq.inscripcionunq.spring.dao.PollDao;
import ar.edu.unq.inscripcionunq.spring.exception.CommissionNotExistenException;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectNotFoundinDBException;
import ar.edu.unq.inscripcionunq.spring.exception.StudentNotExistenException;
import ar.edu.unq.inscripcionunq.spring.exception.UserInPollNotFoundException;
import ar.edu.unq.inscripcionunq.spring.exception.VariasComisionesDeUnaMateriaException;
import ar.edu.unq.inscripcionunq.spring.model.Commission;
import ar.edu.unq.inscripcionunq.spring.model.Poll;
import ar.edu.unq.inscripcionunq.spring.model.Student;

@Service
@Transactional

public class PollServiceImp extends GenericServiceImp<Poll> implements PollService {

	@Autowired
	StudentService studentServiceImp;
	@Autowired
	CommissionService commissionServiceImp;

	@Override
	@Transactional

	public List<Poll> getAllPollsActiveForDni(String dni) {
		return ((PollDao) genericDao).getAllPollsActiveForDni(dni);
	}

	@Override
	@Transactional
	public Student getUserDataForPoll(String dni, Long idPoll) throws UserInPollNotFoundException {
		return ((PollDao) genericDao).getUserDataForPoll(dni, idPoll);
	}

	@Override
	@Transactional
	public Boolean puedeGenerarPDF(String dni, Long idPoll) {
		try {
			return !this.getUserDataForPoll(dni, idPoll).getCommissionsRegistration().isEmpty();
		} catch (UserInPollNotFoundException e) {
		}
		return false;
	}

	@Override
	public void setComisionesSeleccionadas(String id, List<IdJson> idsJson) throws IdNumberFormatException,
			StudentNotExistenException, CommissionNotExistenException, VariasComisionesDeUnaMateriaException {
		Student estudiante;
		try {
			estudiante = studentServiceImp.get(new Long(id));
		} catch (NumberFormatException e) {
			throw new IdNumberFormatException();
		} catch (ObjectNotFoundinDBException e) {
			throw new StudentNotExistenException();
		}
		estudiante.eliminarTodasLasComisionesInscripto();
		for (IdJson idJson : idsJson) {
			Commission comision;
			try {
				comision = commissionServiceImp.get(new Long(idJson.id));
			} catch (NumberFormatException e) {
				throw new IdNumberFormatException();
			} catch (ObjectNotFoundinDBException e) {
				throw new CommissionNotExistenException();

			}
			estudiante.addCommissionRegistration(comision);
		}
		studentServiceImp.update(estudiante);
	}

}
