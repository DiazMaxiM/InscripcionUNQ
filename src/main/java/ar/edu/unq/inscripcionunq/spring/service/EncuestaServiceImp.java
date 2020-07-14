package ar.edu.unq.inscripcionunq.spring.service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.CarreraWebServiceJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.EncuestaSistemaJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.EstudianteEnEncuestaJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.EstudianteJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.EstudianteWebServiceJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ExceptionJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.IdJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.IncidenciaJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.OfertaAcademicaJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.TipoIncidenciaJson;
import ar.edu.unq.inscripcionunq.spring.dao.EncuestaDao;
import ar.edu.unq.inscripcionunq.spring.exception.ApellidoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.CantidadMateriasInscripcionSuperadaException;
import ar.edu.unq.inscripcionunq.spring.exception.ComisionNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.ConexionWebServiceException;
import ar.edu.unq.inscripcionunq.spring.exception.EmailInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.EncuestaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.EstudianteNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.ExisteEncuestaConMismoNombreException;
import ar.edu.unq.inscripcionunq.spring.exception.FormatoNumeroIdException;
import ar.edu.unq.inscripcionunq.spring.exception.MateriaNoCumplePrerrequisitoException;
import ar.edu.unq.inscripcionunq.spring.exception.NoExistenUsuariosEnEncuestaException;
import ar.edu.unq.inscripcionunq.spring.exception.NombreInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectoNoEncontradoEnBDException;
import ar.edu.unq.inscripcionunq.spring.exception.OfertaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.PeriodoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.VariasComisionesDeUnaMateriaException;
import ar.edu.unq.inscripcionunq.spring.model.Carrera;
import ar.edu.unq.inscripcionunq.spring.model.Comision;
import ar.edu.unq.inscripcionunq.spring.model.Encuesta;
import ar.edu.unq.inscripcionunq.spring.model.EnvioMailsMasivos;
import ar.edu.unq.inscripcionunq.spring.model.Estudiante;
import ar.edu.unq.inscripcionunq.spring.model.OfertaAcademica;
import ar.edu.unq.inscripcionunq.spring.model.Periodo;
import ar.edu.unq.inscripcionunq.spring.model.Reporte;
import ar.edu.unq.inscripcionunq.spring.model.TipoReporte;
import ar.edu.unq.inscripcionunq.spring.validacion.Validacion;

@Service
@Transactional
public class EncuestaServiceImp extends GenericServiceImp<Encuesta> implements EncuestaService {

	@Autowired
	EstudianteService estudianteServiceImp;

	@Autowired
	CarreraService carreraServiceImp;

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
	public Estudiante getDatosDeUsuarioParaEncuesta(String dni, Long idEncuesta)
			throws NoExistenUsuariosEnEncuestaException {
		return ((EncuestaDao) genericDao).getDatosDeUsuarioParaEncuesta(dni, idEncuesta);
	}

	@Override
	@Transactional
	public Boolean puedeGenerarPDF(String dni, Long idEncuesta) {
		try {
			return !this.getDatosDeUsuarioParaEncuesta(dni, idEncuesta).getRegistroComisiones().isEmpty();
		} catch (NoExistenUsuariosEnEncuestaException e) {
		}
		return false;
	}

	@Override
	public void setComisionesSeleccionadas(String id, List<IdJson> idsJson) throws FormatoNumeroIdException,
			EstudianteNoExisteException, ComisionNoExisteException, VariasComisionesDeUnaMateriaException,
			MateriaNoCumplePrerrequisitoException, CantidadMateriasInscripcionSuperadaException {
		Estudiante estudiante;
		try {
			estudiante = estudianteServiceImp.get(new Long(id));
		} catch (NumberFormatException e) {
			throw new FormatoNumeroIdException();
		} catch (ObjectoNoEncontradoEnBDException e) {
			throw new EstudianteNoExisteException();
		}
		estudiante.eliminarTodasLasComisionesInscripto();
		for (IdJson idJson : idsJson) {
			Comision comision;
			try {
				comision = comisionServiceImp.get(new Long(idJson.id));
			} catch (NumberFormatException e) {
				throw new FormatoNumeroIdException();
			} catch (ObjectoNoEncontradoEnBDException e) {
				throw new ComisionNoExisteException();

			}
			if (idsJson.size() > estudiante.getEncuesta().getLimilteMaxMaterias()) {
				throw new CantidadMateriasInscripcionSuperadaException(idsJson.size(),
						estudiante.getEncuesta().getLimilteMaxMaterias());

			}
			if (estudiante.getEncuesta().isSolicitaPrerrequisitos()) {
				if (!comision.getMateria().cumplePreRequisitos(estudiante.getMateriasAprobadas())) {
					throw new MateriaNoCumplePrerrequisitoException(comision.getMateria());
				}
			}
			estudiante.agregarRegistroComisiones(comision);
		}
		estudianteServiceImp.update(estudiante);
	}

