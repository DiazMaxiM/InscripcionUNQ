package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.CarreraJson;
import ar.edu.unq.inscripcionunq.spring.dao.CarreraDao;
import ar.edu.unq.inscripcionunq.spring.exception.CarreraNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.CodigoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.DescripcionInvalidaException;
import ar.edu.unq.inscripcionunq.spring.exception.EstadoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.ExisteCarreraConElMismoCodigoException;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectNotFoundinDBException;
import ar.edu.unq.inscripcionunq.spring.model.Carrera;
import ar.edu.unq.inscripcionunq.spring.model.TypeStatus;
import ar.edu.unq.inscripcionunq.spring.validacion.Validacion;

@Service
@Transactional
public class CarreraServiceImp extends GenericServiceImp<Carrera> implements CarreraService {
    
    @Autowired
    CarreraDao carreraDaoImp;
    
	@Override
	public void eliminarCarrera(String idCarrera) throws IdNumberFormatException, CarreraNoExisteException {
		Carrera carrera;
		try {
			carrera = this.get(new Long(idCarrera));
		} catch (NumberFormatException e) {
			throw new IdNumberFormatException();
		} catch (ObjectNotFoundinDBException e) {
			throw new CarreraNoExisteException();
		}
		
		this.delete(carrera);
	}

	@Override
	public void agregarNuevaCarrera(CarreraJson carreraJson) throws DescripcionInvalidaException, CodigoInvalidoException, EstadoInvalidoException, ExisteCarreraConElMismoCodigoException {
		Carrera nuevaCarrera = this.armarCarreraDesdeJson(carreraJson);
		this.validarSiExisteCarreraConElMismoCodigo(nuevaCarrera.getCodigo());
		this.save(nuevaCarrera);
	    
	}

	@Override
	public void validarSiExisteCarreraConElMismoCodigo(String codigo) throws ExisteCarreraConElMismoCodigoException {
		Carrera carrera = carreraDaoImp.encontrarCarreraConElMismoCodigo(codigo);
		if (carrera != null) {
			throw new ExisteCarreraConElMismoCodigoException();
		}
		
	}

	@Override
	public void actualizarCarrera(CarreraJson carreraJson) throws DescripcionInvalidaException, CodigoInvalidoException, EstadoInvalidoException, ExisteCarreraConElMismoCodigoException, CarreraNoExisteException {
		Carrera carreraRecibida = this.armarCarreraDesdeJson(carreraJson);
		Carrera carreraActual = null;
		try {
			carreraActual = this.get(carreraJson.id);
		} catch (ObjectNotFoundinDBException e) {
			throw new CarreraNoExisteException();
		}
		if (carreraActual != null) {
			this.actualizarInformacionDeLaCarrera(carreraActual, carreraRecibida);
		}
		
	}
	
	private Carrera armarCarreraDesdeJson(CarreraJson carreraJson) throws DescripcionInvalidaException, CodigoInvalidoException, EstadoInvalidoException {
		Carrera carrera = new Carrera(carreraJson.codigo,carreraJson.descripcion);
		TypeStatus estado = carreraJson.habilitada ? TypeStatus.ENABLED : TypeStatus.DISABLED;
		carrera.setEstado(estado);
		Validacion.validarCarrera(carrera);
		return carrera;
	}

	private void actualizarInformacionDeLaCarrera(Carrera carreraActual, Carrera carreraRecibida) throws ExisteCarreraConElMismoCodigoException {
		if(!carreraActual.getCodigo().equals(carreraRecibida.getCodigo())) {
			validarSiExisteCarreraConElMismoCodigo(carreraRecibida.getCodigo());	
		}
		carreraActual.actualizarInformacion(carreraRecibida);
		this.save(carreraActual);
		
	}

	@Override
	public List<CarreraJson> getCarrerasJson() {
		List<Carrera> carreras = this.list();
        return carreras.stream().map(c -> new CarreraJson(c.getId(), c.getCodigo(),c.getDescripcion(),this.estadoCarrera(c.getEstado()))).collect(Collectors.toList());
	}

	private boolean estadoCarrera(TypeStatus estado) {
		return TypeStatus.ENABLED == estado;
	}
      
}
