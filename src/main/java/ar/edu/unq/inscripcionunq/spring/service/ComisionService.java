package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ComisionCompletaJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ComisionJson;
import ar.edu.unq.inscripcionunq.spring.exception.ComisionSinHorariosException;
import ar.edu.unq.inscripcionunq.spring.exception.CommissionNotExistenException;
import ar.edu.unq.inscripcionunq.spring.exception.CupoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.MateriaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.NombreInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.PeriodoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.model.Comision;

public interface ComisionService extends GenericService<Comision> {

	List<ComisionJson> getComisionParaMateriaEnEncuesta(String idMateria, String idEncuesta);

	List<Comision> getTodasLasComisionesDeMateriaEnEncuesta(String idEncuesta);

	List<ComisionCompletaJson> getComisionesEnPeriodo(String idPeriodo);

	void crearNuevaComision(ComisionCompletaJson comisionJson) throws PeriodoInvalidoException, MateriaNoExisteException, NombreInvalidoException, CupoInvalidoException, ComisionSinHorariosException;

	void editarComision(ComisionCompletaJson comisionJson) throws PeriodoInvalidoException, MateriaNoExisteException, NombreInvalidoException, CupoInvalidoException, ComisionSinHorariosException, CommissionNotExistenException;

	void eliminarComision(String idComision) throws IdNumberFormatException, CommissionNotExistenException;

}