	@Override
	public void notificarALosEstudianteCambioComision(Long idComision) throws ComisionNoExisteException {
		try {
			Comision comision = this.comisionServiceImp.get(idComision);
		} catch (ObjectoNoEncontradoEnBDException e1) {
			throw new ComisionNoExisteException();
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
		mails.setMensaje("Cambió de horario la comisión");
		mails.run();
	}

	public List<EncuestaSistemaJson> getEncuestaJson() {
		List<Encuesta> encuestas = this.list();
		return encuestas.stream().map(m -> this.crearEncuestaJson(m)).collect(Collectors.toList());
	}

	private EncuestaSistemaJson crearEncuestaJson(Encuesta encuesta) {
		Number nroDeAlumnos = this.encuestaDaoImp.nroDeAlumnosQueCompletaronEncuesta(encuesta.getId());
		EncuestaSistemaJson encuestaSistemaJson = new EncuestaSistemaJson(encuesta);
		encuestaSistemaJson.nroDeAlumnosQueCompletaronEncuesta = nroDeAlumnos;
		return encuestaSistemaJson;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void crearNuevaEncuesta(EncuestaSistemaJson encuestaJson)
			throws FormatoNumeroIdException, PeriodoInvalidoException, ConexionWebServiceException,
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
			throws FormatoNumeroIdException, PeriodoInvalidoException {
		Periodo periodo;
		try {
			periodo = periodoServiceImp.get(encuestaJson.periodo.id);
		} catch (NumberFormatException e) {
			throw new FormatoNumeroIdException();
		} catch (ObjectoNoEncontradoEnBDException e) {
			throw new PeriodoInvalidoException();
		}
		LocalDateTime horaComienzo = LocalDateTime.of(encuestaJson.fechaComienzo.anho, encuestaJson.fechaComienzo.mes,
				encuestaJson.fechaComienzo.dia, encuestaJson.fechaComienzo.horario.hora,
				encuestaJson.fechaComienzo.horario.minutos);
		LocalDateTime horaFin = LocalDateTime.of(encuestaJson.fechaFin.anho, encuestaJson.fechaFin.mes,
				encuestaJson.fechaFin.dia, encuestaJson.fechaFin.horario.hora, encuestaJson.fechaFin.horario.minutos);
		return new Encuesta(encuestaJson.nombre, horaComienzo, horaFin, periodo, encuestaJson.limiteMaxMaterias,
				encuestaJson.solicitaPrerrequisitos);
	}

	@Override
	public void actualizarEncuesta(EncuestaSistemaJson encuestaJson) throws FormatoNumeroIdException,
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
			throw new FormatoNumeroIdException();
		} catch (ObjectoNoEncontradoEnBDException e) {
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
			throws FormatoNumeroIdException, EncuestaNoExisteException, OfertaNoExisteException {
		Encuesta encuesta;
		List<OfertaAcademica> ofertas = new ArrayList<>();
		try {
			encuesta = this.get(new Long(idEncuesta));
		} catch (NumberFormatException e) {
			throw new FormatoNumeroIdException();
		} catch (ObjectoNoEncontradoEnBDException e) {
			throw new EncuestaNoExisteException();
		}
		for (IdJson idJson : idsJson) {
			try {
				ofertas.add(ofertaServiceImp.get(new Long(idJson.id)));
			} catch (NumberFormatException e) {
				throw new FormatoNumeroIdException();
			} catch (ObjectoNoEncontradoEnBDException e) {
				throw new OfertaNoExisteException();
			}
			encuesta.setOfertasAcademicas(ofertas);
		}
		this.save(encuesta);
	}

	private List<OfertaAcademica> mapearOfertasDesdeJson(List<OfertaAcademicaJson> ofertasAcademicas)
			throws FormatoNumeroIdException, OfertaNoExisteException {
		List<OfertaAcademica> ofertas = new ArrayList<>();
		for (OfertaAcademicaJson oferta : ofertasAcademicas) {
			try {
				ofertas.add(ofertaServiceImp.get(new Long(oferta.id)));
			} catch (NumberFormatException e) {
				throw new FormatoNumeroIdException();
			} catch (ObjectoNoEncontradoEnBDException e) {
				throw new OfertaNoExisteException();
			}
		}
		return ofertas;
	}

	public Reporte getReporte(String idEncuesta, String tipoReporte) throws FormatoNumeroIdException, IOException {
		Encuesta encuesta = new Encuesta();
		try {
			encuesta = encuestaDaoImp.get(new Long(idEncuesta));
		} catch (NumberFormatException e) {
			throw new FormatoNumeroIdException();
		}

		Reporte reporte = new Reporte(encuesta, TipoReporte.valueOf(tipoReporte));
		reporte.generarReporte();

		return reporte;
	}

	@Override
	public void guardarArchivo(String archivo) throws IOException {
		System.out.println(archivo);
		FileWriter myWriter = new FileWriter("prueba.json");
		myWriter.write(archivo);
		myWriter.close();

	}

	@Override
	public List<EstudianteEnEncuestaJson> getEstudiantesDeEncuesta(String idEncuesta) throws FormatoNumeroIdException {
		Encuesta encuesta = new Encuesta();
		try {
			encuesta = encuestaDaoImp.get(new Long(idEncuesta));
		} catch (NumberFormatException e) {
			throw new FormatoNumeroIdException();
		}
		List<Estudiante> estudiantes = encuesta.getEstudiantes();
		return estudiantes.stream().map(estudiante -> new EstudianteEnEncuestaJson(estudiante)).collect(Collectors.toList());
	}

	@Override
	public void agregarNuevoEstudianteEnEncuesta(String idEncuesta, EstudianteWebServiceJson estudianteWebServiceJson) throws NombreInvalidoException, EmailInvalidoException, ApellidoInvalidoException, FormatoNumeroIdException {

		Encuesta encuesta;

		try {
			encuesta = encuestaDaoImp.get(new Long(idEncuesta));
		} catch (NumberFormatException e) {
			throw new FormatoNumeroIdException();
		}
		Estudiante estudianteNuevo = new Estudiante(estudianteWebServiceJson.datos_personales.nombre,
				estudianteWebServiceJson.datos_personales.apellido, estudianteWebServiceJson.datos_personales.dni,
				estudianteWebServiceJson.datos_personales.email);
		for (CarreraWebServiceJson carreraWebServiceJson : estudianteWebServiceJson.carreras) {
			estudianteNuevo
					.agregarInscripcionACarrera(carreraServiceImp.getCarreraPorCodigo(carreraWebServiceJson.codigo));
		}
		Validacion.validarEstudiante(estudianteNuevo);
		encuesta.agregarEstudiante(estudianteNuevo);
	}

	@Override
	public void actualizarEstudianteEnEncuesta(EstudianteWebServiceJson estudianteJson) throws NombreInvalidoException, ApellidoInvalidoException, EstudianteNoExisteException, EmailInvalidoException {
		
		Estudiante estudianteRecibido = new Estudiante(estudianteJson.datos_personales.nombre, estudianteJson.datos_personales.apellido,
				estudianteJson.datos_personales.dni, estudianteJson.datos_personales.email);

		Estudiante estudiante;
		try {
			estudiante = estudianteServiceImp.get(new Long(estudianteJson.id));
		} catch (ObjectoNoEncontradoEnBDException e) {
			throw new EstudianteNoExisteException();
		}
		try {
				estudiante.actualizarEstudiante(estudianteRecibido);
				List<Carrera> carrerasInscripto = new ArrayList<>();
				for (CarreraWebServiceJson carreraWebServiceJson : estudianteJson.carreras) {
					carrerasInscripto.add(carreraServiceImp.getCarreraPorCodigo(carreraWebServiceJson.codigo));
				}
				estudiante.setCarrerasInscripto(carrerasInscripto);
				estudianteServiceImp.update(estudiante);
			} catch (NombreInvalidoException n) {
				throw new NombreInvalidoException();
			} catch (ApellidoInvalidoException a) {
				throw new ApellidoInvalidoException();
			} catch (EmailInvalidoException e) {
				throw new EmailInvalidoException();
			}
	}
}