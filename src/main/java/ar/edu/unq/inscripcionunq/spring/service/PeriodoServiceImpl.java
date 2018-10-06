
package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.PeriodoJson;
import ar.edu.unq.inscripcionunq.spring.exception.AnhoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.NoSePudoGenerarCodigoException;
import ar.edu.unq.inscripcionunq.spring.exception.NumeroInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.PeriodoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.model.Periodo;
import ar.edu.unq.inscripcionunq.spring.model.TipoPeriodo;
import ar.edu.unq.inscripcionunq.spring.validacion.Validacion;

@Service
@Transactional
public class PeriodoServiceImpl extends GenericServiceImp<Periodo> implements PeriodoService{

	@Override
	public void crearPeriodo(PeriodoJson peridoJson) throws AnhoInvalidoException, NumeroInvalidoException, PeriodoInvalidoException, NoSePudoGenerarCodigoException {
		Periodo periodo = this.armarPeriodoDesdeJson(peridoJson);
		if(StringUtils.isEmpty(periodo.getCodigo())) {
		  	throw new NoSePudoGenerarCodigoException();
		}
		this.save(periodo);
		
	}

	private Periodo armarPeriodoDesdeJson(PeriodoJson peridoJson) throws AnhoInvalidoException, NumeroInvalidoException, PeriodoInvalidoException {
		TipoPeriodo tipoPeriodo = TipoPeriodo.valueOf(peridoJson.tipoPeriodo);
		Periodo periodo = new Periodo(peridoJson.anho,peridoJson.numero,tipoPeriodo);
		periodo.generarCodigo();
		Validacion.validarPeriodo(periodo);
		return periodo;
		
	}

	@Override
	public List<PeriodoJson> getPeriodosJson() {
		List<Periodo> periodos = this.list();
		return periodos.stream().map(periodo -> new PeriodoJson(periodo)).collect(Collectors.toList());
	} 
}