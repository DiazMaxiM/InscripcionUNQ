package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.EncuestaJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.IdJson;
import ar.edu.unq.inscripcionunq.spring.exception.CommissionNotExistenException;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.StudentNotExistenException;
import ar.edu.unq.inscripcionunq.spring.exception.UserInPollNotFoundException;
import ar.edu.unq.inscripcionunq.spring.exception.VariasComisionesDeUnaMateriaException;
import ar.edu.unq.inscripcionunq.spring.model.Encuesta;
import ar.edu.unq.inscripcionunq.spring.model.Estudiante;

public interface EncuestaService extends GenericService<Encuesta> {

	public List<Encuesta> getTodasLasEncuestasActivasParaDni(String dni);

	public Estudiante getDatosDeUsuarioParaEncuesta(String dni, Long idPoll) throws UserInPollNotFoundException;

	public void setComisionesSeleccionadas(String id, List<IdJson> idsJson) throws IdNumberFormatException,
			StudentNotExistenException, CommissionNotExistenException, VariasComisionesDeUnaMateriaException;

	public Boolean puedeGenerarPDF(String dni, Long id);

	public List<EncuestaJson> getEncuestaJson();

}
