package ar.edu.unq.inscripcionunq.spring.controller;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.inscripcionunq.spring.model.AcademicOffer;
import ar.edu.unq.inscripcionunq.spring.model.Career;
import ar.edu.unq.inscripcionunq.spring.model.Commission;
import ar.edu.unq.inscripcionunq.spring.model.Poll;
import ar.edu.unq.inscripcionunq.spring.model.Student;
import ar.edu.unq.inscripcionunq.spring.model.Subject;
import ar.edu.unq.inscripcionunq.spring.model.TypeDay;
import ar.edu.unq.inscripcionunq.spring.service.GenericService;

@RestController
public class LoadInitialDataController {

	@Autowired
	private GenericService<Career> careerServiceImp;

	@Autowired
	private GenericService<Subject> subjectServiceImp;

	@Autowired
	private GenericService<Commission> commissionServiceImp;

	@Autowired
	private GenericService<AcademicOffer> academicOfferServiceImp;

	@Autowired
	private GenericService<Poll> pollServiceImp;

	@RequestMapping(value = "loadData", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void loadData() {
		Career tpi = new Career("P", "Tecnicatura Universitaria en Programacion Informatica");
		Career lds = new Career("W", "Licenciatura en Informatica");
		long aa = careerServiceImp.save(tpi);
		tpi = careerServiceImp.get(aa);
		lds = careerServiceImp.get(careerServiceImp.save(lds));

		Subject matt = new Subject("00487", "Introducción a la Programación", 8);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		long intro = subjectServiceImp.save(matt);

		matt = new Subject("01032", "Organización de Computadoras", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		subjectServiceImp.save(matt);

		matt = new Subject("01033", "Matemática I", 8);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		long mate1 = subjectServiceImp.save(matt);

		matt = new Subject("01034", "Programación con Objetos I", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		subjectServiceImp.save(matt);

		matt = new Subject("01035", "Bases de Datos", 8);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		subjectServiceImp.save(matt);

		matt = new Subject("01036", "Estructuras de Datos", 8);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		subjectServiceImp.save(matt);

		matt = new Subject("01037", "Programación con Objetos II", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		subjectServiceImp.save(matt);

		matt = new Subject("01038", "Redes de Computadoras", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		subjectServiceImp.save(matt);

		matt = new Subject("01039", "Sistemas Operativos", 4);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		subjectServiceImp.save(matt);

		matt = new Subject("01040", "Programacion Concurrente", 4);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		subjectServiceImp.save(matt);

		matt = new Subject("01041", "Matemática II", 4);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		subjectServiceImp.save(matt);

		matt = new Subject("01042", "Elementos de Ingeniería de Software", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		subjectServiceImp.save(matt);

		matt = new Subject("01043", "Construcción de Interfaces de Usuario", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		subjectServiceImp.save(matt);

		matt = new Subject("01044", "Estrategias de Persistencia", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		subjectServiceImp.save(matt);

		matt = new Subject("01045", "Programación Funcional", 4);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		subjectServiceImp.save(matt);

		matt = new Subject("01046", "Desarrollo de Aplicaciones", 6);
		matt.addCareer(tpi);
		subjectServiceImp.save(matt);

		matt = new Subject("01047", "Laboratorio de Sistemas Operativos y Redes", 4);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		subjectServiceImp.save(matt);

		matt = new Subject("90000", "Inglés I", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		subjectServiceImp.save(matt);

		matt = new Subject("90028", "Inglés II ( P-W )", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		subjectServiceImp.save(matt);

		matt = new Subject("01049", "Participación y Gestión en Proyectos de Software Libre", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		subjectServiceImp.save(matt);
		matt = new Subject("01051", "Programación con Objetos III", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		subjectServiceImp.save(matt);
		matt = new Subject("00646", "Seguridad Informática", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		subjectServiceImp.save(matt);

		matt = new Subject("01058",
				"Seminarios: Introducción a la Electrónica y Programación de Controladores con Arduino", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		subjectServiceImp.save(matt);

		Commission comm1 = new Commission("Intro1", subjectServiceImp.get(intro), 30);
		comm1.addHours(TypeDay.FRIDAY, LocalTime.of(10, 00), 2);
		commissionServiceImp.save(comm1);

		Commission comm2 = new Commission("Intro2", subjectServiceImp.get(intro), 30);
		comm1.addHours(TypeDay.MONDAY, LocalTime.of(14, 00), 2);
		commissionServiceImp.save(comm2);

		AcademicOffer acc1 = new AcademicOffer("OA-P-S2-18", "Oferta Academica TPI 2 semestre 2018", tpi);
		AcademicOffer acc2 = new AcademicOffer("OA-W-S2-18", "Oferta Academica LIDS 2 semestre 2018", lds);

		acc1.addCommission(comm1);
		acc1.addCommission(comm2);

		acc2.addCommission(comm2);
		Long idAcamicOffer1 = academicOfferServiceImp.save(acc1);
		Long idAcamicOffer2 = academicOfferServiceImp.save(acc2);

		Poll poll = new Poll("Encuesta segundo semestre 2018", LocalDateTime.of(2018, 8, 25, 00, 00),
				LocalDateTime.of(2018, 12, 1, 00, 00));
		poll.addAcademicOffer((AcademicOffer) academicOfferServiceImp.get(idAcamicOffer1));
		poll.addAcademicOffer((AcademicOffer) academicOfferServiceImp.get(idAcamicOffer2));

		Student maxi = new Student("Maximiliano Martin", "Diaz", "33810763", "diazmaxi@gmail.com");
		maxi.addcareerInscription(tpi);
		maxi.addcareerInscription(lds);
		maxi.addMatterAprroved(subjectServiceImp.get(intro));
		maxi.addMatterAprroved(subjectServiceImp.get(mate1));
		poll.addStudent(maxi);

		pollServiceImp.save(poll);

	}

}