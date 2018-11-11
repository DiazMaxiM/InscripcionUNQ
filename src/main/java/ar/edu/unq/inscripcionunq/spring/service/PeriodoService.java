package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.PeriodoJson;
import ar.edu.unq.inscripcionunq.spring.exception.AnhoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.GeneracionDeCodigoException;
import ar.edu.unq.inscripcionunq.spring.exception.NoSePudoGenerarCodigoException;
import ar.edu.unq.inscripcionunq.spring.exception.NumeroInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.PeriodoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.model.Periodo;

public interface PeriodoService extends GenericService<Periodo> {

	void crearPeriodo(PeriodoJson peridoJson) throws AnhoInvalidoException, NumeroInvalidoException, PeriodoInvalidoException, NoSePudoGenerarCodigoException, GeneracionDeCodigoException;

	List<PeriodoJson> getPeriodosJson();

}