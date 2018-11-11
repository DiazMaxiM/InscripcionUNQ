package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ComisionCompletaJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.IdJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.OfertaAcademicaJson;
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
import ar.edu.unq.inscripcionunq.spring.model.OfertaAcademica;

public interface OfertaAcademicaService extends GenericService<OfertaAcademica> {

	List<OfertaAcademicaJson> getOfertasAcademicasJson();

	void crearOferta(OfertaAcademicaJson ofertaJson) throws DescripcionInvalidaException, CodigoInvalidoException,
			EstadoInvalidoException, NombreInvalidoException, GeneracionDeCodigoException;

	void actualizarOferta(OfertaAcademicaJson ofertaJson) throws DescripcionInvalidaException, CodigoInvalidoException,
			EstadoInvalidoException, NombreInvalidoException, OfertaNoExisteException, GeneracionDeCodigoException;

	List<ComisionCompletaJson> getComisionesEnOferta(String idOferta);

	void quitarComisionDeOferta(String idComision, String idOferta)
			throws ObjectoNoEncontradoEnBDException, FormatoNumeroIdException;

	void actualizarComisiones(String idOferta, List<IdJson> idsJson) throws FormatoNumeroIdException;

	List<OfertaAcademicaJson> getOfertasJsonEnPeriodo(String idPeriodo)
			throws FormatoNumeroIdException, PeriodoInvalidoException;

	Long clonarOfertaAcademica(OfertaAcademicaJson ofertaAcademicaJson) throws CopiaOfertaMismoPeriodoException;

}