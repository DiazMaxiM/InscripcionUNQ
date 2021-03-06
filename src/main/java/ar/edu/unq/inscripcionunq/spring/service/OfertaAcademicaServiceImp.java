package ar.edu.unq.inscripcionunq.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ComisionCompletaJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.IdJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.OfertaAcademicaJson;
import ar.edu.unq.inscripcionunq.spring.dao.CarreraDao;
import ar.edu.unq.inscripcionunq.spring.dao.ComisionDao;
import ar.edu.unq.inscripcionunq.spring.dao.OfertaAcademicaDao;
import ar.edu.unq.inscripcionunq.spring.dao.PeriodoDao;
import ar.edu.unq.inscripcionunq.spring.exception.CodigoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.CopiaOfertaMismoPeriodoException;
import ar.edu.unq.inscripcionunq.spring.exception.DescripcionInvalidaException;
import ar.edu.unq.inscripcionunq.spring.exception.GeneracionDeCodigoException;
import ar.edu.unq.inscripcionunq.spring.exception.EstadoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.FormatoNumeroIdException;
import ar.edu.unq.inscripcionunq.spring.exception.NombreInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectoNoEncontradoEnBDException;
import ar.edu.unq.inscripcionunq.spring.exception.OfertaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.PeriodoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.model.Carrera;
import ar.edu.unq.inscripcionunq.spring.model.Comision;
import ar.edu.unq.inscripcionunq.spring.model.OfertaAcademica;
import ar.edu.unq.inscripcionunq.spring.model.Periodo;
import ar.edu.unq.inscripcionunq.spring.model.TipoEstado;
import ar.edu.unq.inscripcionunq.spring.validacion.Validacion;

@Service
@Transactional
public class OfertaAcademicaServiceImp extends GenericServiceImp<OfertaAcademica> implements OfertaAcademicaService {

	@Autowired
	OfertaAcademicaDao ofertaAcademicaDaoImp;
	
	@Autowired
	CarreraDao carreraDaoImp;
	
	@Autowired
	ComisionDao comisionImp;
	
	@Autowired
	PeriodoDao periodoDaoImp;

	@Override
	public List<OfertaAcademicaJson> getOfertasAcademicasJson() {
		List<OfertaAcademica> ofertas = this.list();
		return ofertas.stream().map(o -> this.crearOfertaJson(o)).collect(Collectors.toList());
	}

	private OfertaAcademicaJson crearOfertaJson(OfertaAcademica oferta) {
		Carrera carrera = ofertaAcademicaDaoImp.getCarreraEnOferta(oferta.getId());
		Long nroComisiones = (long) oferta.getComisiones().size();
		oferta.setCarrera(carrera);
		OfertaAcademicaJson ofertaJson = new OfertaAcademicaJson(oferta);
		ofertaJson.setNroComisionesCreadas(nroComisiones);
		return ofertaJson;
	}

	@Override
	public void crearOferta(OfertaAcademicaJson ofertaJson) throws DescripcionInvalidaException,
			CodigoInvalidoException, EstadoInvalidoException, NombreInvalidoException, GeneracionDeCodigoException {
		try {
			OfertaAcademica oferta = this.armarOfertaDesdeJson(ofertaJson);
			this.save(oferta);
		} catch (ConstraintViolationException e) {
			throw new GeneracionDeCodigoException();
		}
	}

	private OfertaAcademica armarOfertaDesdeJson(OfertaAcademicaJson ofertaAcademicaJson)
			throws DescripcionInvalidaException, CodigoInvalidoException, EstadoInvalidoException,
			NombreInvalidoException, GeneracionDeCodigoException {
		Carrera carrera = carreraDaoImp.get(ofertaAcademicaJson.carrera.id);
		Periodo periodo = periodoDaoImp.get(ofertaAcademicaJson.periodo.id);

		OfertaAcademica oferta = new OfertaAcademica(ofertaAcademicaJson.descripcion, carrera, periodo);

		OfertaAcademica ofertaOriginal = ofertaAcademicaDaoImp
				.getOfertaPorNombre(oferta.armarNombreDeOferta(oferta.getCarrera(), oferta.getPeriodo()));

		if (ofertaOriginal != null && ofertaOriginal.getId() != ofertaAcademicaJson.id) {
			throw new GeneracionDeCodigoException();
		}

		TipoEstado estado = ofertaAcademicaJson.habilitada ? TipoEstado.ENABLED : TipoEstado.DISABLED;
		oferta.setEstado(estado);
		Validacion.validarOfertaAcademica(oferta);
		
		return oferta;
	}

	@Override
	public void actualizarOferta(OfertaAcademicaJson ofertaJson)
			throws DescripcionInvalidaException, CodigoInvalidoException, EstadoInvalidoException,
			NombreInvalidoException, OfertaNoExisteException, GeneracionDeCodigoException {
		OfertaAcademica ofertaRecibida = this.armarOfertaDesdeJson(ofertaJson);
		OfertaAcademica ofertaActual = null;
		try {
			ofertaActual = this.get(ofertaJson.id);
		} catch (ObjectoNoEncontradoEnBDException e) {
			throw new OfertaNoExisteException();
		}
		if (ofertaActual != null) {
			this.actualizarInformacionDeOferta(ofertaActual, ofertaRecibida);
		}
	}

