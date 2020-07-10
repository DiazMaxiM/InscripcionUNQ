package ar.edu.unq.inscripcionunq.spring.service;

import java.io.IOException;
import java.util.List;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.EncuestaSistemaJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.IdJson;
import ar.edu.unq.inscripcionunq.spring.exception.ComisionNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.ConexionWebServiceException;
import ar.edu.unq.inscripcionunq.spring.exception.EncuestaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.EstudianteNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.ExisteEncuestaConMismoNombreException;
import ar.edu.unq.inscripcionunq.spring.exception.FormatoNumeroIdException;
import ar.edu.unq.inscripcionunq.spring.exception.NoExistenUsuariosEnEncuestaException;
import ar.edu.unq.inscripcionunq.spring.exception.OfertaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.PeriodoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.VariasComisionesDeUnaMateriaException;
import ar.edu.unq.inscripcionunq.spring.model.Encuesta;
import ar.edu.unq.inscripcionunq.spring.model.Estudiante;
import ar.edu.unq.inscripcionunq.spring.model.Reporte;

public interface EncuestaService extends GenericService<Encuesta> {

	public List<Encuesta> getTodasLasEncuestasActivasParaDni(String dni);

	public Estudiante getDatosDeUsuarioParaEncuesta(String dni, Long idEncuesta)
			throws NoExistenUsuariosEnEncuestaException;

	public void setComisionesSeleccionadas(String id, List<IdJson> idsJson) throws FormatoNumeroIdException,
			EstudianteNoExisteException, ComisionNoExisteException, VariasComisionesDeUnaMateriaException;

	public Boolean puedeGenerarPDF(String dni, Long id);

	public void notificarALosEstudianteCambioComision(Long id) throws ComisionNoExisteException;

	public List<EncuestaSistemaJson> getEncuestaJson();

	public void crearNuevaEncuesta(EncuestaSistemaJson encuestaJson)
			throws FormatoNumeroIdException, PeriodoInvalidoException, ConexionWebServiceException,
			EncuestaNoExisteException, OfertaNoExisteException, ExisteEncuestaConMismoNombreException;

	public void actualizarEncuesta(EncuestaSistemaJson encuestaJson) throws FormatoNumeroIdException,
			PeriodoInvalidoException, EncuestaNoExisteException, ExisteEncuestaConMismoNombreException;

	public void asociarOfertasParaEncuesta(String idEncuesta, List<IdJson> idsJson)
			throws FormatoNumeroIdException, EncuestaNoExisteException, OfertaNoExisteException;

	public Reporte getReporte(String idEncuesta, String tipoEncuesta) throws FormatoNumeroIdException, IOException;

	public void guardarArchivo(String archivo) throws IOException;

}
