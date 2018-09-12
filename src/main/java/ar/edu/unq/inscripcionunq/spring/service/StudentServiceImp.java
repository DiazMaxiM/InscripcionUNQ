package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.DocumentException;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.CommissionJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.SubjectJson;
import ar.edu.unq.inscripcionunq.spring.dao.CommissionDao;
import ar.edu.unq.inscripcionunq.spring.dao.SubjectDao;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectNotFoundinDBException;
import ar.edu.unq.inscripcionunq.spring.exception.StudentNotExistenException;
import ar.edu.unq.inscripcionunq.spring.model.Career;
import ar.edu.unq.inscripcionunq.spring.model.Certificate;
import ar.edu.unq.inscripcionunq.spring.model.Commission;
import ar.edu.unq.inscripcionunq.spring.model.Student;
import ar.edu.unq.inscripcionunq.spring.model.Subject;

@Service
@Transactional

public class StudentServiceImp extends GenericServiceImp<Student> implements StudentService {

	@Autowired
	private SubjectDao subjectDaoImp;
	@Autowired
	private CommissionDao commissionDaoImp;

	@Override
	public List<SubjectJson> userApprovedSubjects(String idUser)
			throws IdNumberFormatException, StudentNotExistenException {
		Student student;
		try {
			student = this.get(new Long(idUser));
		} catch (NumberFormatException e) {
			throw new IdNumberFormatException();
		} catch (ObjectNotFoundinDBException e) {
			throw new StudentNotExistenException();
		}
		List<Career> careers = student.getCareersInscription();
		List<Subject> subjects = subjectDaoImp.getSubjectsForCareers(careers);
		List<SubjectJson> subjectsWithApproved = subjects.stream().map(s -> new SubjectJson(s, student.isApproved(s)))
				.collect(Collectors.toList());
		return subjectsWithApproved;
	}

	@Override
	public void updateUserApprovedSubjects(String idUser, List<SubjectJson> studentsJson)
			throws IdNumberFormatException, StudentNotExistenException {
		Student student;
		try {
			student = this.get(new Long(idUser));
		} catch (NumberFormatException e) {
			throw new IdNumberFormatException();
		} catch (ObjectNotFoundinDBException e) {
			throw new StudentNotExistenException();
		}
		List<SubjectJson> subjectsApproved = studentsJson.stream().filter(studentJson -> studentJson.approved)
				.collect(Collectors.toList());
		if (!((int) subjectsApproved.size() == student.getSubjectsApproved().size()
				&& student.getSubjectsApproved().containsAll(
						subjectsApproved.stream().map(sA -> subjectDaoImp.get(sA.id)).collect(Collectors.toList())))) {
			// crear incidencia
			System.out.println("Crear incidencia");
			List<Subject> newSubjectsApproved = subjectsApproved.stream()
					.map(subjectApproved -> subjectDaoImp.get(subjectApproved.id)).collect(Collectors.toList());
			student.setSubjectsApproved(newSubjectsApproved);
		}
	}

	@Override
	public List<SubjectJson> userDisapprovedSubjectsWithCommissionAvailable(String idUser)
			throws IdNumberFormatException, StudentNotExistenException {
		Student student;
		try {
			student = this.get(new Long(idUser));
		} catch (NumberFormatException e) {
			throw new IdNumberFormatException();
		} catch (ObjectNotFoundinDBException e) {
			throw new StudentNotExistenException();
		}
		Long idPoll = student.getPoll().getId();
		List<Commission> commissions = commissionDaoImp.getAllCommissionsSubjectInPoll(idPoll);
		List<Subject> subjectsApproved = student.getSubjectsApproved();
		List<Commission> commissionDisapproved = commissions.stream()
				.filter(commission -> !subjectsApproved.contains(commission.getSubject())).collect(Collectors.toList());
		List<Subject> subjectsDisapproved = commissionDisapproved.stream().map(c -> c.getSubject()).distinct()
				.collect(Collectors.toList());

		List<SubjectJson> subjectJson = subjectsDisapproved.stream().map(subject -> new SubjectJson(subject, false))
				.collect(Collectors.toList());

		subjectJson.stream()
				.map(sJ -> sJ.addCommissionJson(
						commissionDisapproved.stream().filter(c -> c.getSubject().getId().equals(sJ.id))
								.map(com -> new CommissionJson(com)).collect(Collectors.toList())

				)).collect(Collectors.toList());

		return subjectJson;
	}

	public Certificate getCertificate(String idStudent)
			throws StudentNotExistenException, DocumentException, IdNumberFormatException {
		try {
			Student student = this.get(new Long(idStudent));
			Certificate certificate = new Certificate();
			certificate.setEstudiante(student);
			certificate.generatePDF();
			return certificate;
		} catch (ObjectNotFoundinDBException e) {
			throw new StudentNotExistenException();
		} catch (NumberFormatException e) {
			throw new IdNumberFormatException();
		}
	}

}
