package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.SubjectJson;
import ar.edu.unq.inscripcionunq.spring.dao.SubjectDao;
import ar.edu.unq.inscripcionunq.spring.model.Career;
import ar.edu.unq.inscripcionunq.spring.model.Student;
import ar.edu.unq.inscripcionunq.spring.model.Subject;

@Service
@Transactional

public class StudentServiceImp extends GenericServiceImp<Student> implements StudentService {

	@Autowired
	private SubjectDao subjectDaoImp;

	@Override
	public List<SubjectJson> userApprovedSubjects(String idUser) {
		Student student = genericDao.get(new Long(idUser));
		List<Career> careers = student.getCareersInscription();
		List<Subject> subjects = subjectDaoImp.getSubjectsForCareers(careers);
		List<SubjectJson> subjectsWithApproved = subjects.stream().map(s -> new SubjectJson(s, student.isApproved(s)))
				.collect(Collectors.toList());
		return subjectsWithApproved;
	}

	@Override
	public void updateUserApprovedSubjects(String idUser, List<SubjectJson> studentsJson) {
		Student student = genericDao.get(new Long(idUser));
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

}
