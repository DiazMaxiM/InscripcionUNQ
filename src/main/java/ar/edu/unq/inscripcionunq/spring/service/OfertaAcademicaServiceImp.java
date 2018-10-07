package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.CarreraJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ComisionJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.OfertaAcademicaJson;
import ar.edu.unq.inscripcionunq.spring.dao.CarreraDao;
import ar.edu.unq.inscripcionunq.spring.dao.ComisionDao;
import ar.edu.unq.inscripcionunq.spring.dao.OfertaAcademicaDao;
import ar.edu.unq.inscripcionunq.spring.exception.CodigoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.DescripcionInvalidaException;
import ar.edu.unq.inscripcionunq.spring.exception.EstadoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.NombreInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectNotFoundinDBException;
import ar.edu.unq.inscripcionunq.spring.exception.OfertaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.model.Carrera;
import ar.edu.unq.inscripcionunq.spring.model.Comision;
import ar.edu.unq.inscripcionunq.spring.model.OfertaAcademica;
import ar.edu.unq.inscripcionunq.spring.model.TypeStatus;
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
	
	@Override
	public List<OfertaAcademicaJson> getOfertasAcademicasJson() {
		List<OfertaAcademica> ofertas = this.list();
		return ofertas.stream().map(o -> this.crearOfertaJson(o)).collect(Collectors.toList());
	}
	
	private OfertaAcademicaJson crearOfertaJson(OfertaAcademica oferta) {
		Carrera carrera = ofertaAcademicaDaoImp.getCarreraEnOferta(oferta.getId());
		Long nroComisiones = (long) oferta.getComisiones().size();
		CarreraJson carreraJson = new CarreraJson(carrera.getId(),carrera.getCodigo(),
				carrera.getDescripcion(),TypeStatus.esEstadoHabiltado(carrera.getEstado()));
		
		OfertaAcademicaJson ofertaJson = new OfertaAcademicaJson(oferta.getId(), oferta.getNombre(), 
				oferta.getDescripcion(), TypeStatus.esEstadoHabiltado(oferta.getEstado()),
				carreraJson);
		ofertaJson.setNroComisionesCreadas(nroComisiones);
		return ofertaJson;
		
	}

	@Override
	public void clonarOferta(Long idOferta) throws OfertaNoExisteException {
		OfertaAcademica oferta;
		try {
			oferta = this.get(idOferta);
		} catch (ObjectNotFoundinDBException e) {
			throw new OfertaNoExisteException();
		}
		Carrera carrera = ofertaAcademicaDaoImp.getCarreraEnOferta(oferta.getId());
		List<Comision> comisiones = ofertaAcademicaDaoImp.getComisionesEnOferta(oferta.getId());
		oferta.setCarrera(carrera);
		oferta.setComisiones(comisiones);
		this.save(oferta.clonar());	
		
	}

	@Override
	public void crearOferta(OfertaAcademicaJson ofertaJson) throws DescripcionInvalidaException, CodigoInvalidoException, EstadoInvalidoException, NombreInvalidoException {
		OfertaAcademica oferta = this.armarOfertaDesdeJson(ofertaJson);
		this.save(oferta);
		
	}
	
	private OfertaAcademica armarOfertaDesdeJson(OfertaAcademicaJson ofertaAcademicaJson) throws DescripcionInvalidaException, CodigoInvalidoException, EstadoInvalidoException, NombreInvalidoException {
		Carrera carrera = carreraDaoImp.get(ofertaAcademicaJson.carrera.id);
		OfertaAcademica oferta = new OfertaAcademica(ofertaAcademicaJson.nombre, ofertaAcademicaJson.descripcion,carrera);
		TypeStatus estado = ofertaAcademicaJson.habilitada ? TypeStatus.ENABLED : TypeStatus.DISABLED;
		oferta.setEstado(estado);
		Validacion.validarOfertaAcademica(oferta);
		return oferta;
	}

	@Override
	public void actualizarOferta(OfertaAcademicaJson ofertaJson) throws DescripcionInvalidaException, CodigoInvalidoException, EstadoInvalidoException, NombreInvalidoException, OfertaNoExisteException {
		OfertaAcademica ofertaRecibida = this.armarOfertaDesdeJson(ofertaJson);
		OfertaAcademica ofertaActual = null;
		try {
			ofertaActual = this.get(ofertaJson.id);
		} catch (ObjectNotFoundinDBException e) {
			throw new OfertaNoExisteException();
		}
		if (ofertaActual != null) {
			this.actualizarInformacionDeOferta(ofertaActual, ofertaRecibida);
		}
	}

	private void actualizarInformacionDeOferta(OfertaAcademica ofertaActual, OfertaAcademica ofertaRecibida){
		ofertaActual.actualizarInformacion(ofertaRecibida);
		this.save(ofertaActual);
		
	}

	@Override
	@Transactional
	public List<ComisionJson> getComisionesEnOferta(String idOferta) {
		Long id = new Long(idOferta);
		List<Comision> comisiones = ((OfertaAcademicaDao) genericDao).getComisionesEnOferta(id);
		
		return comisiones.stream().map(c -> new ComisionJson(c)).collect(Collectors.toList());
	}

	@Override
	public void quitarComisionDeOferta(String idComision, String idOferta) throws ObjectNotFoundinDBException, IdNumberFormatException {
		Long idOf = new Long(idOferta);
		Long idCom = new Long(idComision);
		try {
			OfertaAcademica oferta = ((OfertaAcademicaDao) genericDao).get(idOf);
			Comision comision = comisionImp.get(idCom);
			oferta.getComisiones().remove(comision);
			this.save(oferta);
 		} catch (NumberFormatException e) {		
 			throw new IdNumberFormatException();		
 		} 		
	}

}
