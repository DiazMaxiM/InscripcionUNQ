package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ComisionCompletaJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.IdJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.OfertaAcademicaJson;
import ar.edu.unq.inscripcionunq.spring.exception.CodigoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.DescripcionInvalidaException;
import ar.edu.unq.inscripcionunq.spring.exception.ErrorAlGenerarCodigoException;
import ar.edu.unq.inscripcionunq.spring.exception.EstadoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.NombreInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectNotFoundinDBException;
import ar.edu.unq.inscripcionunq.spring.exception.OfertaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.model.OfertaAcademica;

public interface OfertaAcademicaService extends GenericService<OfertaAcademica>{

	List<OfertaAcademicaJson> getOfertasAcademicasJson();

	void crearOferta(OfertaAcademicaJson ofertaJson) throws DescripcionInvalidaException, CodigoInvalidoException, EstadoInvalidoException, NombreInvalidoException, ErrorAlGenerarCodigoException;

	void actualizarOferta(OfertaAcademicaJson ofertaJson) throws DescripcionInvalidaException, CodigoInvalidoException, EstadoInvalidoException, NombreInvalidoException, OfertaNoExisteException, ErrorAlGenerarCodigoException;

	List<ComisionCompletaJson> getComisionesEnOferta(String idOferta);
	
	void quitarComisionDeOferta(String idComision, String idOferta) throws ObjectNotFoundinDBException, IdNumberFormatException;

	void actualizarComisiones(String idOferta, List<IdJson> idsJson) throws IdNumberFormatException;
}
