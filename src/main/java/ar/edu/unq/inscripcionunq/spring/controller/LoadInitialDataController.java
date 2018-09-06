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
		long orga = subjectServiceImp.save(matt);

		matt = new Subject("01033", "Matemática I", 8);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		long mate1 = subjectServiceImp.save(matt);

		matt = new Subject("01034", "Programación con Objetos I", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		long obj1 = subjectServiceImp.save(matt);

		matt = new Subject("01315", "Parseo y Generación de Código", 6);
		matt.addCareer(lds);
		long pyGC = subjectServiceImp.save(matt);

		matt = new Subject("01307", "Algoritmos", 6);
		matt.addCareer(lds);
		long alg = subjectServiceImp.save(matt);

		matt = new Subject("01318", "Análisis Estático de Programas y Herramientas Asociadas", 6);
		matt.addCareer(lds);
		long aEPHA = subjectServiceImp.save(matt);

		matt = new Subject("00054", "Análisis Matemático I", 6);
		matt.addCareer(lds);
		long aM1 = subjectServiceImp.save(matt);

		matt = new Subject("01314", "Arquitectura de computadoras", 6);
		matt.addCareer(lds);
		long arqC = subjectServiceImp.save(matt);

		matt = new Subject("01310", "Arquitectura de Software I", 6);
		matt.addCareer(lds);
		long arqS1 = subjectServiceImp.save(matt);

		matt = new Subject("01313", "Arquitectura de Software II", 6);
		matt.addCareer(lds);
		long arqS2 = subjectServiceImp.save(matt);

		matt = new Subject("01316", "Aspectos Legales y Sociales", 6);
		matt.addCareer(lds);
		long aLyS = subjectServiceImp.save(matt);

		matt = new Subject("01312", "Caracteristicas de Lenguajes de Programación", 6);
		matt.addCareer(lds);
		long cLP = subjectServiceImp.save(matt);

		matt = new Subject("01050", "Introducción a las Arquitecturas de Software", 8);
		matt.addCareer(tpi);
		long iAS = subjectServiceImp.save(matt);

		matt = new Subject("01056", "Introducción al Desarrollo de Videojuegos", 8);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		long iDV = subjectServiceImp.save(matt);

		matt = new Subject("01035", "Bases de Datos", 8);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		long bd = subjectServiceImp.save(matt);

		matt = new Subject("01308", "Ingeniería de Requerimientos", 8);
		matt.addCareer(lds);
		long ingR = subjectServiceImp.save(matt);

		matt = new Subject("01048", "Bases de Datos II", 8);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		long bd2 = subjectServiceImp.save(matt);

		matt = new Subject("01036", "Estructuras de Datos", 8);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		long ed = subjectServiceImp.save(matt);

		matt = new Subject("01055", "Herramientas declarativas en Programación", 8);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		long hDP = subjectServiceImp.save(matt);

		matt = new Subject("01304", "Gestión de Proyectos de desarrollo de Software", 8);
		matt.addCareer(lds);
		long gPDS = subjectServiceImp.save(matt);

		matt = new Subject("01037", "Programación con Objetos II", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		long obj2 = subjectServiceImp.save(matt);

		matt = new Subject("01038", "Redes de Computadoras", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		long redes = subjectServiceImp.save(matt);

		matt = new Subject("01039", "Sistemas Operativos", 4);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		long so = subjectServiceImp.save(matt);

		matt = new Subject("00751", "Taller de Trabajo Intelectual", 4);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		long tti = subjectServiceImp.save(matt);

		matt = new Subject("00752", "Taller de Trabajo Universitario", 4);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		long ttu = subjectServiceImp.save(matt);

		matt = new Subject("01309", "Teoría de la Computación", 4);
		matt.addCareer(lds);
		long tC = subjectServiceImp.save(matt);

		matt = new Subject("01060", "Trabajo de Inserción Profesional", 4);
		matt.addCareer(tpi);
		long tip = subjectServiceImp.save(matt);

		matt = new Subject("01040", "Programacion Concurrente", 4);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		long pc = subjectServiceImp.save(matt);

		matt = new Subject("01041", "Matemática II", 4);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		long mate2 = subjectServiceImp.save(matt);

		matt = new Subject("00842", "Matemática III", 4);
		matt.addCareer(lds);
		long mate3 = subjectServiceImp.save(matt);

		matt = new Subject("01042", "Elementos de Ingeniería de Software", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		long ingS = subjectServiceImp.save(matt);

		matt = new Subject("01043", "Construcción de Interfaces de Usuario", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		long cIU = subjectServiceImp.save(matt);

		matt = new Subject("01044", "Estrategias de Persistencia", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		long ePers = subjectServiceImp.save(matt);

		matt = new Subject("01057", "Derecho de Autor  y Derecho de Copia en la Era Digital", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		long dADCED = subjectServiceImp.save(matt);

		matt = new Subject("01045", "Programación Funcional", 4);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		long pF = subjectServiceImp.save(matt);

		matt = new Subject("01046", "Desarrollo de Aplicaciones", 6);
		matt.addCareer(tpi);
		long desa = subjectServiceImp.save(matt);

		matt = new Subject("01047", "Laboratorio de Sistemas Operativos y Redes", 4);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		long labo = subjectServiceImp.save(matt);

		matt = new Subject("90000", "Inglés I", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		long ingles1 = subjectServiceImp.save(matt);

		matt = new Subject("01306", "Lenguajes Formales y Autómatas", 6);
		matt.addCareer(lds);
		long lfa = subjectServiceImp.save(matt);

		matt = new Subject("01302", "Lógica y Programación", 6);
		matt.addCareer(lds);
		long lyp = subjectServiceImp.save(matt);

		matt = new Subject("90028", "Inglés II ( P-W )", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		long ingles2 = subjectServiceImp.save(matt);

		matt = new Subject("01052", "Introducción a la Bioinformática", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		long introBio = subjectServiceImp.save(matt);

		matt = new Subject("01053", "Politicas Públicas en la Sociedad de la Información y la Era Digital", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		long ppsied = subjectServiceImp.save(matt);

		matt = new Subject("01305", "Práctica del Desarrollo de Software", 6);
		matt.addCareer(lds);
		long pds = subjectServiceImp.save(matt);

		matt = new Subject("00604", "Probabilidad Y Estadística", 6);
		matt.addCareer(lds);
		long probE = subjectServiceImp.save(matt);

		matt = new Subject("01049", "Participación y Gestión en Proyectos de Software Libre", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		long pygsl = subjectServiceImp.save(matt);

		matt = new Subject("01051", "Programación con Objetos III", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		long obj3 = subjectServiceImp.save(matt);

		matt = new Subject("00646", "Seguridad Informática", 6);
		matt.addCareer(tpi);
		long sI = subjectServiceImp.save(matt);

		matt = new Subject("01328", "Seminario : Televisión Digital", 6);
		matt.addCareer(tpi);
		long sTD = subjectServiceImp.save(matt);

		matt = new Subject("01054", "Sistemas de Información Geográfica", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		long sIG = subjectServiceImp.save(matt);

		matt = new Subject("01311", "Sistemas Distribuidos", 6);
		matt.addCareer(lds);
		long sD = subjectServiceImp.save(matt);

		matt = new Subject("01059", "Seminarios sobre  Herramientas ó Tecnicas Puntuales: Tecnología y Sociedad", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		long tecSoc = subjectServiceImp.save(matt);

		matt = new Subject("01303", "Seguridad de la Información", 6);
		matt.addCareer(lds);
		long segInfo = subjectServiceImp.save(matt);

		matt = new Subject("01319", "Semántica de Lenguajes de Programación", 6);
		matt.addCareer(lds);
		long sLP = subjectServiceImp.save(matt);

		matt = new Subject("01317", "Seminario final", 6);
		matt.addCareer(lds);
		long semF = subjectServiceImp.save(matt);

		matt = new Subject("01058",
				"Seminarios: Introducción a la Electrónica y Programación de Controladores con Arduino", 6);
		matt.addCareer(tpi);
		matt.addCareer(lds);
		long arduino = subjectServiceImp.save(matt);

		AcademicOffer acc1 = new AcademicOffer("OA-P-S2-18", "Oferta Academica TPI 2 semestre 2018", tpi);
		AcademicOffer acc2 = new AcademicOffer("OA-W-S2-18", "Oferta Academica LIDS 2 semestre 2018", lds);

		Commission commMate1 = new Commission("Mate1 C1", subjectServiceImp.get(mate1), 30);
		commMate1.addHours(TypeDay.TUESDAY, LocalTime.of(9, 00), 4.0f);
		commMate1.addHours(TypeDay.THURSDAY, LocalTime.of(9, 00), 4.0f);
		long idcommMate1 = commissionServiceImp.save(commMate1);

		acc1.addCommission(commissionServiceImp.get(idcommMate1));

		Commission commMate2 = new Commission("Mate1 C2", subjectServiceImp.get(mate1), 30);
		commMate2.addHours(TypeDay.MONDAY, LocalTime.of(18, 00), (float) 4.0f);
		commMate2.addHours(TypeDay.WEDNESDAY, LocalTime.of(18, 00), 4.0f);
		long idcommMate2 = commissionServiceImp.save(commMate2);

		acc1.addCommission(commissionServiceImp.get(idcommMate2));

		Commission commMate3 = new Commission("Mate1 C3", subjectServiceImp.get(mate1), 30);
		commMate3.addHours(TypeDay.MONDAY, LocalTime.of(9, 00), 4.0f);
		commMate3.addHours(TypeDay.WEDNESDAY, LocalTime.of(9, 00), 4.0f);
		long idcommMate3 = commissionServiceImp.save(commMate3);

		acc1.addCommission(commissionServiceImp.get(idcommMate3));

		Commission commInPr1 = new Commission("InPr C1", subjectServiceImp.get(intro), 30);
		commInPr1.addHours(TypeDay.MONDAY, LocalTime.of(9, 00), 2.5f);
		commInPr1.addHours(TypeDay.THURSDAY, LocalTime.of(9, 00), 2.5f);
		commInPr1.addHours(TypeDay.WEDNESDAY, LocalTime.of(8, 00), 3.0f);
		long idcommInPr1 = commissionServiceImp.save(commInPr1);

		acc1.addCommission(commissionServiceImp.get(idcommInPr1));

		Commission commInPr2 = new Commission("InPr C2", subjectServiceImp.get(intro), 30);
		commInPr2.addHours(TypeDay.MONDAY, LocalTime.of(12, 00), 2.5f);
		commInPr2.addHours(TypeDay.THURSDAY, LocalTime.of(12, 00), 2.5f);
		commInPr2.addHours(TypeDay.WEDNESDAY, LocalTime.of(8, 00), 3.0f);
		long idcommInPr2 = commissionServiceImp.save(commInPr2);

		acc1.addCommission(commissionServiceImp.get(idcommInPr2));

		Commission commInPr3 = new Commission("InPr C3", subjectServiceImp.get(intro), 30);
		commInPr3.addHours(TypeDay.MONDAY, LocalTime.of(15, 00), 2.5f);
		commInPr3.addHours(TypeDay.THURSDAY, LocalTime.of(15, 00), 2.5f);
		commInPr3.addHours(TypeDay.WEDNESDAY, LocalTime.of(18, 00), 3.0f);
		long idcommInPr3 = commissionServiceImp.save(commInPr3);
		acc1.addCommission(commissionServiceImp.get(idcommInPr3));

		Commission commInPr4 = new Commission("InPr C4", subjectServiceImp.get(intro), 30);
		commInPr4.addHours(TypeDay.MONDAY, LocalTime.of(19, 00), 2.5f);
		commInPr4.addHours(TypeDay.THURSDAY, LocalTime.of(19, 00), 2.5f);
		commInPr4.addHours(TypeDay.WEDNESDAY, LocalTime.of(18, 00), 3.0f);
		long idcommInPr4 = commissionServiceImp.save(commInPr4);
		acc1.addCommission(commissionServiceImp.get(idcommInPr4));

		Commission commOrga1 = new Commission("Orga C1", subjectServiceImp.get(orga), 30);
		commOrga1.addHours(TypeDay.TUESDAY, LocalTime.of(8, 00), 3.0f);
		commOrga1.addHours(TypeDay.FRIDAY, LocalTime.of(8, 00), 3f);
		long idcommOrga1 = commissionServiceImp.save(commOrga1);
		acc1.addCommission(commissionServiceImp.get(idcommOrga1));

		Commission commOrga2 = new Commission("Orga C2", subjectServiceImp.get(orga), 30);
		commOrga2.addHours(TypeDay.TUESDAY, LocalTime.of(11, 00), 3.0f);
		commOrga2.addHours(TypeDay.FRIDAY, LocalTime.of(11, 00), 3f);
		long idcommOrga2 = commissionServiceImp.save(commOrga2);
		acc1.addCommission(commissionServiceImp.get(idcommOrga2));

		Commission commOrga3 = new Commission("Orga C3", subjectServiceImp.get(orga), 30);
		commOrga3.addHours(TypeDay.TUESDAY, LocalTime.of(16, 00), 3.0f);
		commOrga3.addHours(TypeDay.FRIDAY, LocalTime.of(16, 00), 3f);
		long idcommOrga3 = commissionServiceImp.save(commOrga3);
		acc1.addCommission(commissionServiceImp.get(idcommOrga3));

		Commission commOrga4 = new Commission("Orga C4", subjectServiceImp.get(orga), 30);
		commOrga4.addHours(TypeDay.TUESDAY, LocalTime.of(19, 00), 3.0f);
		commOrga4.addHours(TypeDay.FRIDAY, LocalTime.of(19, 00), 3f);
		long idcommOrga4 = commissionServiceImp.save(commOrga4);
		acc1.addCommission(commissionServiceImp.get(idcommOrga4));

		Commission commed1 = new Commission("EstrD C1", subjectServiceImp.get(ed), 30);
		commed1.addHours(TypeDay.TUESDAY, LocalTime.of(18, 00), 3.0f);
		commed1.addHours(TypeDay.WEDNESDAY, LocalTime.of(18, 00), 3f);
		commed1.addHours(TypeDay.SATURDAY, LocalTime.of(8, 00), 3f);
		long idcommed1 = commissionServiceImp.save(commed1);
		acc1.addCommission(commissionServiceImp.get(idcommed1));

		Commission commobj11 = new Commission("Obj1 C1", subjectServiceImp.get(obj1), 30);
		commobj11.addHours(TypeDay.WEDNESDAY, LocalTime.of(14, 00), 4f);
		commobj11.addHours(TypeDay.SATURDAY, LocalTime.of(8, 00), 4f);
		long idcommobj11 = commissionServiceImp.save(commobj11);
		acc1.addCommission(commissionServiceImp.get(idcommobj11));

		Commission commobj12 = new Commission("Obj1 C2", subjectServiceImp.get(obj1), 30);
		commobj12.addHours(TypeDay.MONDAY, LocalTime.of(18, 00), 4f);
		commobj12.addHours(TypeDay.TUESDAY, LocalTime.of(18, 00), 4f);
		long idcommobj12 = commissionServiceImp.save(commobj12);
		acc1.addCommission(commissionServiceImp.get(idcommobj12));

		Commission commbd1 = new Commission("BD C1", subjectServiceImp.get(bd), 30);
		commbd1.addHours(TypeDay.MONDAY, LocalTime.of(18, 00), 3f);
		commbd1.addHours(TypeDay.THURSDAY, LocalTime.of(18, 00), 3f);
		long idcommbd1 = commissionServiceImp.save(commbd1);
		acc1.addCommission(commissionServiceImp.get(idcommbd1));

		Commission commso1 = new Commission("SO C1", subjectServiceImp.get(so), 30);
		commso1.addHours(TypeDay.THURSDAY, LocalTime.of(16, 00), 6f);
		long idcommso1 = commissionServiceImp.save(commso1);
		acc1.addCommission(commissionServiceImp.get(idcommso1));

		Commission commso2 = new Commission("SO C2", subjectServiceImp.get(so), 30);
		commso2.addHours(TypeDay.WEDNESDAY, LocalTime.of(18, 00), 4f);
		commso2.addHours(TypeDay.THURSDAY, LocalTime.of(16, 00), 2f);
		long idcommso2 = commissionServiceImp.save(commso2);
		acc1.addCommission(commissionServiceImp.get(idcommso2));

		Commission commredes1 = new Commission("Redes C1", subjectServiceImp.get(redes), 30);
		commredes1.addHours(TypeDay.TUESDAY, LocalTime.of(9, 00), 2f);
		commredes1.addHours(TypeDay.THURSDAY, LocalTime.of(8, 00), 4f);
		long idcommredes1 = commissionServiceImp.save(commredes1);
		acc1.addCommission(commissionServiceImp.get(idcommredes1));

		Commission commredes2 = new Commission("Redes C1", subjectServiceImp.get(redes), 30);
		commredes2.addHours(TypeDay.TUESDAY, LocalTime.of(18, 00), 4f);
		commredes2.addHours(TypeDay.THURSDAY, LocalTime.of(18, 00), 2f);
		long idcommredes2 = commissionServiceImp.save(commredes2);
		acc1.addCommission(commissionServiceImp.get(idcommredes2));

		Commission commobj21 = new Commission("Obj2 C1", subjectServiceImp.get(obj2), 30);
		commobj21.addHours(TypeDay.MONDAY, LocalTime.of(18, 00), 3f);
		commobj21.addHours(TypeDay.FRIDAY, LocalTime.of(15, 00), 3f);
		long idcommobj21 = commissionServiceImp.save(commobj21);
		acc1.addCommission(commissionServiceImp.get(idcommobj21));

		Commission commobj22 = new Commission("Obj2 C3", subjectServiceImp.get(obj2), 30);
		commobj22.addHours(TypeDay.MONDAY, LocalTime.of(18, 00), 3f);
		commobj22.addHours(TypeDay.FRIDAY, LocalTime.of(18, 00), 3f);
		long idcommobj22 = commissionServiceImp.save(commobj22);
		acc1.addCommission(commissionServiceImp.get(idcommobj22));

		Commission commMate21 = new Commission("Mate2 C1", subjectServiceImp.get(mate2), 30);
		commMate21.addHours(TypeDay.THURSDAY, LocalTime.of(18, 00), 4f);
		long idcommMate21 = commissionServiceImp.save(commMate21);
		acc1.addCommission(commissionServiceImp.get(idcommMate21));

		Commission commMate22 = new Commission("Mate2 C2", subjectServiceImp.get(mate2), 30);
		commMate22.addHours(TypeDay.TUESDAY, LocalTime.of(16, 00), 2f);
		commMate22.addHours(TypeDay.WEDNESDAY, LocalTime.of(16, 00), 2f);
		long idcommMate22 = commissionServiceImp.save(commMate22);
		acc1.addCommission(commissionServiceImp.get(idcommMate22));

		Commission commcIU1 = new Commission("UIs C1", subjectServiceImp.get(cIU), 30);
		commcIU1.addHours(TypeDay.THURSDAY, LocalTime.of(16, 00), 6f);
		long idcommcIU1 = commissionServiceImp.save(commcIU1);
		acc1.addCommission(commissionServiceImp.get(idcommcIU1));

		Commission commingS1 = new Commission("IngSoft C1", subjectServiceImp.get(ingS), 30);
		commingS1.addHours(TypeDay.MONDAY, LocalTime.of(19, 00), 3f);
		commingS1.addHours(TypeDay.WEDNESDAY, LocalTime.of(19, 00), 3f);
		long idcommingS1 = commissionServiceImp.save(commingS1);
		acc1.addCommission(commissionServiceImp.get(idcommingS1));

		Commission commePers1 = new Commission("EPers C1", subjectServiceImp.get(ePers), 30);
		commePers1.addHours(TypeDay.FRIDAY, LocalTime.of(16, 00), 6f);
		long idcommePers1 = commissionServiceImp.save(commePers1);
		acc1.addCommission(commissionServiceImp.get(idcommePers1));

		Commission commlabo1 = new Commission("Labo C1", subjectServiceImp.get(labo), 30);
		commlabo1.addHours(TypeDay.THURSDAY, LocalTime.of(18, 00), 4f);
		long idcommlabo1 = commissionServiceImp.save(commlabo1);
		acc1.addCommission(commissionServiceImp.get(idcommlabo1));

		Commission commpF1 = new Commission("PF C1", subjectServiceImp.get(pF), 30);
		commpF1.addHours(TypeDay.WEDNESDAY, LocalTime.of(15, 00), 2f);
		commpF1.addHours(TypeDay.SATURDAY, LocalTime.of(11, 00), 2f);
		long idcommpF1 = commissionServiceImp.save(commpF1);
		acc1.addCommission(commissionServiceImp.get(idcommpF1));

		Commission commdesa1 = new Commission("DesApp C1", subjectServiceImp.get(desa), 30);
		commdesa1.addHours(TypeDay.TUESDAY, LocalTime.of(16, 00), 6f);
		long idcommdesa1 = commissionServiceImp.save(commdesa1);
		acc1.addCommission(commissionServiceImp.get(idcommdesa1));

		Commission commtip1 = new Commission("TIP C1", subjectServiceImp.get(tip), 30);
		commtip1.addHours(TypeDay.SATURDAY, LocalTime.of(8, 00), 5f);
		long idcommtip1 = commissionServiceImp.save(commtip1);
		acc1.addCommission(commissionServiceImp.get(idcommtip1));

		Commission commsI1 = new Commission("TIP C1", subjectServiceImp.get(sI), 30);
		commsI1.addHours(TypeDay.SATURDAY, LocalTime.of(9, 00), 4f);
		long idcommsI1 = commissionServiceImp.save(commsI1);
		acc1.addCommission(commissionServiceImp.get(idcommsI1));

		Commission commpc1 = new Commission("PConc C1", subjectServiceImp.get(pc), 30);
		commpc1.addHours(TypeDay.WEDNESDAY, LocalTime.of(17, 00), 4f);
		long idcommpc1 = commissionServiceImp.save(commpc1);
		acc1.addCommission(commissionServiceImp.get(idcommpc1));

		Commission commobj3 = new Commission("Obj3 C1", subjectServiceImp.get(obj3), 30);
		commobj3.addHours(TypeDay.TUESDAY, LocalTime.of(18, 00), 4f);
		long idcommobj3 = commissionServiceImp.save(commobj3);
		acc1.addCommission(commissionServiceImp.get(idcommobj3));

		Commission commcLP = new Commission("CLP C1", subjectServiceImp.get(cLP), 30);
		commcLP.addHours(TypeDay.TUESDAY, LocalTime.of(18, 00), 2f);
		commcLP.addHours(TypeDay.WEDNESDAY, LocalTime.of(18, 00), 2f);
		long idcommcLP = commissionServiceImp.save(commcLP);
		acc1.addCommission(commissionServiceImp.get(idcommcLP));

		Commission commlyp = new Commission("LyP C1", subjectServiceImp.get(lyp), 30);
		commlyp.addHours(TypeDay.MONDAY, LocalTime.of(9, 00), 2f);
		commlyp.addHours(TypeDay.TUESDAY, LocalTime.of(9, 00), 2f);
		long idcommlyp = commissionServiceImp.save(commlyp);
		acc1.addCommission(commissionServiceImp.get(idcommlyp));

		Commission commiDV = new Commission("Videojuegos C1", subjectServiceImp.get(iDV), 30);
		commiDV.addHours(TypeDay.WEDNESDAY, LocalTime.of(8, 00), 4f);
		long idcommiDV = commissionServiceImp.save(commiDV);
		acc1.addCommission(commissionServiceImp.get(idcommiDV));

		Commission commingles2 = new Commission("Ingles2 C1", subjectServiceImp.get(ingles2), 30);
		commingles2.addHours(TypeDay.WEDNESDAY, LocalTime.of(14, 00), 3f);
		long idcommingles2 = commissionServiceImp.save(commingles2);
		acc1.addCommission(commissionServiceImp.get(idcommingles2));

		Long idAcamicOffer1 = academicOfferServiceImp.save(acc1);
		Long idAcamicOffer2 = academicOfferServiceImp.save(acc2);

		Poll poll = new Poll("Encuesta segundo semestre 2018", LocalDateTime.of(2018, 8, 25, 00, 00),
				LocalDateTime.of(2018, 12, 1, 00, 00));
		poll.addAcademicOffer((AcademicOffer) academicOfferServiceImp.get(idAcamicOffer1));
		poll.addAcademicOffer((AcademicOffer) academicOfferServiceImp.get(idAcamicOffer2));

		Student maxi = new Student("Maximiliano Martin", "Diaz", "33810763", "diazmaxi@gmail.com");
		maxi.addcareerInscription(tpi);
		maxi.addMatterAprroved(subjectServiceImp.get(intro));
		maxi.addMatterAprroved(subjectServiceImp.get(mate1));
		poll.addStudent(maxi);

		pollServiceImp.save(poll);

	}

}