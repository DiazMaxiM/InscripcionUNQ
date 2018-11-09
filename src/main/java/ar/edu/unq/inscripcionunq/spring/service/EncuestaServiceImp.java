package ar.edu.unq.inscripcionunq.spring.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.EncuestaSistemaJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.IdJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.OfertaAcademicaJson;
import ar.edu.unq.inscripcionunq.spring.dao.EncuestaDao;
import ar.edu.unq.inscripcionunq.spring.exception.CommissionNotExistenException;
import ar.edu.unq.inscripcionunq.spring.exception.ConexionWebServiceException;
import ar.edu.unq.inscripcionunq.spring.exception.EncuestaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.ExisteEncuestaConMismoNombreException;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectNotFoundinDBException;
import ar.edu.unq.inscripcionunq.spring.exception.OfertaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.PeriodoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.StudentNotExistenException;
import ar.edu.unq.inscripcionunq.spring.exception.UserInPollNotFoundException;
import ar.edu.unq.inscripcionunq.spring.exception.VariasComisionesDeUnaMateriaException;
import ar.edu.unq.inscripcionunq.spring.model.Comision;
import ar.edu.unq.inscripcionunq.spring.model.Encuesta;
import ar.edu.unq.inscripcionunq.spring.model.EnvioMailsMasivos;
import ar.edu.unq.inscripcionunq.spring.model.Estudiante;
import ar.edu.unq.inscripcionunq.spring.model.OfertaAcademica;
import ar.edu.unq.inscripcionunq.spring.model.Periodo;
import ar.edu.unq.inscripcionunq.spring.model.Reporte;
import ar.edu.unq.inscripcionunq.spring.model.TipoReporte;

@Service
@Transactional
public class EncuestaServiceImp extends GenericServiceImp<Encuesta> implements EncuestaService {

	@Autowired
	EstudianteService estudianteServiceImp;
	@Autowired
	ComisionService comisionServiceImp;
	@Autowired
	PeriodoService periodoServiceImp;
	@Autowired
	OfertaAcademicaService ofertaServiceImp;
	@Autowired
	private WebService webService;
	@Autowired
	private EncuestaDao encuestaDaoImp;

	@Override
	@Transactional
	public List<Encuesta> getTodasLasEncuestasActivasParaDni(String dni) {
		return ((EncuestaDao) genericDao).getTodasLasEncuestasActivasParaDni(dni);
	}

	@Override
	@Transactional
	public Estudiante getDatosDeUsuarioParaEncuesta(String dni, Long idEncuesta) throws UserInPollNotFoundException {
		return ((EncuestaDao) genericDao).getDatosDeUsuarioParaEncuesta(dni, idEncuesta);
	}

	@Override
	@Transactional
	public Boolean puedeGenerarPDF(String dni, Long idEncuesta) {
		try {
			return !this.getDatosDeUsuarioParaEncuesta(dni, idEncuesta).getRegistroComisiones().isEmpty();
		} catch (UserInPollNotFoundException e) {
		}
		return false;
	}

	@Override
	public void setComisionesSeleccionadas(String id, List<IdJson> idsJson) throws IdNumberFormatException,
			StudentNotExistenException, CommissionNotExistenException, VariasComisionesDeUnaMateriaException {
		Estudiante estudiante;
		try {
			estudiante = estudianteServiceImp.get(new Long(id));
		} catch (NumberFormatException e) {
			throw new IdNumberFormatException();
		} catch (ObjectNotFoundinDBException e) {
			throw new StudentNotExistenException();
		}
		estudiante.eliminarTodasLasComisionesInscripto();
		for (IdJson idJson : idsJson) {
			Comision comision;
			try {
				comision = comisionServiceImp.get(new Long(idJson.id));
			} catch (NumberFormatException e) {
				throw new IdNumberFormatException();
			} catch (ObjectNotFoundinDBException e) {
				throw new CommissionNotExistenException();

			}
			estudiante.agregarRegistroComisiones(comision);
		}
		estudianteServiceImp.update(estudiante);
	}

	@Override
	public void notificarALosEstudianteCambioComision(Long idComision) throws CommissionNotExistenException {
		try {
			Comision comision = this.comisionServiceImp.get(idComision);
		} catch (ObjectNotFoundinDBException e1) {
			throw new CommissionNotExistenException();
		}
		List<Encuesta> encuestas = ((EncuestaDao) genericDao).getEncuestasDeUnaComision(idComision);
		List<String> mailsParaNotificar = new ArrayList<String>();
		for (Encuesta encuesta : encuestas) {
			mailsParaNotificar.addAll(encuesta.getEstudiantes().stream()
					.filter(estudiante -> !estudiante.getRegistroComisiones().isEmpty()).map(e -> e.getEmail())
					.collect(Collectors.toList()));
		}
		EnvioMailsMasivos mails = new EnvioMailsMasivos();
		mails.setAsunto("Cambio de comisión");
		mails.setEmails(mailsParaNotificar);
		mails.setMensaje("Cambió la comisión");
		mails.run();
	}

	public List<EncuestaSistemaJson> getEncuestaJson() {
		List<Encuesta> encuestas = this.list();
		return encuestas.stream().map(m -> this.crearEncuestaJson(m)).collect(Collectors.toList());
	}
	
