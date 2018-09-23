package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import org.apache.commons.mail.EmailException;

import com.itextpdf.text.DocumentException;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.MateriaJson;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.StudentNotExistenException;
import ar.edu.unq.inscripcionunq.spring.model.Certificado;
import ar.edu.unq.inscripcionunq.spring.model.Estudiante;

public interface EstudianteService extends GenericService<Estudiante> {

	List<MateriaJson> materiasAprobadasDeUsuario(String idUsuario) throws IdNumberFormatException, StudentNotExistenException;

	void actualizarMateriasAprobadasDeUsuario(String idUsuario, List<MateriaJson> estudiantesJson)
			throws IdNumberFormatException, StudentNotExistenException;

	List<MateriaJson> materiasDesaprobadasConComisionesDisponiblesDeUsuario(String idUsuario)
			throws IdNumberFormatException, StudentNotExistenException;

	Certificado getCertificado(String idEstudiante)
			throws StudentNotExistenException, DocumentException, IdNumberFormatException;

	public void enviarCertificado(String idEstudiante)
			throws StudentNotExistenException, DocumentException, IdNumberFormatException, EmailException;

}
