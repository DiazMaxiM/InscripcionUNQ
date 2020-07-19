package ar.edu.unq.inscripcionunq.spring.service;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.DocumentException;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.CarreraJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ComisionJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.IncidenciaJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.MateriaJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.TipoIncidenciaJson;
import ar.edu.unq.inscripcionunq.spring.dao.ComisionDao;
import ar.edu.unq.inscripcionunq.spring.dao.EncuestaDao;
import ar.edu.unq.inscripcionunq.spring.dao.EstudianteDao;
import ar.edu.unq.inscripcionunq.spring.dao.MateriaDao;
import ar.edu.unq.inscripcionunq.spring.exception.CertificadoException;
import ar.edu.unq.inscripcionunq.spring.exception.EmailInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.EstudianteNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.FormatoNumeroIdException;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectoNoEncontradoEnBDException;
import ar.edu.unq.inscripcionunq.spring.model.Carrera;
import ar.edu.unq.inscripcionunq.spring.model.Certificado;
import ar.edu.unq.inscripcionunq.spring.model.Comision;
import ar.edu.unq.inscripcionunq.spring.model.Email;
import ar.edu.unq.inscripcionunq.spring.model.Estudiante;
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

	@Autowired
	private EncuestaDao encuestaDaoImp;

	@Autowired
	private IncidenciaService incidenciaServiceImp;

	@Override
	public List<MateriaJson> materiasAprobadasDeUsuario(String idUsuario)
			throws FormatoNumeroIdException, EstudianteNoExisteException {
		Estudiante estudiante;
		try {
			estudiante = this.get(new Long(idUsuario));
		} catch (NumberFormatException e) {
			throw new FormatoNumeroIdException();
		} catch (ObjectoNoEncontradoEnBDException e) {
			throw new EstudianteNoExisteException();
		}
		List<Carrera> carreras = estudiante.getCarrerasInscripto();
		List<Materia> materias = materiaDaoImp.getMateriasParaCarreras(carreras);
		return materias.stream()
				.map(s -> new MateriaJson(s, this.carrerasACarrerasJson(s.getCarreras()), estudiante.estaAprobada(s)))
				.collect(Collectors.toList());
	}

	public List<CarreraJson> carrerasACarrerasJson(List<Carrera> carreras) {
		return carreras.stream().map(c -> new CarreraJson(c)).collect(Collectors.toList());
	}

	@Override
	public void actualizarMateriasAprobadasDeUsuario(String idUsuario, List<MateriaJson> estudiantesJson)
			throws FormatoNumeroIdException, EstudianteNoExisteException {
		Estudiante estudiante;
		try {
			estudiante = this.get(new Long(idUsuario));
		} catch (NumberFormatException e) {
			throw new FormatoNumeroIdException();
		} catch (ObjectoNoEncontradoEnBDException e) {
			throw new EstudianteNoExisteException();
		}
		List<MateriaJson> materiasAprobadas = estudiantesJson.stream().filter(estudianteJson -> estudianteJson.aprobada)
				.collect(Collectors.toList());
		if (!((int) materiasAprobadas.size() == estudiante.getMateriasAprobadas().size()
				&& estudiante.getMateriasAprobadas().containsAll(
						materiasAprobadas.stream().map(sA -> materiaDaoImp.get(sA.id)).collect(Collectors.toList())))) {
			List<Materia> nuevasMateriasAprobadas = materiasAprobadas.stream()
					.map(subjectApproved -> materiaDaoImp.get(subjectApproved.id)).collect(Collectors.toList());

			TipoIncidenciaJson otros = new TipoIncidenciaJson();
			otros.id = (long) 4;
			IncidenciaJson incidenciaJson = new IncidenciaJson();
			incidenciaJson.tipoIncidencia = otros;
			incidenciaJson.descripcion = "cambie materias aprobadas";
			incidenciaJson.emailDelReportante = estudiante.getEmail();

			try {
				incidenciaServiceImp.agregarIncidencia(incidenciaJson);
			} catch (EmailInvalidoException e) {

			}

			estudiante.setMateriasAprobadas(nuevasMateriasAprobadas);
		}
	}

	@Override
	public List<MateriaJson> materiasDesaprobadasConComisionesDisponiblesDeUsuario(String idUsuario)
			throws FormatoNumeroIdException, EstudianteNoExisteException {
		Estudiante estudiante;
		try {
			estudiante = this.get(new Long(idUsuario));
		} catch (NumberFormatException e) {
			throw new FormatoNumeroIdException();
		} catch (ObjectoNoEncontradoEnBDException e) {
			throw new EstudianteNoExisteException();
		}
		Long idEncuesta = estudiante.getEncuesta().getId();

		List<Comision> comisiones = comisionDaoImp.getTodasLasComisionesDeMateriaEnEncuesta(idEncuesta);

		List<Materia> materiasAprobadas = estudiante.getMateriasAprobadas();

		List<Comision> comisionesDesaprobadas = comisiones.stream()
				.filter(comision -> !materiasAprobadas.contains(comision.getMateria())).collect(Collectors.toList());

		List<Materia> materiasDesaprobadas = comisionesDesaprobadas.stream().map(c -> c.getMateria()).distinct()
				.collect(Collectors.toList());

		if (encuestaDaoImp.get(idEncuesta).isSolicitaPrerrequisitos()) {

			materiasDesaprobadas = materiasDesaprobadas.stream()
					.filter(materia -> materia.cumplePreRequisitos(materiasAprobadas)).collect(Collectors.toList());
		}
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
		Collections.sort(materiaJson);
		return materiaJson;
	}

	public Certificado getCertificado(String idEstudiante)
			throws EstudianteNoExisteException, DocumentException, FormatoNumeroIdException, CertificadoException {
		try {
			Estudiante estudiante = this.get(new Long(idEstudiante));
			Certificado certificado = new Certificado();
			certificado.setEstudiante(estudiante);
			certificado.generarPDF();
			return certificado;
		} catch (ObjectoNoEncontradoEnBDException e) {
			throw new EstudianteNoExisteException();
		} catch (NumberFormatException e) {
			throw new FormatoNumeroIdException();
		}
	}

	public void enviarCertificado(String idEstudiante) throws EstudianteNoExisteException, DocumentException,
			FormatoNumeroIdException, EmailException, CertificadoException {
		try {
			Estudiante estudiante = this.get(new Long(idEstudiante));
			Certificado certificado = new Certificado();
			certificado.setEstudiante(estudiante);
			certificado.setNombreArchivo(estudiante.getDni() + ".pdf");
			certificado.generarPDF();
			Email mail = new Email();
			mail.setFile(estudiante.getDni() + ".pdf");
			mail.sendConAdjunto(estudiante.getEmail(), "Constancia de preinscripción UNQ",
					"Estimado: te enviamos tu constancia de preinscripción.");
			File file = new File(estudiante.getDni() + ".pdf");
			file.delete();
		} catch (ObjectoNoEncontradoEnBDException e) {
			throw new EstudianteNoExisteException();
		} catch (NumberFormatException e) {
			throw new FormatoNumeroIdException();
		}
	}

	public Integer estudiantesPorComision(String idComision) {
		return estudianteDaoImp.getNroEstudiantesPorComision(new Long(idComision));
	}
}