	private EncuestaSistemaJson crearEncuestaJson(Encuesta encuesta){
		Number nroDeAlumnos = this.encuestaDaoImp.nroDeAlumnosQueCompletaronEncuesta(encuesta.getId());
		EncuestaSistemaJson encuestaSistemaJson = new EncuestaSistemaJson(encuesta);
		encuestaSistemaJson.nroDeAlumnosQueCompletaronEncuesta = nroDeAlumnos;
		return encuestaSistemaJson;
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void crearNuevaEncuesta(EncuestaSistemaJson encuestaJson)
			throws IdNumberFormatException, PeriodoInvalidoException, ConexionWebServiceException,
			EncuestaNoExisteException, OfertaNoExisteException, ExisteEncuestaConMismoNombreException {
		Encuesta encuesta = this.mapearEncuestaDesdeJson(encuestaJson);
		List<OfertaAcademica> ofertasParaEncuesta = this.mapearOfertasDesdeJson(encuestaJson.ofertasAcademicas);
		encuesta.setOfertasAcademicas(ofertasParaEncuesta);
		try {
			this.save(encuesta);
		} catch (ConstraintViolationException e) {
			throw new ExisteEncuestaConMismoNombreException();
		}

		webService.importarEstudiantes(encuesta.getId());
	}

	private Encuesta mapearEncuestaDesdeJson(EncuestaSistemaJson encuestaJson)
			throws IdNumberFormatException, PeriodoInvalidoException {
		Periodo periodo;
		try {
			periodo = periodoServiceImp.get(encuestaJson.periodo.id);
		} catch (NumberFormatException e) {
			throw new IdNumberFormatException();
		} catch (ObjectNotFoundinDBException e) {
			throw new PeriodoInvalidoException();

		}
		LocalDateTime horaComienzo = LocalDateTime.of(encuestaJson.fechaComienzo.anho, encuestaJson.fechaComienzo.mes,
				encuestaJson.fechaComienzo.dia, encuestaJson.fechaComienzo.horario.hour,
				encuestaJson.fechaComienzo.horario.minute);
		LocalDateTime horaFin = LocalDateTime.of(encuestaJson.fechaFin.anho, encuestaJson.fechaFin.mes,
				encuestaJson.fechaFin.dia, encuestaJson.fechaFin.horario.hour, encuestaJson.fechaFin.horario.minute);
		return new Encuesta(encuestaJson.nombre, horaComienzo, horaFin, periodo);
	}

	@Override
	public void actualizarEncuesta(EncuestaSistemaJson encuestaJson) throws IdNumberFormatException,
			PeriodoInvalidoException, EncuestaNoExisteException, ExisteEncuestaConMismoNombreException {
		Encuesta encuestaActualizada = this.mapearEncuestaDesdeJson(encuestaJson);
		try {
			Encuesta encuestaOriginal = this.get(encuestaJson.id);
			if (!encuestaOriginal.getNombre().equals(encuestaActualizada.getNombre())) {
				validarSiExisteEncuestaConNombre(encuestaActualizada.getNombre());
			}
			encuestaOriginal.actualizarDatos(encuestaActualizada);
			this.save(encuestaOriginal);
		} catch (NumberFormatException e) {
			throw new IdNumberFormatException();
		} catch (ObjectNotFoundinDBException e) {
			throw new EncuestaNoExisteException();
		}
	}

	private void validarSiExisteEncuestaConNombre(String nombre) throws ExisteEncuestaConMismoNombreException {
		Encuesta encuesta = encuestaDaoImp.getEncuestaConNombre(nombre);
		if (encuesta != null) {
			throw new ExisteEncuestaConMismoNombreException();
		}
	}

	@Override
	public void asociarOfertasParaEncuesta(String idEncuesta, List<IdJson> idsJson)
			throws IdNumberFormatException, EncuestaNoExisteException, OfertaNoExisteException {
		Encuesta encuesta;
		List<OfertaAcademica> ofertas = new ArrayList<>();
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

	private List<OfertaAcademica> mapearOfertasDesdeJson(List<OfertaAcademicaJson> ofertasAcademicas)
			throws IdNumberFormatException, OfertaNoExisteException {
		List<OfertaAcademica> ofertas = new ArrayList<>();
		for (OfertaAcademicaJson oferta : ofertasAcademicas) {
			try {
				ofertas.add(ofertaServiceImp.get(new Long(oferta.id)));
			} catch (NumberFormatException e) {
				throw new IdNumberFormatException();
			} catch (ObjectNotFoundinDBException e) {
				throw new OfertaNoExisteException();
			}
		}
		return ofertas;
	}

	public Reporte getReporte(String idEncuesta, String tipoReporte) throws IdNumberFormatException {
		Encuesta encuesta = new Encuesta();
		try {
			encuesta = encuestaDaoImp.get(new Long(idEncuesta));
		} catch (NumberFormatException e) {
			throw new IdNumberFormatException();
		}
    
		Reporte reporte = new Reporte(encuesta, TipoReporte.valueOf(tipoReporte));
		try {
			reporte.generarReporte();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reporte;
	}
}