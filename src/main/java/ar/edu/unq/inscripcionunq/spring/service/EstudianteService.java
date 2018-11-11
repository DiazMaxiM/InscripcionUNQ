package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import org.apache.commons.mail.EmailException;

import com.itextpdf.text.DocumentException;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.MateriaJson;
import ar.edu.unq.inscripcionunq.spring.exception.CertificadoException;
import ar.edu.unq.inscripcionunq.spring.exception.FormatoNumeroIdException;
import ar.edu.unq.inscripcionunq.spring.exception.EstudianteNoExisteException;
import ar.edu.unq.inscripcionunq.spring.model.Certificado;
import ar.edu.unq.inscripcionunq.spring.model.Estudiante;

public interface EstudianteService extends GenericService<Estudiante> {

	List<MateriaJson> materiasAprobadasDeUsuario(String idUsuario)
			throws FormatoNumeroIdException, EstudianteNoExisteException;

	void actualizarMateriasAprobadasDeUsuario(String idUsuario, List<MateriaJson> estudiantesJson)
			throws FormatoNumeroIdException, EstudianteNoExisteException;

	List<MateriaJson> materiasDesaprobadasConComisionesDisponiblesDeUsuario(String idUsuario)
			throws FormatoNumeroIdException, EstudianteNoExisteException;

	Certificado getCertificado(String idEstudiante)
			throws EstudianteNoExisteException, DocumentException, FormatoNumeroIdException, CertificadoException;

	public void enviarCertificado(String idEstudiante) throws EstudianteNoExisteException, DocumentException,
			FormatoNumeroIdException, EmailException, CertificadoException;
	
	public Integer estudiantesPorComision(String idComision);

}