	private void actualizarInformacionDeOferta(OfertaAcademica ofertaActual, OfertaAcademica ofertaRecibida)
			throws DescripcionInvalidaException, NombreInvalidoException, EstadoInvalidoException,
			CodigoInvalidoException, GeneracionDeCodigoException {
		ofertaActual.actualizarInformacion(ofertaRecibida);
		try {
			this.save(ofertaActual);
		} catch (ConstraintViolationException e) {
			throw new GeneracionDeCodigoException();
		}

	}

	@Override
	@Transactional
	public List<ComisionCompletaJson> getComisionesEnOferta(String idOferta) {
		Long id = new Long(idOferta);
		List<Comision> comisiones = ((OfertaAcademicaDao) genericDao).getComisionesEnOferta(id);

		return comisiones.stream().map(c -> new ComisionCompletaJson(c)).collect(Collectors.toList());
	}

	@Override
	public void quitarComisionDeOferta(String idComision, String idOferta)
			throws ObjectoNoEncontradoEnBDException, FormatoNumeroIdException {
		Long idOf = new Long(idOferta);
		Long idCom = new Long(idComision);
		try {
			OfertaAcademica oferta = ((OfertaAcademicaDao) genericDao).get(idOf);
			Comision comision = comisionImp.get(idCom);
			oferta.getComisiones().remove(comision);
			this.save(oferta);
		} catch (NumberFormatException e) {
			throw new FormatoNumeroIdException();
		}
	}

	@Override
	public void actualizarComisiones(String idOferta, List<IdJson> idsJson) throws FormatoNumeroIdException {
		OfertaAcademica oferta;
		List<Comision> comisiones = new ArrayList<Comision>();
		try {
			oferta = ofertaAcademicaDaoImp.get(new Long(idOferta));
		} catch (NumberFormatException e) {
			throw new FormatoNumeroIdException();
		}
		for (IdJson idJson : idsJson) {
			try {
				comisiones.add(comisionImp.get(new Long(idJson.id)));
			} catch (NumberFormatException e) {
				throw new FormatoNumeroIdException();
			}
			oferta.setComisiones(comisiones);
		}
		ofertaAcademicaDaoImp.update(oferta);
	}

	@Override
	public List<OfertaAcademicaJson> getOfertasJsonEnPeriodo(String idPeriodo)
			throws FormatoNumeroIdException, PeriodoInvalidoException {
		Periodo periodo;
		try {
			periodo = periodoDaoImp.get(new Long(idPeriodo));
		} catch (NumberFormatException e) {
			throw new FormatoNumeroIdException();
		}

		if (periodo == null) {
			throw new PeriodoInvalidoException();
		}

		List<OfertaAcademica> ofertas = ofertaAcademicaDaoImp.getOfertasParaPeriodo(periodo.getId());
		
		return ofertas.stream().map(o -> new OfertaAcademicaJson(o)).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public Long clonarOfertaAcademica(OfertaAcademicaJson ofertaAcademicaJson) throws CopiaOfertaMismoPeriodoException {
		OfertaAcademica oferta;
		oferta = ofertaAcademicaDaoImp.get(new Long(ofertaAcademicaJson.id));
		Periodo periodo = periodoDaoImp.get(ofertaAcademicaJson.periodo.id);
		if (oferta.getPeriodo().equals(periodo)) {
			throw new CopiaOfertaMismoPeriodoException();
		}
		OfertaAcademica ofertaNueva = new OfertaAcademica(oferta.getDescripcion().concat(" COPIA"), oferta.getCarrera(),
				periodo);

		List<Comision> comisionesDelPeriodo = comisionImp.getComisionParaPeriodo(periodo.getId());

		List<Comision> listaDeComisiones = oferta.getComisiones();
		for (Comision comision : listaDeComisiones) {
			List<Comision> comisionesEncontradas = comisionesDelPeriodo.stream()
					.filter(c -> c.getMateria().equals(comision.getMateria())
							&& c.getNombre().equals(comision.getNombre()) && c.getCupo().equals(comision.getCupo())
							&& c.getEstado().equals(comision.getEstado())
							&& c.getHorariosString().equals(comision.getHorariosString()))
					.collect(Collectors.toList());
			if (comisionesEncontradas.size() == 1) {
				ofertaNueva.agregarComision(comisionImp.get(comisionesEncontradas.get(0).getId()));
			} else {
				Long idComision = comisionImp.save(comision.clonar(periodo));
				ofertaNueva.agregarComision(comisionImp.get(idComision));
			}
		}
		
		Long id = ofertaAcademicaDaoImp.save(ofertaNueva);
		
		return id;
	}
}