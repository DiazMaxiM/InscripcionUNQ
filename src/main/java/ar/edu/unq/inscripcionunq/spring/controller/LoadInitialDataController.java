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
import ar.edu.unq.inscripcionunq.spring.model.Matter;
import ar.edu.unq.inscripcionunq.spring.model.Poll;
import ar.edu.unq.inscripcionunq.spring.model.Student;
import ar.edu.unq.inscripcionunq.spring.model.TypeDay;
import ar.edu.unq.inscripcionunq.spring.service.AcademicOfferService;
import ar.edu.unq.inscripcionunq.spring.service.CareerService;
import ar.edu.unq.inscripcionunq.spring.service.CommissionService;
import ar.edu.unq.inscripcionunq.spring.service.MatterService;
import ar.edu.unq.inscripcionunq.spring.service.PollService;

@RestController
public class LoadInitialDataController {

	@Autowired
	private CareerService careerService;

	@Autowired
	private MatterService matterService;

	@Autowired
	private CommissionService commissionService;

	@Autowired
	private AcademicOfferService academicOfferService;

	@Autowired
	private PollService pollService;

	@RequestMapping(value = "loadData", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void loadData() {
		Career tpi = new Career("P", "Tecnicatura Universitaria en Programacion Informatica");
		Career lds = new Career("W", "Licenciatura en Informatica");
		tpi = careerService.get(careerService.save(tpi));
		lds = careerService.get(careerService.save(lds));

		Matter matt = new Matter("00487", "Introducción a la Programación", 8);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		long intro = matterService.save(matt);

		matt = new Matter("01032", "Organización de Computadoras", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		matterService.save(matt);

		matt = new Matter("01033", "Matemática I", 8);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		matterService.save(matt);

		matt = new Matter("01034", "Programación con Objetos I", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		matterService.save(matt);

		matt = new Matter("01035", "Bases de Datos", 8);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		matterService.save(matt);

		matt = new Matter("01036", "Estructuras de Datos", 8);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		matterService.save(matt);

		matt = new Matter("01037", "Programación con Objetos II", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		matterService.save(matt);

		matt = new Matter("01038", "Redes de Computadoras", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		matterService.save(matt);

		matt = new Matter("01039", "Sistemas Operativos", 4);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		matterService.save(matt);

		matt = new Matter("01040", "Programacion Concurrente", 4);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		matterService.save(matt);

		matt = new Matter("01041", "Matemática II", 4);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		matterService.save(matt);

		matt = new Matter("01042", "Elementos de Ingeniería de Software", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		matterService.save(matt);

		matt = new Matter("01043", "Construcción de Interfaces de Usuario", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		matterService.save(matt);

		matt = new Matter("01044", "Estrategias de Persistencia", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		matterService.save(matt);

		matt = new Matter("01045", "Programación Funcional", 4);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		matterService.save(matt);

		matt = new Matter("01046", "Desarrollo de Aplicaciones", 6);
		matt.addCareer(tpi);
		matterService.save(matt);

		matt = new Matter("01047", "Laboratorio de Sistemas Operativos y Redes", 4);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		matterService.save(matt);

		matt = new Matter("90000", "Inglés I", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		matterService.save(matt);

		matt = new Matter("90028", "Inglés II ( P-W )", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		matterService.save(matt);

		matt = new Matter("01049", "Participación y Gestión en Proyectos de Software Libre", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		matterService.save(matt);
		matt = new Matter("01051", "Programación con Objetos III", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		matterService.save(matt);
		matt = new Matter("00646", "Seguridad Informática", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		matterService.save(matt);

		matt = new Matter("01058",
				"Seminarios: Introducción a la Electrónica y Programación de Controladores con Arduino", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		matterService.save(matt);

		Commission comm1 = new Commission("Intro1", matterService.get(intro), 30);
		comm1.addHours(TypeDay.FRIDAY, LocalTime.of(10, 00), 2);
		commissionService.save(comm1);

		Commission comm2 = new Commission("Intro2", matterService.get(intro), 30);
		comm1.addHours(TypeDay.MONDAY, LocalTime.of(14, 00), 2);
		commissionService.save(comm2);

		AcademicOffer acc1 = new AcademicOffer("OA-P-S2-18", "Oferta Academica TPI 2 semestre 2018", tpi);
		AcademicOffer acc2 = new AcademicOffer("OA-W-S2-18", "Oferta Academica LIDS 2 semestre 2018", lds);

		acc1.addCommission(comm1);
		acc1.addCommission(comm2);

		acc2.addCommission(comm2);
		Long idAcamicOffer1 = academicOfferService.save(acc1);
		Long idAcamicOffer2 = academicOfferService.save(acc2);

		Poll poll = new Poll("Encuesta segundo semestre 2018", LocalDateTime.of(2018, 8, 25, 00, 00),
				LocalDateTime.of(2018, 12, 1, 00, 00));
		poll.addAcademicOffer(academicOfferService.get(idAcamicOffer1));
		poll.addAcademicOffer(academicOfferService.get(idAcamicOffer2));

		Student maxi = new Student("Maximiliano Martin", "Diaz", "33810763");
		maxi.addcareerInscription(tpi);
		maxi.addcareerInscription(lds);
		poll.addStudent(maxi);

		pollService.save(poll);

	}

}