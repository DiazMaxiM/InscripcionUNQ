package ar.edu.unq.inscripcionunq.spring.service;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.DocumentException;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.CarreraJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ComisionJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.MateriaJson;
import ar.edu.unq.inscripcionunq.spring.dao.ComisionDao;
import ar.edu.unq.inscripcionunq.spring.dao.EstudianteDao;
import ar.edu.unq.inscripcionunq.spring.dao.MateriaDao;
import ar.edu.unq.inscripcionunq.spring.exception.CertificadoException;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectNotFoundinDBException;
import ar.edu.unq.inscripcionunq.spring.exception.StudentNotExistenException;
import ar.edu.unq.inscripcionunq.spring.model.Carrera;
import ar.edu.unq.inscripcionunq.spring.model.Certificado;
import ar.edu.unq.inscripcionunq.spring.model.Comision;
import ar.edu.unq.inscripcionunq.spring.model.Estudiante;
import ar.edu.unq.inscripcionunq.spring.model.Mail;
import ar.edu.unq.inscripcionunq.spring.model.Materia;

@Service
@Transactional
public class EstudianteServiceImp extends GenericServiceImp<Estudiante> implements EstudianteService {

	@Autowired
	private MateriaDao materiaDaoImp;
	@Autowired
	private ComisionDao comisionDaoImp;
	@Autowired
	private EstudianteDao estudianteDaoImp;

	@Override
	public List<MateriaJson> materiasAprobadasDeUsuario(String idUsuario)
			throws IdNumberFormatException, StudentNotExistenException {
		Estudiante estudiante;
		try {
			estudiante = this.get(new Long(idUsuario));
		} catch (NumberFormatException e) {
			throw new IdNumberFormatException();
		} catch (ObjectNotFoundinDBException e) {
			throw new StudentNotExistenException();
		}
		List<Carrera> carreras = estudiante.getCarrerasInscripto();
		List<Materia> materias = materiaDaoImp.getMateriasParaCarreras(carreras);
		return materias.stream().map(s -> new MateriaJson(s, this.carrerasACarrerasJson(s.getCarreras()),estudiante.estaAprobada(s))).collect(Collectors.toList());
	}
	
	public List<CarreraJson> carrerasACarrerasJson(List<Carrera> carreras){
		return carreras.stream().map(c -> new CarreraJson(c)).collect(Collectors.toList());
	}

	@Override
	public void actualizarMateriasAprobadasDeUsuario(String idUsuario, List<MateriaJson> estudiantesJson)
			throws IdNumberFormatException, StudentNotExistenException {
		Estudiante estudiante;
		try {
			estudiante = this.get(new Long(idUsuario));
		} catch (NumberFormatException e) {
			throw new IdNumberFormatException();
		} catch (ObjectNotFoundinDBException e) {
			throw new StudentNotExistenException();
		}
		List<MateriaJson> materiasAprobadas = estudiantesJson.stream().filter(estudianteJson -> estudianteJson.aprobada)
				.collect(Collectors.toList());
		if (!((int) materiasAprobadas.size() == estudiante.getMateriasAprobadas().size()
				&& estudiante.getMateriasAprobadas().containsAll(
						materiasAprobadas.stream().map(sA -> materiaDaoImp.get(sA.id)).collect(Collectors.toList())))) {
			// crear incidencia
			System.out.println("Crear incidencia");
			List<Materia> nuevasMateriasAprobadas = materiasAprobadas.stream()
					.map(subjectApproved -> materiaDaoImp.get(subjectApproved.id)).collect(Collectors.toList());
			estudiante.setMateriasAprobadas(nuevasMateriasAprobadas);
		}
	}

	@Override
	public List<MateriaJson> materiasDesaprobadasConComisionesDisponiblesDeUsuario(String idUsuario)
			throws IdNumberFormatException, StudentNotExistenException {
		Estudiante estudiante;
		try {
			estudiante = this.get(new Long(idUsuario));
		} catch (NumberFormatException e) {
			throw new IdNumberFormatException();
		} catch (ObjectNotFoundinDBException e) {
			throw new StudentNotExistenException();
		}
		Long idEncuesta = estudiante.getEncuesta().getId();
		List<Comision> comisiones = comisionDaoImp.getTodasLasComisionesDeMateriaEnEncuesta(idEncuesta);
		List<Materia> materiasAprobadas = estudiante.getMateriasAprobadas();
		List<Comision> comisionesDesaprobadas = comisiones.stream()
				.filter(comision -> !materiasAprobadas.contains(comision.getMateria())).collect(Collectors.toList());
		List<Materia> materiasDesaprobadas = comisionesDesaprobadas.stream().map(c -> c.getMateria()).distinct()
				.collect(Collectors.toList());
		List<Comision> comisionesRegistradas = estudiante.getRegistroComisiones();
		List<MateriaJson> materiaJson = materiasDesaprobadas.stream()
				.map(subject -> new MateriaJson(subject, false, comisionesRegistradas.stream()
						.filter(c -> c.getMateria().getId().equals(subject.getId())).collect(Collectors.toList())))
				.collect(Collectors.toList());

		materiaJson.stream()
				.map(sJ -> sJ.agregarComisionJson(
						comisionesDesaprobadas.stream().filter(c -> c.getMateria().getId().equals(sJ.id))
								.map(com -> new ComisionJson(com)).collect(Collectors.toList())

				)).collect(Collectors.toList());

		return materiaJson;
	}

	public Certificado getCertificado(String idEstudiante)
			throws StudentNotExistenException, DocumentException, IdNumberFormatException, CertificadoException {
		try {
			Estudiante estudiante = this.get(new Long(idEstudiante));
			Certificado certificado = new Certificado();
			certificado.setEstudiante(estudiante);
			certificado.generarPDF();
			return certificado;
		} catch (ObjectNotFoundinDBException e) {
			throw new StudentNotExistenException();
		} catch (NumberFormatException e) {
			throw new IdNumberFormatException();
		}
	}

	public void enviarCertificado(String idEstudiante) throws StudentNotExistenException, DocumentException,
			IdNumberFormatException, EmailException, CertificadoException {
		try {
			Estudiante estudiante = this.get(new Long(idEstudiante));
			Certificado certificado = new Certificado();
			certificado.setEstudiante(estudiante);
			certificado.setNombreArchivo(estudiante.getDni() + ".pdf");
			certificado.generarPDF();
			Mail mail = new Mail();
			mail.setFile(estudiante.getDni() + ".pdf");
			mail.sendConAdjunto(estudiante.getEmail(), "Certificado de PreInscripcion UNQ",
					"Estimado. Te enviamos tu certificado de preInscripcion.");
			File file = new File(estudiante.getDni() + ".pdf");
			file.delete();
		} catch (ObjectNotFoundinDBException e) {
			throw new StudentNotExistenException();
		} catch (NumberFormatException e) {
			throw new IdNumberFormatException();
		}
	}
	
	public Integer estudiantesPorComision(String idComision) {
		return estudianteDaoImp.getNroEstudiantesPorComision(new Long(idComision));
	}

}
