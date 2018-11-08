package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.EncuestaSistemaJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.IdJson;
import ar.edu.unq.inscripcionunq.spring.exception.CommissionNotExistenException;
import ar.edu.unq.inscripcionunq.spring.exception.ConexionWebServiceException;
import ar.edu.unq.inscripcionunq.spring.exception.EncuestaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.ExisteEncuestaConMismoNombreException;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.OfertaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.PeriodoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.StudentNotExistenException;
import ar.edu.unq.inscripcionunq.spring.exception.UserInPollNotFoundException;
import ar.edu.unq.inscripcionunq.spring.exception.VariasComisionesDeUnaMateriaException;
import ar.edu.unq.inscripcionunq.spring.model.Encuesta;
import ar.edu.unq.inscripcionunq.spring.model.Estudiante;
import ar.edu.unq.inscripcionunq.spring.model.Reporte;

public interface EncuestaService extends GenericService<Encuesta> {

	public List<Encuesta> getTodasLasEncuestasActivasParaDni(String dni);

	public Estudiante getDatosDeUsuarioParaEncuesta(String dni, Long idPoll) throws UserInPollNotFoundException;

	public void setComisionesSeleccionadas(String id, List<IdJson> idsJson) throws IdNumberFormatException,
			StudentNotExistenException, CommissionNotExistenException, VariasComisionesDeUnaMateriaException;

	public Boolean puedeGenerarPDF(String dni, Long id);

	public void notificarALosEstudianteCambioComision(Long id) throws CommissionNotExistenException;

	public List<EncuestaSistemaJson> getEncuestaJson();

	public void crearNuevaEncuesta(EncuestaSistemaJson encuestaJson)
			throws IdNumberFormatException, PeriodoInvalidoException, ConexionWebServiceException,
			EncuestaNoExisteException, OfertaNoExisteException, ExisteEncuestaConMismoNombreException;

	public void actualizarEncuesta(EncuestaSistemaJson encuestaJson) throws IdNumberFormatException,
			PeriodoInvalidoException, EncuestaNoExisteException, ExisteEncuestaConMismoNombreException;

	public void asociarOfertasParaEncuesta(String idEncuesta, List<IdJson> idsJson)
			throws IdNumberFormatException, EncuestaNoExisteException, OfertaNoExisteException;
	
	public Reporte getReporte(String idEncuesta, String tipoEncuesta) throws IdNumberFormatException;

}
