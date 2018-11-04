package ar.edu.unq.inscripcionunq.spring.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.EncuestaJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.EncuestaSistemaJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.IdJson;
import ar.edu.unq.inscripcionunq.spring.dao.EncuestaDao;
import ar.edu.unq.inscripcionunq.spring.dao.OfertaAcademicaDao;
import ar.edu.unq.inscripcionunq.spring.exception.CommissionNotExistenException;
import ar.edu.unq.inscripcionunq.spring.exception.ConexionWebServiceException;
import ar.edu.unq.inscripcionunq.spring.exception.EncuestaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectNotFoundinDBException;
import ar.edu.unq.inscripcionunq.spring.exception.OfertaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.PeriodoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.StudentNotExistenException;
import ar.edu.unq.inscripcionunq.spring.exception.UserInPollNotFoundException;
import ar.edu.unq.inscripcionunq.spring.exception.VariasComisionesDeUnaMateriaException;
import ar.edu.unq.inscripcionunq.spring.model.Comision;
import ar.edu.unq.inscripcionunq.spring.model.Encuesta;
import ar.edu.unq.inscripcionunq.spring.model.Estudiante;
import ar.edu.unq.inscripcionunq.spring.model.OfertaAcademica;
import ar.edu.unq.inscripcionunq.spring.model.Periodo;

@Service
@Transactional
public class EncuestaServiceImp extends GenericServiceImp<Encuesta> implements EncuestaService {

	@Autowired
	EstudianteService studentServiceImp;
	@Autowired
	ComisionService commissionServiceImp;
	@Autowired
	PeriodoService periodoServiceImp;
	@Autowired
	OfertaAcademicaService ofertaServiceImp;
	@Autowired
	private WebService webService;


	@Override
	@Transactional
	public List<Encuesta> getTodasLasEncuestasActivasParaDni(String dni) {
		return ((EncuestaDao) genericDao).getTodasLasEncuestasActivasParaDni(dni);
	}

	@Override
	@Transactional
	public Estudiante getDatosDeUsuarioParaEncuesta(String dni, Long idPoll) throws UserInPollNotFoundException {
		return ((EncuestaDao) genericDao).getDatosDeUsuarioParaEncuesta(dni, idPoll);
	}

	@Override
	@Transactional
	public Boolean puedeGenerarPDF(String dni, Long idPoll) {
		try {
			return !this.getDatosDeUsuarioParaEncuesta(dni, idPoll).getRegistroComisiones().isEmpty();
		} catch (UserInPollNotFoundException e) {
		}
		return false;
	}

	@Override
	public void setComisionesSeleccionadas(String id, List<IdJson> idsJson) throws IdNumberFormatException,
			StudentNotExistenException, CommissionNotExistenException, VariasComisionesDeUnaMateriaException {
		Estudiante estudiante;
		try {
			estudiante = studentServiceImp.get(new Long(id));
		} catch (NumberFormatException e) {
			throw new IdNumberFormatException();
		} catch (ObjectNotFoundinDBException e) {
			throw new StudentNotExistenException();
		}
		estudiante.eliminarTodasLasComisionesInscripto();
		for (IdJson idJson : idsJson) {
			Comision comision;
			try {
				comision = commissionServiceImp.get(new Long(idJson.id));
			} catch (NumberFormatException e) {
				throw new IdNumberFormatException();
			} catch (ObjectNotFoundinDBException e) {
				throw new CommissionNotExistenException();

			}
			estudiante.agregarRegistroComisiones(comision);
		}
		studentServiceImp.update(estudiante);
	}

	@Override
	public List<EncuestaSistemaJson> getEncuestaJson() {
		List<Encuesta> encuestas = this.list();
		return encuestas.stream().map(m -> new EncuestaSistemaJson(m)).collect(Collectors.toList());
	}

	@Override
	public void crearNuevaEncuesta(EncuestaSistemaJson encuestaJson) throws IdNumberFormatException, PeriodoInvalidoException, ConexionWebServiceException, EncuestaNoExisteException {
		Encuesta encuesta = this.mapearEncuestaDedeJson(encuestaJson);
		this.save(encuesta);
		webService.importarEstudiantes(encuesta.getId());
		
		
	}
	private Encuesta mapearEncuestaDedeJson(EncuestaSistemaJson encuestaJson) throws IdNumberFormatException, PeriodoInvalidoException {
		Periodo periodo;
		try {
			periodo = periodoServiceImp.get(encuestaJson.periodo.id);
		} catch (NumberFormatException e) {
			throw new IdNumberFormatException();
		} catch (ObjectNotFoundinDBException e) {
			throw new PeriodoInvalidoException();

		}
		LocalDateTime horaComienzo = LocalDateTime.of(encuestaJson.fechaComienzo.anho, encuestaJson.fechaComienzo.mes, encuestaJson.fechaComienzo.dia, encuestaJson.fechaComienzo.horario.hour, encuestaJson.fechaComienzo.horario.minute);
		LocalDateTime horaFin =LocalDateTime.of(encuestaJson.fechaFin.anho, encuestaJson.fechaFin.mes, encuestaJson.fechaFin.dia, encuestaJson.fechaFin.horario.hour, encuestaJson.fechaFin.horario.minute);
		return  new Encuesta(encuestaJson.nombre,horaComienzo, horaFin, periodo);
	}

	@Override
	public void actualizarEncuesta(EncuestaSistemaJson encuestaJson) throws IdNumberFormatException, PeriodoInvalidoException, EncuestaNoExisteException {
		Encuesta encuestaActualizada = this.mapearEncuestaDedeJson(encuestaJson);
		try {
			Encuesta encuestaOriginal = this.get(encuestaJson.id);
			encuestaOriginal.actualizarDatos(encuestaActualizada);
			this.save(encuestaOriginal);
		} catch (NumberFormatException e) {
			throw new IdNumberFormatException();
		} catch (ObjectNotFoundinDBException e) {
			throw new EncuestaNoExisteException();

		}
		
	}

	@Override
	public void asociarOfertasParaEncuesta(String idEncuesta, List<IdJson> idsJson) throws IdNumberFormatException, EncuestaNoExisteException, OfertaNoExisteException {
		Encuesta encuesta;
		List <OfertaAcademica> ofertas = new ArrayList <>();
		try {
			encuesta = this.get(new Long(idEncuesta));
		} catch (NumberFormatException e) {
			throw new IdNumberFormatException();
		} catch (ObjectNotFoundinDBException e) {
			throw new EncuestaNoExisteException();
		}
		for (IdJson idJson : idsJson) {
			try {
				ofertas.add(ofertaServiceImp.get(new Long(idJson.id)));
			} catch (NumberFormatException e) {
				throw new IdNumberFormatException();
			} catch (ObjectNotFoundinDBException e) {
				throw new OfertaNoExisteException();
			} 
			encuesta.setOfertasAcademicas(ofertas);
		}
		this.save(encuesta);
		
		
	}

}
