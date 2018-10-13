package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.CarreraJson;
import ar.edu.unq.inscripcionunq.spring.exception.CarreraNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.CodigoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.DescripcionInvalidaException;
import ar.edu.unq.inscripcionunq.spring.exception.EstadoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.ExisteCarreraConElMismoCodigoException;
import ar.edu.unq.inscripcionunq.spring.model.Carrera;

public interface CarreraService extends GenericService<Carrera> {

	void agregarNuevaCarrera(CarreraJson carreraJson) throws DescripcionInvalidaException, CodigoInvalidoException,
			EstadoInvalidoException, ExisteCarreraConElMismoCodigoException;

	void validarSiExisteCarreraConElMismoCodigo(String codigo) throws ExisteCarreraConElMismoCodigoException;

	void actualizarCarrera(CarreraJson carreraJson) throws DescripcionInvalidaException, CodigoInvalidoException,
			EstadoInvalidoException, ExisteCarreraConElMismoCodigoException, CarreraNoExisteException;

	List<CarreraJson> getCarrerasJson();

	Carrera getCarreraPorCodigo(String codigo);
}
