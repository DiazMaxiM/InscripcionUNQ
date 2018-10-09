package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ComisionCompletaJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ComisionJson;
import ar.edu.unq.inscripcionunq.spring.model.Comision;

public interface ComisionService extends GenericService<Comision> {

	List<ComisionJson> getComisionParaMateriaEnEncuesta(String idMateria, String idEncuesta);

	List<Comision> getTodasLasComisionesDeMateriaEnEncuesta(String idEncuesta);

	List<ComisionCompletaJson> getComisionesEnPeriodo(String idPeriodo);

	void crearNuevaComision(ComisionCompletaJson comisionJson);

}
