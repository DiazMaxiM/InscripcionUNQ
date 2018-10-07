package ar.edu.unq.inscripcionunq.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ExceptionJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.PeriodoJson;
import ar.edu.unq.inscripcionunq.spring.exception.AnhoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.ErrorAlGenerarCodigoException;
import ar.edu.unq.inscripcionunq.spring.exception.NoSePudoGenerarCodigoException;
import ar.edu.unq.inscripcionunq.spring.exception.NumeroInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.PeriodoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.model.TipoPeriodo;
import ar.edu.unq.inscripcionunq.spring.service.PeriodoService;

@RestController
public class PeriodoController {
	
	@Autowired
	private PeriodoService periodioServiceImp;
	
	
	@GetMapping("/periodos")
	public ResponseEntity<List> getPeridos() {
		return ResponseEntity.ok().body(periodioServiceImp.getPeriodosJson());
	}
	
	@PutMapping("/periodos/crearPeriodo/")
	public ResponseEntity crearNuevaOferta(@RequestBody PeriodoJson peridoJson) {
		try {
			periodioServiceImp.crearPeriodo(peridoJson);
		} catch (AnhoInvalidoException | NumeroInvalidoException | PeriodoInvalidoException
				| NoSePudoGenerarCodigoException | ErrorAlGenerarCodigoException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		} 
		return ResponseEntity.ok().build();

	}
	
	@GetMapping("/tipoPeriodos")
	public ResponseEntity getTiposPeriodos() {
		return ResponseEntity.ok().body(TipoPeriodo.values());
	}

}